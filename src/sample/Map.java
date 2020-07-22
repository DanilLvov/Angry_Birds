package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

public class Map {

    private static ImageView map;
    private static SnapshotParameters parameters;
    private static Scale scale;

    public static void load() {
        map = new ImageView();
        parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Main.group.getChildren().add(map);
        scale = new Scale();
        scale.setX(0.1);
        scale.setY(0.1);
        map.getTransforms().add(scale);
    }
    public static void mapUpdate() {
        WritableImage snapshot = Sea.move.snapshot(parameters, null);
        map.setImage(snapshot);
        map.setLayoutX(976 - Main.group.getLayoutX());
        map.setLayoutY(60 - Main.group.getLayoutY());
    }

    public static void click(double x, double y) {
        if(map.boundsInParentProperty ().get ().contains (x, y)){
            double posX = -(( x - 54.64 + Main.group.getLayoutX () - 976) /(3840 * 0.1) * 3840);
            double posY = -(( y - 30.72 + Main.group.getLayoutY () - 60 )/(2160 * 0.1) * 2160);
            if (posX > 0) posX = 0.0;
            if (posY > 0) posY = 0.0;
            if (posX < -3840 + 1366) posX = -3840 + 1366;
            if (posY < -2160 + 768) posY = -2160 + 768;

            Main.group.setLayoutX(posX);
            Main.group.setLayoutY(posY);
            Sea.movePrimitiv(-posX, -posY);
            mapUpdate();
        }
    }
}
