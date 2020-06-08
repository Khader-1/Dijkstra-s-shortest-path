package views.layout;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import static graph.Main.component;
import graph.Router;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

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

    private static Node progress;

    private final Map<JFXButton, FontAwesomeIconView> nav = new HashMap();

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
        ref(home, homeIcon, "layout/Home");
        ref(code, codeIcon, "code/Code");
        ref(info, infoIcon, "desc/Desc");
        ref(creators, creatorsIcon, "about/About");
        ref(off, offIcon, "layout/Home");
        off.setOnMouseClicked((event) -> {
            System.exit(0);
        });
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

    public static void select(JFXButton button, FontAwesomeIconView icon) {
        button.setStyle("-fx-background-radius : 10; -fx-border-color :  #0d7377; -fx-border-width :  0 0 3 0;");
        icon.setFill(Paint.valueOf("#0d7377"));
    }

    public static void unSelect(JFXButton button, FontAwesomeIconView icon) {
        button.setStyle("-fx-background-radius : 10; -fx-border-color :  #0d7377; -fx-border-width :  0 0 0 0;");
        icon.setFill(Paint.valueOf("#ffffff"));
    }

    public void ref(JFXButton button, FontAwesomeIconView icon, String path) {
        nav.put(button, icon);
        button.setOnMouseClicked((event) -> {
            nav.keySet().forEach((aButton) -> {
                if (aButton.equals(button)) {
                    select(button, nav.get(button));
                } else {
                    unSelect(aButton, nav.get(aButton));
                }
            });
            route(path);
        });
    }
}
