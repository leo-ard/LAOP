package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.staticobjects.FitnessWallObject;
import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.utils.math.RandomUtils;

import java.awt.geom.Point2D;

/**
 * Map made like a randomly generated maze
 * @author Clement Bisaillon
 */
public class MazeMap extends AbstractMap {
    private final int MAP_SQUARE_WIDTH = 100;
    private int numberSquareX;
    private boolean[][] north;
    private boolean[][] south;
    private boolean[][] east;
    private boolean[][] west;
    private boolean[][] visited;
    private Point2D start;

	private double orientation;

	/**
	 * Initiates a new maze map
	 * @param numberOfSquareX the number of squares in the x direction.
	 */
	public MazeMap(int numberOfSquareX) {
    	super();
    	this.numberSquareX = numberOfSquareX;

    	this.initMaze();
    	this.generate(1, 1);
    	this.createMazeObjects();
    	this.generateStartLocation();

    }

    /**
     * Initialize the maze with each border present
     */
    private void initMaze() {
    	//Initialize border cells as already visited
    	visited = new boolean[this.numberSquareX + 2][this.numberSquareX + 2];
    	for(int x = 0 ; x < this.numberSquareX + 2 ; x++) {
    		visited[x][0] = true;
    		visited[x][this.numberSquareX + 1] = true;
    	}
    	for(int y = 0 ; y < this.numberSquareX + 2 ; y++) {
    		visited[0][y] = true;
    		visited[this.numberSquareX + 1][y] = true;
    	}

    	//initialize all walls as present
    	this.north = new boolean[this.numberSquareX + 2][this.numberSquareX + 2];
    	this.west = new boolean[this.numberSquareX + 2][this.numberSquareX + 2];
    	this.east = new boolean[this.numberSquareX + 2][this.numberSquareX + 2];
    	this.south = new boolean[this.numberSquareX + 2][this.numberSquareX + 2];
    	for(int x = 0 ; x < this.numberSquareX + 2 ; x++) {
    		for(int y = 0 ; y < this.numberSquareX + 2 ; y++) {
    			north[x][y] = true;
    			east[x][y] = true;
    			west[x][y] = true;
    			south[x][y] = true;
    		}
    	}
    }

    /**
     * Generate the maze
     * @param x the position in x to generate the maze from
     * @param y the position in y to generate the maze from
     */
    private void generate(int x, int y) {
    	visited[x][y] = true;
    	while(hasNeighbors(x, y)) {

    		while(true) {
    			int neighbor = RandomUtils.getInteger(0, 3);
    			if(neighbor == 0 && !visited[x + 1][y]) {
    				//Right
    				west[x + 1][y] = false;
					east[x][y] = false;
					generate(x + 1, y);
					break;
    			}
    			else if(neighbor == 1 && !visited[x - 1][y]) {
    				//Left
    				west[x][y] = false;
					east[x - 1][y] = false;
					generate(x - 1, y);
					break;
    			}
    			else if(neighbor == 2 && !visited[x][y - 1]) {
    				//Top
    				north[x][y] = false;
					south[x][y - 1] = false;
					generate(x, y - 1);
					break;
    			}
    			else if(neighbor == 3 && !visited[x][y + 1]) {
    				//Bottom
    				south[x][y] = false;
					north[x][y + 1] = false;
					generate(x, y + 1);
					break;
    			}
    		}
    	}
    }

    /**
     * Check if a square has neighbors that are not visited
     * @param x the position in x of the node
     * @param y the position in y of the node
     * @return true if the node has a neighbor
     */
    private boolean hasNeighbors(int x, int y) {
    	boolean right = false;
    	boolean left = false;
    	boolean top = false;
    	boolean bottom = false;

    	if(x + 1 < visited.length) {
    		right = !visited[x + 1][y];
    	}

    	if(x - 1 >= 0) {
    		left = !visited[x - 1][y];
    	}

    	if(y - 1 >= 0) {
    		top = !visited[x][y - 1];
    	}

    	if(y + 1 < visited[0].length) {
    		bottom = !visited[x][y + 1];
    	}

    	return right || left || top || bottom;
    }

    /**
     * Create the lines of the maze from the generated maze
     */
    private void createMazeObjects() {
    	//North lines
    	for(int x = 1 ; x < north.length - 1 ; x++) {
			for(int y = 1 ; y < north[0].length - 1 ; y++) {
				int x1 = (x - 1) * MAP_SQUARE_WIDTH;
				int y1 = (y - 1) * MAP_SQUARE_WIDTH;
				int x2 = x * MAP_SQUARE_WIDTH;
				int y2 = (y - 1) * MAP_SQUARE_WIDTH;
				if(north[x][y]) {
					this.lines.add(new StaticLineObject(x1, y1, x2, y2));
				}else{
					this.lines.add(new FitnessWallObject(x1, y1, x2, y2));
				}
			}
		}
    	//South lines
    	for(int x = 1 ; x < south.length - 1 ; x++) {
    		for(int y = 1 ; y < south[0].length - 1 ; y++) {
    			if(south[x][y]) {
    				this.lines.add(new StaticLineObject((x - 1) * MAP_SQUARE_WIDTH, y * MAP_SQUARE_WIDTH, x * MAP_SQUARE_WIDTH, y * MAP_SQUARE_WIDTH));
    			}
    		}
    	}
    	//East lines
    	for(int x = 1 ; x < east.length - 1 ; x++) {
    		for(int y = 1 ; y < east[0].length - 1 ; y++) {
    			int x1 = x * MAP_SQUARE_WIDTH;
    			int y1 = (y - 1) * MAP_SQUARE_WIDTH;
    			int x2 = x * MAP_SQUARE_WIDTH;
    			int y2 = y * MAP_SQUARE_WIDTH;

    			if(east[x][y]) {
    				this.lines.add(new StaticLineObject(x1, y1, x2, y2));
    			}else{
    				this.lines.add(new FitnessWallObject(x1, y1, x2, y2));
				}
    		}
    	}
    	//West lines
    	for(int x = 1 ; x < west.length - 1 ; x++) {
    		for(int y = 1 ; y < west[0].length - 1 ; y++) {
    			if(west[x][y]) {
    				this.lines.add(new StaticLineObject((x - 1) * MAP_SQUARE_WIDTH, (y - 1) * MAP_SQUARE_WIDTH, (x - 1) * MAP_SQUARE_WIDTH, y * MAP_SQUARE_WIDTH));
    			}
    		}
    	}
    }

    /**
     * Generate the starting location after creating the maze
     */
    private void generateStartLocation() {
		int randomX = 0;//RandomUtils.getInteger(0, numberSquareX - 1);
		int randomY = 0;//RandomUtils.getInteger(0, numberSquareX - 1);


		this.start = new Point2D.Double((randomX * MAP_SQUARE_WIDTH) + MAP_SQUARE_WIDTH / 2.0, (randomY * MAP_SQUARE_WIDTH) + 10);

//		System.out.println(south[1][1] + " " + east[1][1]);

		if(east[1][1]){
			orientation = 0;
		}
		if(south[1][1]){
			orientation = -Math.PI / 2;
		}

	}

    @Override
    public Point2D getStartPoint() {
    	return this.start;
    }

	/**
	 * Gets the starting orientation
	 *
	 * @return the starting orientation
	 */
	public double getStartingOrientation() {
		return orientation;
	}
}
