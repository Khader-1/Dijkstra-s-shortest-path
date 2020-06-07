package views.about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public static void viewImage(Circle circle, String path) {
        Image image = new Image(About.class.getResourceAsStream("/img/code1.png"));
        circle.setFill(new ImagePattern(image));
    }
    
}
