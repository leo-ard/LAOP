package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.physic.staticobjects.StaticLineObject;

import java.util.ArrayList;

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

    /*
     * Constructor
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
    private int getLineIndexPure(StaticLineObject line){
        return getLineIndexPure(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    private int[] getLineIndexAll(float q1x, float q1y, float q2x, float q2y){
        int i1 = checkIndex(q1x, q1y);
        int i2 = checkIndex(q2x, q2y);
        if(i1==i2){
            return new int[]{i1};
        }

        if(i1+i2 == 3){
            return new int[]{0, 1, 2, 3};
        }
        else{
            return new int[]{i1, i2};
        }
    }

    private int getLineIndexPure(float q1x, float q1y, float q2x, float q2y){
        int i1 = checkIndex(q1x, q1y);
        int i2 = checkIndex(q2x, q2y);
        if(i1==i2){
            return i1;
        }
        else
            return -1;
    }

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

    public void collide(LineCollidable lineCollidable){
        for (StaticLineObject line : lines) {
            lineCollidable.collide(line);
        }

        if(nodes == null) return;

        int[] index = getLineIndexAll(lineCollidable.getX1(), lineCollidable.getY1(), lineCollidable.getX2(), lineCollidable.getY2());
        for (int i : index) {
            nodes[i].collide(lineCollidable);
        }
    }
}