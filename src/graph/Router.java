package graph;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;

public class Router {

    public FXMLLoader view(String path) throws IOException {
        return new FXMLLoader(getClass().getResource(path + ".fxml"));
    }

    public URL getClass(String path) throws IOException {
        return (getClass().getResource(path + ".class"));
    }

    public FXMLLoader route(String path) throws IOException {
        return new FXMLLoader(getClass().getResource("components/" + path + ".fxml"));
    }

    public URL ComponentClass(String path) {
        return (getClass().getResource("components/" + path + ".class"));
    }
}
