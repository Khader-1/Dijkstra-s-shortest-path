package views.canvas;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 *
 * @author Khader
 */
public class Edge {

    private final static int PADDING_X = 10;
    private final static int PADDING_Y = 10;
    private final Line line;
    private final Label label;

    private Vertex from;
    private Vertex to;
    double val = 1;

    public Edge(Vertex from, Vertex to, double val) {
        line = new Line();
        line.setFill(Paint.valueOf("#0d7377"));
        label = new Label(val + "");
        label.setTextFill(Paint.valueOf("#fff"));
//        label.setStyle("-fx-background-color :  #212121; -fx-background-radius : 100;");
        this.from = from;
        this.to = to;
        this.val = val;
        from.layoutXProperty().addListener((observable) -> {
            resize();
        });
        from.layoutYProperty().addListener((observable) -> {
            resize();
        });
        to.layoutXProperty().addListener((observable) -> {
            resize();
        });
        to.layoutYProperty().addListener((observable) -> {
            resize();
        });
        label.setOnMouseClicked((event) -> {
            double x = label.getLayoutX() - 33;
            double y = label.getLayoutY() + event.getY() - 55;
            Canvas.viewEdge(x, y, this);
        });
        
        line.setOnMouseClicked((event) -> {
            double x = line.getLayoutX()+ event.getX() - 35;
            double y = line.getLayoutY()+ event.getY() - 50;
            Canvas.viewEdge(x, y, this);
        });
        label.setCursor(Cursor.HAND);
        line.setStrokeWidth(5);
    }

    public Node[] getShowable() {
        return new Node[]{label, line};
    }

    public void resize() {
        line.setStroke(Paint.valueOf("#0d7377"));
        line.setCursor(Cursor.HAND);
        line.setStartX(from.getLayoutX() + PADDING_X);
        line.setStartY(from.getLayoutY() + PADDING_Y);
        line.setEndX(to.getLayoutX() + PADDING_X);
        line.setEndY(to.getLayoutY() + PADDING_Y);
        label.setLayoutX((from.getLayoutX() + to.getLayoutX()) / 2 + 10);
        label.setLayoutY((from.getLayoutY() + to.getLayoutY()) / 2 + 10);
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
        label.setText(val + "");
    }

    public Line getLine() {
        return line;
    }

    public Label getLabel() {
        return label;
    }

    public void highlight() {
        line.setStroke(Paint.valueOf("#14ffec"));
    }

    public void unselect() {
        line.setStroke(Paint.valueOf("#0d7377"));
    }
}
