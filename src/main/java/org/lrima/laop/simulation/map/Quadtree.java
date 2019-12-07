package org.lrima.laop.simulation.map;

import org.apache.commons.math3.genetics.Fitness;
import org.lrima.laop.physic.SimpleCar;
import org.lrima.laop.physic.staticobjects.FitnessWallObject;
import org.lrima.laop.physic.staticobjects.StaticLineObject;

import java.util.ArrayList;

/**
 * Class used to optimise the seach for possinle colisions between LineCollidables and StaticLines
 *
 * @author Léonard
 */
public class Quadtree {
    private final int MIN_OBJECTS = 20;
    private final int MAX_LEVELS = 7;

    private int level;
    private ArrayList<StaticLineObject> lines;
    private Quadtree[] nodes;
    private boolean splitted;

    //bounds
    private double p1x, p1y, p2x, p2y;

    float midX, midY;

    /**
     * Creates a Quadtree that contains the static lines
     *
     * @param pLevel the level of the quadtree
     * @param p1x the x coordinate of first point forming the bound
     * @param p1y the y coordinate of first point forming the bound
     * @param p2x the x coordinate of second point forming the bound
     * @param p2y the y coordinate of second point forming the bound
     */
    public Quadtree(int pLevel, double p1x, double p1y, double p2x, double p2y) {
        level = pLevel;
        lines = new ArrayList();
        midX = (float) ((p1x + p2x)/2.0);
        midY = (float) ((p1y + p2y)/2.0);
        splitted = false;

        this.p1x = p1x;
        this.p1y = p1y;
        this.p2x = p2x;
        this.p2y = p2y;
    }

    /**
     * insert a new line into the quadtree
     *
     * @param line the line to be added
     */
    public void insert(StaticLineObject line){
        if(!splitted){
            lines.add(line);
            if(lines.size() > MIN_OBJECTS && level < MAX_LEVELS){
                split();
            }
        }
        else{
            int i = getLineIndexPure(line);
            if(i == -1){
                lines.add(line);
            }else{
                nodes[i].insert(line);
            }
        }
    }

    /**
     * Splits the quadtree into four quaters and puts all the related lines into the right node
     *
     */
    private void split() {
        splitted = true;
        nodes = new Quadtree[4];
        nodes[0] = new Quadtree(level-1, p1x, p1y, midX, midY);
        nodes[1] = new Quadtree(level-1, midX, p1y, p2x, midY);
        nodes[2] = new Quadtree(level-1, p1x, midY, midX, p2y);
        nodes[3] = new Quadtree(level-1, midX, midY, p2x, p2y);

        int i = 0;
        while (i < lines.size()) {
            int index = getLineIndexPure(lines.get(i));
            if (index != -1)
                nodes[index].insert(lines.remove(i));
            else
                i++;
        }
    }

    /**
     * Gets the index of a line (what quarter should he be on)
     *
     * If the value is -1, it means that the line should be in the parent compatiment
     *
     * @param line the line
     * @return the index of the line
     */
    private int getLineIndexPure(StaticLineObject line){
        return getLineIndexPure(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    /**
     * Gets the index of a line (what quarter should he be on)  <br/> <br/>
     *
     * If the value is -1, it means that the line should be in the parent compatiment
     *
     * @param q1x the coordinates of the line
     * @param q1y the coordinates of the line
     * @param q2x the coordinates of the line
     * @param q2y the coordinates of the line
     * @return the index of the node the line should be in
     */
    private int getLineIndexPure(float q1x, float q1y, float q2x, float q2y){
        int i1 = checkIndex(q1x, q1y);
        int i2 = checkIndex(q2x, q2y);
        if(i1==i2){
            return i1;
        }
        else
            return -1;
    }

    /**
     * Gets all the possible indexes of a line (what quarter should he be on)  <br/> <br/>
     *
     * If the returned vector has a lenght of more then one, it means that the line should be in the parent node
     *
     * @param q1x the coordinates of the line
     * @param q1y the coordinates of the line
     * @param q2x the coordinates of the line
     * @param q2y the coordinates of the line
     * @return all the indexes of the node the line should be in
     */
    private int[] getLineIndexAll(float q1x, float q1y, float q2x, float q2y){
        int i1 = checkIndex(q1x, q1y);
        int i2 = checkIndex(q2x, q2y);
        if(i1==i2){
            return new int[]{i1};
        }

        //si ils sont en diagonales
        if(i1+i2 == 3){
            return new int[]{0, 1, 2, 3};
        }
        else{
            return new int[]{i1, i2};
        }
    }

    /**
     * Checks the index of the point
     *
     * @param p1x the x coordinate of the point
     * @param p1y the x coordinate of the point
     * @return the index of the point
     */
    private int checkIndex(float p1x, float p1y) {
        if(p1x > midX){
            if(p1y > midY){
                return 3;
            }
            else{
                return 1;
            }
        }else{
            if(p1x > midY){
                return 2;
            }
            else{
                return 0;
            }
        }
    }

    /**
     * Calls the collide function of all the lineCollidable of all the lines in this node. Then proceedes to collide with all the other nodes if it can
     *
     * @param lineCollidable the lineCollidable to check the collision of
     */
    public void collide(LineCollidable lineCollidable){
        for (StaticLineObject line : lines) {
            if(!(line instanceof FitnessWallObject)) {
                lineCollidable.collide(line);
            }else{
                if(lineCollidable instanceof SimpleCar) {
                    lineCollidable.collideFitnessAdder((FitnessWallObject) line);
                }
            }
        }

        if(nodes == null) return;

        int[] index = getLineIndexAll(lineCollidable.getX1(), lineCollidable.getY1(), lineCollidable.getX2(), lineCollidable.getY2());
        for (int i : index) {
            nodes[i].collide(lineCollidable);
        }
    }
}