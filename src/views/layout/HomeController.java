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
import javafx.scene.layout.AnchorPane;
import model.DijkstraImplementation;
import model.Graph;
import views.canvas.Canvas;

/**
 *
 * @author Khader
 */
public class HomeController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    JFXButton play, reset, selectStart;
    
    private DijkstraImplementation implementation;

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
            implementation.init(graph.getNode(0));
//            implementation.toString();
        });
        reset.setOnMouseClicked((event) -> {
            Canvas.reset();
        });
        selectStart.setOnMouseClicked((event) -> {
            if (implementation == null) {
                play.getOnMouseClicked().handle(event);
            }
        });
    }

}
