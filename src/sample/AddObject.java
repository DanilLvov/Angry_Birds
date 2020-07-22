package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddObject {
    public static double x = 0;
    public static double y = 0;

    public static void displayMain() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Menu");

        Label label_0 = new Label("Pig or Bird?");
        label_0.setFont(new Font(20));
        RadioButton selectPig = new RadioButton("Pig");
        selectPig.setFont(new Font(20));
        RadioButton selectBird = new RadioButton("Bird");
        selectBird.setFont(new Font(20));

        ToggleGroup group = new ToggleGroup();
        selectPig.setToggleGroup(group);
        selectPig.setSelected(true);
        selectBird.setToggleGroup(group);

        Label label_1 = new Label("Riven");
        label_1.setFont(new Font(20));
        ComboBox riven = new ComboBox();
        riven.getItems().addAll("First", "Second", "Third");

        Label label_2 = new Label("Insert cords");
        label_2.setFont(new Font(20));
        TextField textField_1 = new TextField();
        textField_1.setFont(new Font(20));
        TextField textField_2 = new TextField();
        textField_2.setFont(new Font(20));

        Label label_3 = new Label("Insert name");
        label_3.setFont(new Font(20));
        TextField textField_3 = new TextField();
        textField_3.setFont(new Font(20));

        Button okButton = new Button("Confirm");
        okButton.setFont(new Font(30));

        okButton.setOnAction(event -> {

            RadioButton selection = (RadioButton) group.getSelectedToggle();
            x = Double.parseDouble(textField_1.getText());
            y = Double.parseDouble(textField_2.getText());
            String name = textField_3.getText();
            String rivenStr = riven.getValue().toString();
            String vyd =  selection.getText();
            Sea.addNew(rivenStr, x, y, vyd, name, Main.group);
            window.close();
        });

        VBox layout = new VBox(6);
        layout.getChildren().addAll
                (label_0, selectPig, selectBird, label_1, riven, label_2,
                        textField_1, textField_2, label_3, textField_3,
                        okButton);

        Scene scene = new Scene(layout, 400, 570);
        window.setScene(scene);
        window.showAndWait();
    }
}
