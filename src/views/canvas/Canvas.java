package views.canvas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Graph;
import views.layout.HomeController;

public class Canvas implements Initializable {

    public static Canvas refrence;

    @FXML
    Pane canvas;
    @FXML
    JFXButton vertex, move, delete, link, unlink, unlinkOne, change, selectStart;
    @FXML
    VBox container;
    @FXML
    AnchorPane options, edgeMenu, text;
    @FXML
    HBox out;
    @FXML
    JFXTextField value;

    private Vertex focused;
    private Edge focsedEdge;

    public final Map<String, Vertex> verteces = new HashMap();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refrence = this;
        vertex.setOnMouseClicked((event) -> {
            unselectEdges();
            hideOptions();
            String name = null;
            for (char i = 0; i < (char) 24; i++) {
                char temp = (char) ('a' + i);
                if (temp > 'z') {
                    return;
                } else {
                    if (!verteces.containsKey(temp + "")) {
                        name = temp + "";
                        break;
                    }
                }
            }
            if (name != null) {
                Vertex v = new Vertex(name);
                canvas.getChildren().add(v);
                v.setLayoutX(event.getX() + 530);
                v.setLayoutY(event.getY() + 20);
                verteces.put(name, v);
                move(v);
            }
        });
        move.setOnMouseClicked((event) -> {
            move(focused);
            hideOptions();
        });
        delete.setOnMouseClicked((event) -> {
            unselectEdges();
            focused.unlink();
            verteces.remove(focused.getName());
            canvas.getChildren().remove(focused);
            hideOptions();
        });
        link.setOnMouseClicked((event) -> {
            unselectEdges();
            hideOptions();
            HomeController.showAlert("Choose a vertex to connect with " + focused.getName());
            verteces.values().stream().filter((vertex) -> (!vertex.equals(focused))).forEachOrdered((vertex) -> {
                vertex.setOnMouseClicked((toEvent) -> {
                    boolean check = true;
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getFrom().equals(focused) || edge.getTo().equals(focused)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        HomeController.hideAlert();
                        Edge edge = new Edge(focused, vertex, 1.0);
                        edge.resize();
                        canvas.getChildren().addAll(edge.getShowable());
                        for (Node node : edge.getShowable()) {
                            node.toBack();
                        }
                        focused.addEdge(edge);
                        vertex.addEdge(edge);
                    }
                    verteces.values().forEach((v) -> {
                        v.setOnMouseClicked((backEvent) -> {
                            viewOptions(v);
                        });
                    });
                });
            });
        });
        unlinkOne.setOnMouseClicked((event) -> {
            unselectEdges();
            deleteEdge(focsedEdge);
            hideOptions();
        });
        change.setOnMouseClicked((event) -> {
            viewChange();
        });
        value.setOnKeyPressed((event) -> {
            unselectEdges();
            if (event.getCode().equals(KeyCode.ENTER)) {
                hideOptions();
                if ("".equals(value.getText()) || !(value.getText().matches("\\d*\\.?\\d*"))) {
                    System.out.println("error");
                } else {
                    double val = Double.parseDouble(value.getText());
                    System.out.println(val);
                    if (val <= 0) {
                        System.out.println("error");
                    } else {
                        focsedEdge.setVal(val);
                    }
                }
            }
        });
        unlink.setOnMouseClicked((event) -> {
            focused.unlink();
            hideOptions();
        });
    }

    public static void move(Vertex v) {
        Pane canvas = refrence.canvas;
        canvas.setCursor(Cursor.NONE);
        refrence.container.setVisible(false);
        canvas.setOnMouseMoved((canvasEvent) -> {
            v.setLayoutX(Math.min(canvasEvent.getX(), canvas.getWidth() - v.getWidth()));
            v.setLayoutY(Math.min(canvasEvent.getY(), canvas.getHeight() - v.getHeight()));
        });
        v.setOnMouseClicked((Vertexevent) -> {
            canvas.setOnMouseMoved(null);
            canvas.setCursor(Cursor.DEFAULT);
            refrence.container.setVisible(true);
            v.setOnMouseClicked((event) -> {
                Canvas.viewOptions(v);
            });
        });
    }

    public static void viewOptions(Vertex vertex) {
        if (refrence.focused != null) {
            if (refrence.focused.equals(vertex) && refrence.options.isVisible()) {
                hideOptions();
                return;
            }
        }
        hideOptions();
        refrence.container.setVisible(false);
        refrence.focused = vertex;
        refrence.options.setVisible(true);
        refrence.options.setLayoutX(vertex.getLayoutX() - 62);
        refrence.options.setLayoutY(vertex.getLayoutY() - 50);
        refrence.options.toFront();
        refrence.value.requestFocus();
        refrence.out.setOnMouseClicked((event) -> {
            refrence.out.setOnMouseClicked((event2) -> {
                hideOptions();
            });
        });
    }

    public static void viewEdge(double x, double y, Edge edge) {
        if (refrence.edgeMenu.isVisible()) {
            hideOptions();
        } else {
            hideOptions();
            AnchorPane menu = refrence.edgeMenu;
            menu.setVisible(true);
            menu.toFront();
            menu.setLayoutX(x);
            menu.setLayoutY(y);
            refrence.focsedEdge = edge;
            refrence.container.setVisible(false);
        }
        refrence.out.setOnMouseClicked((event) -> {
            refrence.out.setOnMouseClicked((event2) -> {
                hideOptions();
            });
        });
    }

    public static void hideOptions() {
        refrence.edgeMenu.setVisible(false);
        refrence.edgeMenu.setVisible(false);
        refrence.options.setVisible(false);
        refrence.text.setVisible(false);
        refrence.container.setVisible(true);
        refrence.out.setOnMouseClicked(null);
    }

    public static void deleteEdge(Edge edge) {
        edge.getFrom().getEdges().remove(edge);
        edge.getTo().getEdges().remove(edge);
        refrence.canvas.getChildren().removeAll(edge.getShowable());
        unselectEdges();
    }

    private static void viewChange() {
        hideOptions();
        Label label = refrence.focsedEdge.getLabel();
        refrence.text.setLayoutX(label.getLayoutX() - 23);
        refrence.text.setLayoutY(label.getLayoutY() - 50);
        refrence.text.setVisible(true);
        refrence.text.toFront();
        refrence.value.requestFocus();
        refrence.value.setText(label.getText());
    }

    public static Graph store() {
        unselectEdges();
        int length = refrence.verteces.size();
        Graph graph = new Graph(length);
        Set<String> names = refrence.verteces.keySet();
        names.forEach((name) -> {
            refrence.verteces.get(name).setNode(graph.addNode(name));
        });
        refrence.verteces.values().forEach((vertex) -> {
            vertex.getEdges().forEach((edge) -> {
                graph.addEdge(edge.getFrom().getNode(), edge.getTo().getNode(), edge.getVal());
            });
        });
        return graph;
    }

    public static void reset() {
        Vertex[] verteces = new Vertex[refrence.verteces.size()];
        verteces = refrence.verteces.values().toArray(verteces);
        for (Vertex vertex : verteces) {
            delete(vertex);
        }
        hideOptions();
    }

    public static void delete(Vertex vertex) {
        vertex.unlink();
        unselectEdges();
        refrence.verteces.remove(vertex.getName());
        refrence.canvas.getChildren().remove(vertex);
    }

    public static void unselectEdges() {
        refrence.verteces.values().forEach((value) -> {
            value.getEdges().forEach((edge) -> {
                edge.unselect();
            });
        });
    }
}
