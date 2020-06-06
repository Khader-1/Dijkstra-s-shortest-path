package graph;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Khader
 */
public class Main extends Application {

    private static Stage stage;
    final private static Router ROUTER = new Router();
    final private static String COMPONENTS_PATH = "/views/";


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = (new Router().view(component("layout/Layout")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
        stage.setOpacity(1);
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.centerOnScreen();
        Main.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Router getRouter() {
        return ROUTER;
    }

    public static Parent view(String name) {
        System.out.println(name);
        try {
            return ROUTER.view(name).load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static FXMLLoader getResourse(String path) {
        try {
            return ROUTER.view(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void alert(String path) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = (new Router().view(path));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setIconified(false);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void route(String path) {
        FXMLLoader loader;
        try {
            loader = (new Router().view(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(850);
            stage.centerOnScreen();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String component(String path) {
        path = COMPONENTS_PATH + path;
        return path;
    }    
}
