package model;

import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import views.canvas.Canvas;
import views.canvas.Edge;
import views.canvas.Vertex;

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
                Label minCell = null;
                int count = 0;
                for (CheckPoint checkPoint : checkPoints) {
                    double relative = graph.getVal(current.node, checkPoint.node);
                    double val = checkPoint.assign(relative == 0 ? 0 : current.val + relative, current);
                    String prev = checkPoint.previous == null ? "" : checkPoint.previous.node.getName();
                    String num = checkPoint.finalized || checkPoint.node.equals(start) ? checkPoint.val + prev : (checkPoint.val == 0 ? "--" : val + prev);
                    System.out.print((val != Double.MAX_VALUE ? val + checkPoint.previous.node.getName() : " f  ") + " ");
                    Label cell = Graph.design(new Label(num));
                    if (checkPoint.finalized || checkPoint.node.equals(start)) {
                        cell.setStyle("-fx-background-color : #323232; -fx-border-color : #0d7377;");
                    }
                    row.getChildren().add(cell);
                    vals[i][count++] = val;
                    if (val < min) {
                        min = val;
                        temp = checkPoint;
                        minCell = cell;
                    }
                }
                System.out.println();
                if (current != null) {
                    current.finalized = true;
                }
                current = temp;
                if (current != null) {
                    current.finalized = true;
                    minCell.setTextFill(Paint.valueOf("#fff"));
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

    public void backtrac(String name, Label... labels) {
        labels[1].setText(name);
        labels[0].setText(start.getName());
        labels[3].setText(name);
        for (Vertex vertex : Canvas.refrence.verteces.values()) {
            for (Edge edge : vertex.getEdges()) {
                edge.unselect();
            }
        }
        for (CheckPoint checkPoint : checkPoints) {
            if (checkPoint.node.getName().equals(name)) {
                System.out.print(checkPoint.node.getName());
                String temp = checkPoint.node.getName();
                labels[2].setText(checkPoint.val + "");
                while (true) {
                    if (checkPoint.previous == null) {
                        break;
                    }
                    checkPoint = checkPoint.previous;
                    String currentName = checkPoint.node.getName();
                    Canvas.refrence.verteces.get(temp).getEdge(currentName);
                    labels[3].setText(currentName + " -> " + labels[3].getText());
                    temp = currentName;
                    System.out.print(" -> " + checkPoint.node.getName());
                }
                System.out.println();
                break;
            }
        }
    }
}
