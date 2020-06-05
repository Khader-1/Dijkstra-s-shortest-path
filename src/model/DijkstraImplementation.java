package model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Khader
 */
public class DijkstraImplementation {

    private final Graph graph;
    private Graph.Node start;
    private CheckPoint current;
    private final double[][] vals;
    private Set<CheckPoint> checkPoints;

    public DijkstraImplementation(Graph graph) {
        this.graph = graph;
        vals = new double[graph.getSize()][graph.getSize()];
    }

    public void init(Graph.Node start) {
        this.start = start;
        checkPoints = new HashSet();
        for (Graph.Node node : graph.getNodes()) {
            CheckPoint checkPoint = new CheckPoint(node);
            if (node.equals(start)) {
                current = checkPoint;
            }
            checkPoints.add(checkPoint);
        }
        if (vals.length > 0) {
            for (int i = 0; i < vals.length; i++) {
                double min = Double.MAX_VALUE;
                CheckPoint temp = null;
                for (CheckPoint checkPoint : checkPoints) {
                    System.out.println(checkPoint.node.getName() + " " + current.node.getName());
                    double val = checkPoint.assign(graph.getVal(current.node, checkPoint.node), current);
                    System.out.println(val);
                    if (val < min) {
                        min = val;
                        temp = checkPoint;
                    }
                }
                current.finalized = true;
                current = temp;
            }
        }
    }

    @Override
    public String toString() {
        String line = "";
        for (CheckPoint checkPoint : checkPoints) {
            System.out.print(checkPoint.node.getName() + " " + checkPoint.val);
            while (checkPoint.previous != null) {
                checkPoint = checkPoint.previous;
                System.out.println(" -> " + checkPoint.node.getName() + " " + checkPoint.val);
            }
            System.out.println("\n");
        }
        return "";
    }

    private static class CheckPoint {

        private CheckPoint previous;
        private Graph.Node node;
        private boolean finalized;
        private double val;

        public CheckPoint(Graph.Node node) {
            this.node = node;
        }

        private int getIndex() {
            return node.getIndex();
        }

        private double assign(double val, CheckPoint source) {
            if (finalized) {
                return Double.MAX_VALUE;
            }
            System.out.println("val = " + val);
            if (val != 0) {
                if (val < this.val || this.val == 0) {
                    this.val = val;
                    previous = source;
                }
            }
            return this.val;
        }
    }
}
