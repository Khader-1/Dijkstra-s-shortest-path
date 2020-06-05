package views.layout;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Khader
 */
public class LayoutController implements Initializable {

    public static LayoutController reference;
    @FXML
    private AnchorPane main;
    @FXML
    private AnchorPane outer;
    @FXML
    private VBox container;
    @FXML
    private JFXButton home;
    @FXML
    private FontAwesomeIconView homeIcon;
    @FXML
    private JFXButton info;
    @FXML
    private FontAwesomeIconView infoIcon;
    @FXML
    private JFXButton code;
    @FXML
    private FontAwesomeIconView codeIcon;
    @FXML
    private JFXButton creators;
    @FXML
    private FontAwesomeIconView creatorsIcon;
    @FXML
    private JFXButton off;
    @FXML
    private FontAwesomeIconView offIcon;
    @FXML
    private AnchorPane alert;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reference = this;
        route("layout/Home");
    }

    public static void route(String path) {
        try {
            FXMLLoader loader = (new Router().view(component(path)));
            Node node = loader.load();
            reference.container.getChildren().clear();
            reference.container.getChildren().add(node);
            VBox.setVgrow(node, Priority.ALWAYS);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
