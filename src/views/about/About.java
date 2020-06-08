package views.about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author Khader
 */
public class About implements Initializable{
    @FXML
    Circle main;
    @FXML
    Circle kjk;
    @FXML
    Circle kmk;
    @FXML
    Circle m;
    
    @FXML
    AnchorPane mainContainer, kmkContainer, kjkContainer, mmContainer;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bind(kmk, "kmk", kmkContainer);
        bind(kjk, "kjk", kjkContainer);
        bind(main, "drAkram", mainContainer);
        bind(m, "mm", mmContainer);
    }
    
    public static void bind(Circle circle, String path, AnchorPane container) {
        Image image = new Image(About.class.getResourceAsStream("/img/" + path + ".png"));
        circle.setFill(new ImagePattern(image));
        circle.setOnMouseEntered((event) -> {
            container.setVisible(true);
        });
        container.setOnMouseExited((event) -> {
            container.setVisible(false);
        });
    }
    
}
