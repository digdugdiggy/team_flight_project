package edu.uhcl.team_drone.world.mapgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapGenerator {

    private enum Direction {

        N(0, 1), S(0, -1), E(1, 0), W(-1, 0);

        private final int changeInX;
        private final int changeInY;

        Direction(int xChange, int yChange) {
            this.changeInX = xChange;
            this.changeInY = yChange;
        }
    };
    
    
    private static final int MAX_LOOPS = 100;

    private Cell[][] mapCells;

    private List<Direction> directions;

    private final int columns;
    private final int rows;

    private Random rand = new Random();

    public MapGenerator(int widthIn, int heightIn) {
        // ENSURE MAZE SIZE IS ODD
        if (widthIn % 2 == 0) {
            this.columns = widthIn + 1;
        } else {
            this.columns = widthIn;
        }
        if (heightIn % 2 == 0) {
            this.rows = heightIn + 1;
        } else {
            this.rows = heightIn;
        }

        // Directions is a list of 4 directions, shuffled
        directions = new ArrayList<Direction>();
        directions.add(Direction.N);
        directions.add(Direction.S);
        directions.add(Direction.E);
        directions.add(Direction.W);
        Collections.shuffle(directions);
        
        // fill maze with walls to start
        initMazeAndFillWithWalls();

        // start mazes from the center
        int currentX = columns / 2;
        int currentY = rows / 2;
        mapCells[currentX][currentY].visited = true;
        mapCells[currentX][currentY].setImage('#');     
        
        int loopCount = 0;
        boolean firstLoop = true;

        while ( loopCount < MAX_LOOPS) {
            loopCount++;
            
            // choose a random direction
            Collections.shuffle(directions);
            Direction chosenDirection = directions.get(rand.nextInt(directions.size()));
            if(firstLoop){
                firstLoop = false;
                chosenDirection = Direction.E;
            }
            
            //Move two cells in that direction
            int nextX = currentX + chosenDirection.changeInX * 2;
            int nextY = currentY + chosenDirection.changeInY * 2;

            //check if new position will be in bounds
            if (nextX <= columns - 2 && nextY <= rows - 2 && nextX > 0 && nextY > 0) {

                // and if new position HASNT been visited
                if (!mapCells[nextX][nextY].visited) {

                    // visit it, and set it as current cell
                    mapCells[nextX][nextY].visited = true;
                    mapCells[nextX - chosenDirection.changeInX][nextY - chosenDirection.changeInY].visited = true;
                    currentX = nextX;
                    currentY = nextY;
                }
            }
        }
        int finalX = currentX;
        int finalY = currentY;
        mapCells[finalX][finalY].setImage('#');
    }

    private void initMazeAndFillWithWalls() {
        mapCells = new Cell[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                mapCells[i][j] = new Cell(i, j);
                if (i == 0 || i == columns - 1) {
                    mapCells[i][j].visited = false;
                    mapCells[i][j].setImage('W');
                } else if (j == 0 || j == rows - 1) {
                    mapCells[i][j].visited = false;
                    mapCells[i][j].setImage('W');
                }
            }
        }
    }

    public void printMap() {
        for (int x = 1; x < rows-1; x++) {
            for (int y = 1; y < columns-1; y++) {
                char out = mapCells[y][x].getImage();
                System.out.print(out);
            }
            System.out.printf("\n");
        }
    }

    public char[][] getMap() {
        char[][] charMap = new char[columns][rows];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                charMap[y][x] = mapCells[y][x].getImage();
            }
        }
        return charMap;
    }
    

}
