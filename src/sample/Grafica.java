package sample;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class Grafica {
    public static HashMap<String, Image> grafica;

    public static void load() {
        File file = new File("src/sample/grafica");
            grafica = new HashMap<String, Image>();
            for(File obj : file.listFiles()) {
                Image newImage = new Image("sample/grafica/" + obj.getName());
                grafica.put(obj.getName(), newImage);
            }
    }
}
