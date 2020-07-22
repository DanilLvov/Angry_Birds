package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Comparator;

public class Island implements Serializable, Cloneable, Comparable<Island> {
    private Text sign;
    private Text captureSign;

    private double x, y, scale;
    private String owner = "No owner";
    private int capturePoints;

    public double getX() { return x; }
    public double getY() { return y; }
    public double getScale() { return scale; }
    public String getOwner() { return owner; }
    public int getPoints() { return capturePoints; }

    public void setCapturePoints(int points) {
        capturePoints = points;
        captureSign.setText(String.valueOf(capturePoints));
    }
    public void setOwner(String owner) {
        this.owner = owner;
        sign.setText(owner);
        capturePoints = 5;
        captureSign.setText(String.valueOf(capturePoints));
    }

    public Island (double x, double y, Pane group, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        capturePoints = 5;

        ImageView imageView = new ImageView(Grafica.grafica.get("Island.png"));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(512 * scale);
        imageView.setFitHeight(512 * scale);

        Rectangle signBG = new Rectangle(x, y, 190 * scale, 50 * scale);
        signBG.setFill(Color.SANDYBROWN);

        sign = new Text("No owner");
        sign.setFont(Font.font("Verdana", 25  * scale));
        sign.setFill(Color.BLACK);
        sign.setX(x + 10 * scale);
        sign.setY(y + 40 * scale);

        captureSign = new Text (String.valueOf (capturePoints));
        captureSign.setFont(Font.font("Verdana", 25  * scale));
        captureSign.setFill(Color.BLACK);
        captureSign.setX(x + 150 * scale);
        captureSign.setY(y + 40 * scale);

        group.getChildren().addAll(imageView, signBG, sign, captureSign);
        this.x += 190 * scale;
        this.y += 190 * scale;
    }
    public Island() {}

    public void capture(String owner) {
        if(!this.owner.equals("PigHome") && !this.owner.equals("BirdHome") && !this.owner.equals(owner)){
            capturePoints--;
            if(capturePoints == 0) {
                sign.setText(owner);
                this.owner = owner;
                capturePoints = 5;
            }
            captureSign.setText(String.valueOf(capturePoints));
        }
    }

    @Override
    public Island clone() throws CloneNotSupportedException {
        return (Island) super.clone();
    }

    @Override
    public int compareTo(Island island) {
        return owner.compareTo(island.owner);
    }

    public static Comparator <Island> islandNameCompare
            = (island_1, island_2) -> {
                String name1 = island_1.owner.toUpperCase();
                String name2 = island_2.owner.toUpperCase();
                return name1.compareTo(name2);
            };
}
