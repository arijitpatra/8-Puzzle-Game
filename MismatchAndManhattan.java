package pkg8.puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class MismatchAndManhattan {

    String fullSolutionString = "";

    String heuristicValue;

    String str = ""; // initial state
    String goal = ""; // goal state
    public int totalSteps;

    PriorityQueue<StateOrder> queue;

    Map<String, Integer> levelDepth;

    Map<String, String> stateHistory;

    int nodes = 0; // counter for node generation
    //int limit = 10000; // counter for limit
    int unique = -1;// counter for uniq state
    int newValue; // counter depth limit
    int a; // position of blank
    int h; // heuristic

    String currState;
    boolean solution = false;

    MismatchAndManhattan(String str, String goal) {
        queue = new PriorityQueue<StateOrder>();
        levelDepth = new HashMap<String, Integer>();
        stateHistory = new HashMap<String, String>();
        this.str = str;
        this.goal = goal;
        totalSteps = 0;
        heuristicValue = "";
        nodes = 0;
        unique = -1;
        addToQueue(str, null, 0);

    }

    void doSearch(String heuristic) {
        heuristicValue = heuristic;

        while (!queue.isEmpty()) {

            currState = queue.poll().toString();// RETRIEVE then remove first
            // node

            if (currState.equals(goal)) { // check if current state is goal
                // state
                solution = true;

                // line to print solution to console
                printSolution(currState);// print solutions
                break;
            } /**
             * if (levelDepth.get(currState) == limit) {// check if under limit
             * solution = false; printSolution(currState);// print solutions
             * break; }
             */
            else {

                int ord;// ordering for same level and same priority;

                // expand currentstate then add expanded node to the of openlist
                a = currState.indexOf("0");// get index position of 0 (blank)

                // left
                while (a != 0 && a != 3 && a != 6) {// if blank not in the left
                    // most column then it able
                    // move left
                    String nextState = currState.substring(0, a - 1) + "0"
                            + currState.charAt(a - 1)
                            + currState.substring(a + 1);// swap blank with
                    // destination

                    ord = 1;
                    addToQueue(nextState, currState, ord);// add expanded node
                    // to
                    // openlist
                    nodes++;
                    break;
                }

                // up
                while (a != 0 && a != 1 && a != 2) {// if blank not in the very
                    // top of row then it able
                    // to move up
                    String nextState = currState.substring(0, a - 3) + "0"
                            + currState.substring(a - 2, a)
                            + currState.charAt(a - 3)
                            + currState.substring(a + 1);// swap blank with
                    // destination
                    ord = 2;
                    addToQueue(nextState, currState, ord);// add expanded node
                    // to
                    // openlist
                    nodes++; // nodes = nodes + 1; a node is being genereted add
                    // it to counter
                    break;
                }

                // right
                while (a != 2 && a != 5 && a != 8) {// if blank not in the right
                    // most column then it able
                    // to move right
                    String nextState = currState.substring(0, a)
                            + currState.charAt(a + 1) + "0"
                            + currState.substring(a + 2);// swap blank with
                    // destination
                    ord = 3;
                    addToQueue(nextState, currState, ord);// add expanded node
                    // to
                    // openlist
                    nodes++;
                    break;
                }

                // down
                while (a != 6 && a != 7 && a != 8) {// if blank not in the very
                    // bottom row then it able
                    // to move down
                    String nextState = currState.substring(0, a)
                            + currState.substring(a + 3, a + 4)
                            + currState.substring(a + 1, a + 3) + "0"
                            + currState.substring(a + 4);// swap blank with
                    // destination

                    ord = 4;
                    addToQueue(nextState, currState, ord);// add expanded node
                    // to
                    // openlist
                    nodes++;
                    break;
                }

            }

        }

        if (solution) {
            System.out.println("Solution Exist");
        } else {
            /*System.out.println("Solution not yet found! My suggestion are:");
             System.out.println("1. Try to increse level depth limit ");
             System.out.println("2. Use other heuristc ");
             System.out.println("3. Maybe it is physically impossible");*/
        }

    }

    private void addToQueue(String newState, String oldState, int ord) {
        if (!levelDepth.containsKey(newState)) {// check repeated state
            newValue = oldState == null ? 0 : levelDepth.get(oldState) + 1;
            unique++;
            levelDepth.put(newState, newValue);

            if (heuristicValue.equalsIgnoreCase("Mismatch")) {
                h = calcMismatch(newState, goal) + newValue;
            } else {
                h = calcManhattan(newState, goal) + newValue;
            }

            queue.add(new StateOrder(h, newState, newValue, ord));// add to
            // priority
            // queue
            stateHistory.put(newState, oldState);
        }

    }

    int calcManhattan(String currState, String goalState) {
        // lookup table for manhattan distance
        int[][] manValue = {{0, 1, 2, 1, 2, 3, 2, 3, 4},
        {1, 0, 1, 2, 1, 2, 3, 2, 3}, {2, 1, 0, 3, 2, 1, 4, 3, 2},
        {1, 2, 3, 0, 1, 2, 1, 2, 3}, {2, 1, 2, 1, 0, 1, 2, 1, 2},
        {3, 2, 1, 2, 1, 0, 3, 2, 1}, {2, 3, 4, 1, 2, 3, 0, 1, 2},
        {3, 2, 3, 2, 1, 2, 1, 0, 1}, {4, 3, 2, 3, 2, 1, 2, 1, 0},};
        // calculate manhattan distance
        int heu = 0;
        int result = 0;

        for (int i = 1; i < 9; i++) {
            heu = manValue[currState.indexOf(String.valueOf(i))][goalState
                    .indexOf(String.valueOf(i))];
            result = result + heu;

        }
        return result;
    }

    int calcMismatch(String currState, String goalState) {
        int mismatch = 0;
        for (int i = 1; i < 9; i++) {
            if (currState.indexOf(String.valueOf(i)) != goalState
                    .indexOf(String.valueOf(i))) {
                mismatch++;
            }
        }
        return mismatch;
    }

    void printSolution(String currState) {

        totalSteps = levelDepth.get(currState);
        fullSolutionString = "";

        if (solution) {
            System.out.println("Solution found in " + levelDepth.get(currState)
                    + " step(s)");
            System.out.println("Node generated: " + nodes);
            System.out.println("Unique Node generated: " + unique);
        } else {
            /*System.out.println("Solution not found!");
             System.out.println("Depth Limit Reached!");
             System.out.println("Node generated: " + nodes);
             System.out.println("Unique Node generated: " + unique);*/
        }

        String traceState = currState;

        Stack<String> stack = new Stack<String>();
        while (traceState != null) {
            stack.push(traceState);
            traceState = stateHistory.get(traceState);
        }
        int flag = 0;
        String preString = "";
        String proString;

        int xx = 0;

        while (!stack.isEmpty()) {
            traceState = stack.peek();
            if (flag == 0) {
                flag = 1;
                preString = traceState;
            } else {
                proString = traceState;
                for (xx = 0; xx < proString.length(); xx++) {

                    if (preString.charAt(xx) == '0') {

                        fullSolutionString = fullSolutionString + "Move "
                                + proString.charAt(xx) + "\n";
                        break;
                    }
                }

                preString = proString;
            }

            System.out
                    .println(traceState + " at " + levelDepth.get(traceState));

            try {
                for (int z = 0; z < 9; z++) {
                    System.out.print(" " + String.valueOf(traceState.charAt(z))
                            + " ");
                    if ((z + 1) % 3 == 0) {
                        System.out.println();
                    }
                }
            } catch (NullPointerException e) {
            }
            traceState = stateHistory.get(traceState);
            stack.pop();
        }

        System.out.println(fullSolutionString);
        System.out.println("Solution found in " + levelDepth.get(currState)
                + " step(s)");

    }
}
