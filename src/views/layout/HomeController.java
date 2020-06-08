package views.layout;

import com.jfoenix.controls.JFXButton;
import static graph.Main.component;
import graph.Router;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
    Label startName, destName, cost, path, alert;

    private DijkstraImplementation implementation;

    @FXML
    VBox adjTable, data;

    private static FadeTransition view;
    private static FadeTransition hide;
    private static Label alertText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertText = alert;
        FadeTransition view = new FadeTransition(Duration.millis(1000), alert);
        view.setFromValue(0);
        view.setToValue(1.0);
        view.setCycleCount(1);
        HomeController.view = view;
        FadeTransition hide = new FadeTransition(Duration.millis(1000), alert);
        hide.setFromValue(1.0);
        hide.setToValue(0);
        hide.setCycleCount(1);
        HomeController.hide = hide;
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
            showAlert("Please select a start point");
            refrence.verteces.values().forEach((vertex) -> {
                vertex.setOnMouseClicked((event2) -> {
                    hideAlert();
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
            showAlert("Please select an end point");
            if (implementation == null) {
                play.getOnMouseClicked().handle(event);
            }
            refrence.verteces.values().forEach((vertex) -> {
                vertex.setOnMouseClicked((event2) -> {
                    hideAlert();
                    implementation.backtrac(vertex.getName(), startName, destName, cost, this.path);
                    refrence.verteces.values().forEach((v) -> {
                        v.setOnMouseClicked((newEvent) -> {
                            Canvas.viewOptions(v);
                        });
                    });
                });
            });
        });
    }

    public static void showAlert(String text) {
        alertText.setText(text);
        view.play();
    }

    public static void hideAlert() {
        hide.play();
    }
}
