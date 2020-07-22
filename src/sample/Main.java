package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Pane group;
    public static Scene scene;
    static FileChooser fileChooser = new FileChooser();
    public static File file;

    @Override
    public void start(Stage primaryStage) throws Exception{
        group = new Pane();
        primaryStage.setTitle("AngryBirds");
        primaryStage.setResizable(false);
        scene = new Scene(group, 1366, 768);
        Grafica.load();
        Sea.load(group);



        Main.scene.setOnMouseClicked(event -> {
            Sea.mouse(event.getX() - group.getLayoutX(), event.getY() - group.getLayoutY());
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                Sea.up();
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                Sea.down();
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                Sea.left();
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                Sea.right();
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                Sea.deactivate();
            }

            if (event.getCode().equals(KeyCode.DELETE)) {
                Sea.delete();
            }
            if (event.getCode().equals(KeyCode.INSERT)) {
                AddObject.displayMain();
            }

            if (event.getCode().equals(KeyCode.Q)) {
                Sea.other();
            }

            if (event.getCode().equals(KeyCode.E)) {
                Sea.leaveIsland();
            }

            if (event.getCode().equals(KeyCode.K)) {
                Voenkom.activation();
            }

            if (event.getCode().equals(KeyCode.DIGIT1)) {
                Sea.one();
            }
            if (event.getCode().equals(KeyCode.DIGIT2)) {
                Sea.two();
            }
            if (event.getCode().equals(KeyCode.DIGIT3)) {
                Sea.three();
            }
            if (event.getCode().equals(KeyCode.DIGIT4)) {
                Sea.four();
            }
            if (event.getCode().equals(KeyCode.DIGIT5)) {
                Sea.five();
            }
            if (event.getCode().equals(KeyCode.DIGIT6)) {
                Sea.six();
            }

            double x = group.getLayoutX();
            double y = group.getLayoutY();
            if (event.getCode().equals(KeyCode.W)) {
                y += 200;
                if (y > 0) y = 0.0;
                group.setLayoutX(x);
                group.setLayoutY(y);
                Sea.movePrimitiv(-x, -y);
                Map.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.A)) {
                x += 200;
                if (x > 0) x = 0.0;
                group.setLayoutX(x);
                group.setLayoutY(y);
                Sea.movePrimitiv(-x, -y);
                Map.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.S)) {
                y -= 200;
                if (y < -2160 + 768) y = -2160 + 768;
                group.setLayoutX(x);
                group.setLayoutY(y);
                Sea.movePrimitiv(-x, -y);
                Map.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.D)) {
                x -= 200;
                if (x < -3840 + 1366) x = -3840 + 1366;
                group.setLayoutX(x);
                group.setLayoutY(y);
                Sea.movePrimitiv(-x, -y);
                Map.mapUpdate();
            }

            if (event.getCode().equals(KeyCode.R)) {
                file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    Serealize.serialize(file.getAbsolutePath());
                }
            }
            if (event.getCode().equals(KeyCode.T)) {
                file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    Serealize.deserialization(file.getAbsolutePath());
                }
            }

        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Sea.animation();
            }
        };

        timer.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
