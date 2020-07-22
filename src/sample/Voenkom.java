package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Voenkom {
    private static boolean k_pressed = false;
    public static void activation() {
        if(!k_pressed) {

            create();
        }
        else {
            endPriziv();
        }
    }

    private static void create() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Voenkom");

        Label label_1 = new Label("Хто буде военкомом?");
        label_1.setFont(new Font(20));
        ComboBox riven = new ComboBox();
        int i = 0;
        for (RedBird bird: Sea.birds) {
            riven.getItems().add(i + ": " + bird.getName() + " " + bird.getVyd());
            i++;
        }

        Button okButton = new Button("Confirm");
        okButton.setFont(new Font(30));

        okButton.setOnAction(event -> {
            String rivenStr = riven.getValue().toString();
            Sea.birds.get(Integer.parseInt(rivenStr.substring(0, 1))).youAreVoenkom();
            k_pressed = true;
            window.close();
        });

        VBox layout = new VBox(6);
        layout.getChildren().addAll
                (label_1, riven, okButton);

        Scene scene = new Scene(layout, 300, 270);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void endPriziv() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Весняний призив завершено");
        alert.setHeaderText(Sea.voenkom.inArmy_1 + "- першого рівня \n" +
                Sea.voenkom.inArmy_2 + "- другого рівня\n" +
                Sea.voenkom.inArmy_3 + "- третього рівня\n");

        alert.showAndWait();

        return;
    }
}
