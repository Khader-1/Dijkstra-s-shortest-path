package views.layout;

import com.jfoenix.controls.JFXButton;
import static graph.Main.component;
import graph.Router;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DijkstraImplementation;
import model.Graph;
import views.canvas.Canvas;
import static views.canvas.Canvas.*;

/**
 *
 * @author Khader
 */
public class HomeController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    JFXButton play, reset, selectStart, selectDest;
    @FXML
    Label startName, destName, cost, path;

    private DijkstraImplementation implementation;

    @FXML
    VBox adjTable, data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = (new Router().view(component("canvas/Canvas")));
            Node node = loader.load();
            pane.getChildren().add(node);
            AnchorPane.setTopAnchor(node, 100d);
            AnchorPane.setRightAnchor(node, 50d);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        play.setOnMouseClicked((event) -> {
            Graph graph = Canvas.store();
            DijkstraImplementation implementation = new DijkstraImplementation(graph);
            this.implementation = implementation;
            implementation.init(graph.getNode(0), data);
            graph.view(adjTable);
        });
        reset.setOnMouseClicked((event) -> {
            Canvas.reset();
        });
        selectStart.setOnMouseClicked((event) -> {
            refrence.verteces.values().forEach((vertex) -> {
                vertex.setOnMouseClicked((event2) -> {
                    Graph graph = Canvas.store();
                    implementation = new DijkstraImplementation(graph);
                    implementation.init(vertex.getNode(), data);
                    graph.view(adjTable);
                    refrence.verteces.values().forEach((v) -> {
                        v.setOnMouseClicked((newEvent) -> {
                            Canvas.viewOptions(v);
                        });
                    });
                });
            });
        });
        selectDest.setOnMouseClicked((event) -> {
            if (implementation == null) {
                play.getOnMouseClicked().handle(event);
            }
            refrence.verteces.values().forEach((vertex) -> {
                vertex.setOnMouseClicked((event2) -> {
                    implementation.backtrac(vertex.getName(), startName, destName, cost, path);
                    refrence.verteces.values().forEach((v) -> {
                        v.setOnMouseClicked((newEvent) -> {
                            Canvas.viewOptions(v);
                        });
                    });
                });
            });
        });
    }
}
