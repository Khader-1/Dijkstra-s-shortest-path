package views.canvas;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Graph;

public class Vertex extends VBox {

    private final static String FONT = "BULLSEYE";
    private final static Paint COLOR = Paint.valueOf("#0d7377");
    private final static String FONT_SIZE = "20";

    private String name;

    private final FontAwesomeIconView icon;
    private final Label label;
    private final ArrayList<Edge> edges;
    private Graph.Node node;

    public Vertex(String name) {
        edges = new ArrayList();
        this.name = name;
        label = new Label(name);
        label.setFont(new Font("Cairo", 20));
        label.setTextFill(COLOR);
        icon = new FontAwesomeIconView();
        icon.setSize(FONT_SIZE);
        icon.setGlyphName(FONT);
        icon.setFill(COLOR);
        this.getChildren().add(icon);
        this.getChildren().add(label);
        this.setAlignment(Pos.CENTER);
    }

    public void viewName() {
        label.setVisible(true);
    }

    public void hideName() {
        label.setVisible(false);
    }

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void deleteEdge(Edge edge) {
        edges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void unlink() {
        while (edges.size() > 0) {
            Canvas.deleteEdge(edges.get(0));
        }
    }

    public void setNode(Graph.Node node) {
        this.node = node;
    }

    public Graph.Node getNode() {
        return node;
    }

    public Edge getEdge (String name) {
        for (Edge edge : edges) {
            if (edge.getFrom().getName().equals(name) || edge.getTo().getName().equals(name)) {
                edge.highlight();
            }
        }
        return null;
    }
}
