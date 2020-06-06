package model;

import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    public void init(Graph.Node start, VBox data) {
        System.out.println(graph.toString());
        this.start = start;
        checkPoints = new HashSet();
        data.getChildren().clear();
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(0, 0, 0, 50));
        data.getChildren().add(row);
        for (Graph.Node node : graph.getNodes()) {
            CheckPoint checkPoint = new CheckPoint(node);
            if (node.equals(start)) {
                current = checkPoint;
            }
            checkPoints.add(checkPoint);
        }
        if (vals.length > 0) {
            System.out.print("   ");
            for (CheckPoint checkPoint : checkPoints) {
                System.out.print(checkPoint.node.getName() + "    ");
                row.getChildren().add(Graph.design(new Label(checkPoint.node.getName())));
            }
            System.out.println();
            for (int i = 0; i < vals.length; i++) {
                row = new HBox();
                row.setAlignment(Pos.CENTER);
                data.getChildren().add(row);
                row.getChildren().add(Graph.design(new Label(current.node.getName())));
                System.out.print(current.node.getName() + " ");
                double min = Double.MAX_VALUE;
                CheckPoint temp = null;
                int count = 0;
                for (CheckPoint checkPoint : checkPoints) {
                    double relative = graph.getVal(current.node, checkPoint.node);
                    double val = checkPoint.assign(relative == 0 ? 0 : current.val + relative, current);
                    String num = (val != Double.MAX_VALUE ? val + checkPoint.previous.node.getName() : "f");
                    System.out.print((val != Double.MAX_VALUE ? val + checkPoint.previous.node.getName() : " f  ") + " ");
                    row.getChildren().add(Graph.design(new Label(num)));
                    vals[i][count++] = val;
                    if (val < min) {
                        min = val;
                        temp = checkPoint;
                    }
                }
                System.out.println();
                if (current != null) {
                    current.finalized = true;
                }
                current = temp;
                if (current != null) {
                    current.finalized = true;
                }
            }
        }
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
            if (val != 0) {
                if (val < this.val || this.val == 0) {
                    this.val = val;
                    previous = source;
                }
            }
            return this.val == 0 ? Double.MAX_VALUE : this.val;
        }
    }
}
