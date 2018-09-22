/**
 * Pledge: "I pledge my honor that I have abided by the Stevens Honor System."
 * @author Bobby Georgiou
 * CS 284 A | Homework 4 | 03-25-2018
 */
/**
 * Cited:
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/

import java.util.*;

public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        // COMPLETE HERE FOR PROBLEM 1
        if (x < 0 || y < 0 || x > maze.getNCols() - 1 || y > maze.getNRows() - 1) {
            return false;
        } else if (maze.getColor(x, y) != NON_BACKGROUND) {
            return false;
        } else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            maze.recolor(x, y, PATH);
            return true;
        } else {
            maze.recolor(x, y, PATH);
            if (findMazePath(x + 1, y)) {
                return true;
            }
            if (findMazePath(x, y + 1)) {
                return true;
            }
            if (findMazePath(x - 1, y)) {
                return true;
            }
            if (findMazePath(x, y - 1)) {
                return true;
            }
            maze.recolor(x, y, TEMPORARY);
            return false;
        }
    }

    // ADD METHOD FOR PROBLEM 2 HERE
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathStackBased(x, y, result, trace);
        return result;
    }

    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
        if (x < 0 || y < 0 || x > maze.getNCols() - 1 || y > maze.getNRows() - 1) {
            trace = null;
        } else if (maze.getColor(x, y) != NON_BACKGROUND) {
            trace = null;
        } else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            PairInt pi = new PairInt(x, y);
            trace.push(pi);
            ArrayList<PairInt> temp = new ArrayList<PairInt>();
            temp.addAll(trace);
            result.add(temp);
            trace = new Stack<>();
        } else { // produces an extra coordinate in one of ArrayList
            maze.recolor(x, y, PATH);
            PairInt pi = new PairInt(x, y);

            trace.push(pi);

            findMazePathStackBased(x + 1, y, result, trace);
            findMazePathStackBased(x, y + 1, result, trace);
            findMazePathStackBased(x - 1, y, result, trace);
            findMazePathStackBased(x, y - 1, result, trace);

            trace.pop();
        }
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        boolean init = true;
        int min_length = 0;
        ArrayList<PairInt> min_arlist = new ArrayList<>();
        ArrayList<ArrayList<PairInt>> all_paths = findAllMazePaths(x, y);
        for (ArrayList<PairInt> arlist : all_paths) {
            if (init) {
                min_length = arlist.size();
                min_arlist = arlist;
                init = false;
            }
            if (arlist.size() < min_length) {
                min_length = arlist.size();
                min_arlist = arlist;
            }
        }
        return min_arlist;
    }

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
