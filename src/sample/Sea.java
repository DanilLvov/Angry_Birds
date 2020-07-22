package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Sea {
    private static ImageView imageView;
    private static Text text;
    private static Rectangle rect, rectSign, border;
    private static ImageView fon;

    public static ArrayList<Island> islands;
    public static Island birdIsland;
    public static Island pigIsland;
    public static ArrayList<RedBird> birds;
    public static Pane move;
    public static RedBird voenkom;

    private static boolean wasKilled;

    public static void load(Pane group) {
        islands = new ArrayList<>();
        birds = new ArrayList<>();
        move = new Pane();
        imageView = new ImageView(Grafica.grafica.get("Sea.png"));
        imageView.setFitWidth(1920 * 2);
        imageView.setFitHeight(1080 * 2);

        text = new Text ("Capture all Islands to win");
        text.setFont(Font.font("Verdana", 35));
        text.setFill(Color.BLACK);
        text.setX(420);
        text.setY(40);

        rect = new Rectangle(1406, 808);
        rect.setStrokeWidth(20);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setX(-20);
        rect.setY(-20);

        border = new Rectangle(3880, 2200);
        border.setStrokeWidth(20);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLUE);
        border.setX(-20);
        border.setY(-20);

        rectSign = new Rectangle(1376, 50);
        rectSign.setFill(Color.WHITE);
        rectSign.setX(0);
        rectSign.setY(0);

        fon = new ImageView(Grafica.grafica.get("Map.png"));
        fon.setX(976);
        fon.setY(60);
        move.getChildren().add(border);
        group.getChildren().addAll(imageView, move, fon, rectSign, text);
        birdIsland = new Island(60, 1080 - 256, move, 1);
        birdIsland.setOwner("BirdHome");
        pigIsland = new Island(1920 * 2 - 600, 1080 - 256, move, 1);
        pigIsland.setOwner("PigHome");

        islands.add (new Island (90, 90, move, 0.9));
        islands.add (new Island (3000, 400, move, 0.5));
        islands.add (new Island (1279, 1300, move, 0.65));
        islands.add (new Island (600, 700, move, 0.5));
        islands.add (new Island (1900, 230, move, 0.8));
        islands.add (new Island (2300, 1400, move, 0.65));
        islands.add (new Island (1000, 130, move, 0.7));
        islands.add (new Island (1600, 900, move, 0.85));

        Map.load();
        move.getChildren().addAll(rect);
        birds.add (new RedBird(40, 40, "Bird", "Vasia", move));
        birds.add (new YellowBird(40, 40, "Bird", "Andrii", move));
        birds.add (new RedBird(820, 330, "Pig", "Opach", move));
        birds.add (new RedBird(920, 370, "Pig", "Opak", move));
        birds.add (new BlackBird(830, 290, "Bird", "Musa", move));
    }

    public static void one() {
        birds.add (new RedBird(60, 1080 - 256, "Bird", "Bird", move));
    }
    public static void two() {
        birds.add (new YellowBird(60, 1080 - 256, "Bird", "Bird", move));
    }
    public static void three() {
        birds.add (new BlackBird(60, 1080 - 256, "Bird", "Bird", move));
    }
    public static void four() {
        birds.add (new RedBird(1920 * 2 - 600, 1080 - 256, "Pig", "Pig", move));
    }
    public static void five() {
        birds.add (new YellowBird(1920 * 2 - 600, 1080 - 256, "Pig", "Pig", move));
    }
    public static void six() {
        birds.add (new BlackBird(1920 * 2 - 600, 1080 - 256, "Pig", "Pig", move));
    }

    private static int timer = 19;
    public static void animation() {
        timer++;
        if(timer == 20){
            Map.mapUpdate();
            timer = 0;
        }

        for (RedBird bird: birds) {
            bird.interaction();
            if(bird.killed) {
                wasKilled = true;
            }
        }
        if (wasKilled) {
            wasKilled = false;
            ArrayList<RedBird> array = new ArrayList<>();
            for (RedBird bird: birds) {
                if(bird.killed) bird.removeAll(move);
                else try {
                    array.add(bird.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            birds = array;
        }

        Island[] cloned = new Island[islands.size()];

        for (int i = 0; i < islands.size(); i++) {
            try {
                cloned[i] = islands.get(i).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Island[] copied = Arrays.copyOf(cloned, cloned.length);
        Arrays.sort(copied, Island.islandNameCompare);
        if (copied[0].getOwner().equals("Pig")) {
            text.setText("Pigs win");
        }
        if(copied[islands.size() - 1].getOwner().equals("Bird")) {
            text.setText("Birds win");
        }
    }

    public static void addNew(String riven, double x, double y, String vyd, String name, Pane group) {

        if(riven.equals("First")){
            birds.add (new RedBird(x, y, vyd, name, move));
        }
        if(riven.equals("Second")){
            birds.add (new YellowBird(x, y, vyd, name, move));
        }
        if(riven.equals("Third")){
            birds.add (new BlackBird(x, y, vyd, name, move));
        }
    }

    public static void mouse (double x, double y) {
        for (RedBird bird: birds) {
            if(!bird.capturingIsland && !bird.exloded) {
                if (bird.contains(x, y)) {
                    bird.swapActive();
                }
            }
        }
        Map.click(x, y);
    }

    public static void movePrimitiv (double x, double y) {
        rect.setX(x - 20);
        rect.setY(y - 20);
        text.setX(x + 420);
        text.setY(y + 40);
        rectSign.setX(x);
        rectSign.setY(y);
        fon.setX(x + 976);
        fon.setY(y + 60);
    }

    public static void up() {
        for (RedBird bird: birds) {
            if(bird.active)
                bird.up();
        }
    }
    public static void down() {
        for (RedBird bird: birds) {
            if(bird.active)
                bird.down();
        }
    }
    public static void left() {
        for (RedBird bird: birds) {
            if(bird.active)
                bird.left();
        }
    }
    public static void right() {
        for (RedBird bird: birds) {
            if(bird.active)
                bird.right();
        }
    }
    public static void deactivate() {
        for (RedBird bird: birds) {
            if(bird.active) {
                bird.swapActive();
            }
        }
    }
    public static void delete() {
        ArrayList<RedBird> array = new ArrayList<>();
        for (RedBird bird: birds) {
            if(bird.active) bird.removeAll(move);
            else array.add(bird);
        }
        birds = array;
    }
    public static void other() {
        for (RedBird bird: birds) {
            bird.swapReaction();
        }
    }

    public static void leaveIsland() {
        for (RedBird bird: birds) {
            bird.afterCapture();
        }
    }

}
