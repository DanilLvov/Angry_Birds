package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static java.lang.Math.*;

public class RedBird implements Cloneable {
    protected double x, y;
    protected boolean active;
    protected boolean killed;
    protected boolean searchIsland;
    protected boolean movingToIsland;
    protected boolean enemyAttack;
    protected boolean capturingIsland;
    protected boolean exloded;
    protected boolean otherReaction;
    protected boolean voenkom;
    protected boolean onParade;
    protected String activeImage;
    protected String notActive;
    protected int riven;
    protected ImageView imageView;
    protected Text nameText;
    protected Rectangle lifeBar;
    protected Rectangle lifeBarBG;
    protected double speed;
    protected ImportantFields fields;
    protected RedBird enemyBird;

    private double kut;
    private Island captureIsland;
    private double captureX, captureY;
    private int life;
    private String name;
    private int circles;
    private String vyd;
    protected int inArmy_1 = 0;
    protected int inArmy_2 = 0;
    protected int inArmy_3 = 0;

    public int getIndex() {
        if(captureIsland != null)return Sea.islands.indexOf(captureIsland);
        else return 0;
    }
    public void setCircles(int circles) {this.circles = circles;}
    public String getVyd() {return vyd;}
    public double getX() { return x; }
    public double getY() { return y; }
    public String getName() { return name; }

    public void setCaptureX(double x) { captureX = x; }
    public void setCaptureY(double y) { captureY = y; }
    public void setIsland(int index) { captureIsland = Sea.islands.get(index); }

    public void setLife(int life) { this.life = life; lifeBar.setWidth(25 * life); }

    public void removeAll(Pane group) {
        if(!onParade)group.getChildren().removeAll(imageView, nameText, lifeBar, lifeBarBG);
    }

    public RedBird (double x, double y, String vyd, String name, Pane group) {
        this();
        this.x = x;
        this.y = y;
        this.vyd = vyd;
        this.name = name;

        if(vyd.equals("Bird")) {
            activeImage = "Red_active.png";
            notActive = "Red.png";
        }
        else {
            activeImage = "Pig_active.png";
            notActive = "Pig.png";
        }
        riven = 1;

        imageView = new ImageView(Grafica.grafica.get(notActive));
        imageView.setX(x);
        imageView.setY(y);
        lifeBar = new Rectangle(x, y - 20, 125, 23 );
        lifeBar.setFill(Color.RED);
        lifeBarBG = new Rectangle(x, y - 20, 125, 23 );
        lifeBarBG.setFill(Color.WHITE);

        nameText = new Text(name);
        nameText.setFont(Font.font("Verdana", 20));
        nameText.setFill(Color.BLACK);
        nameText.setX(x + 10);
        nameText.setY(y);

        fields = new ImportantFields(riven, vyd, name);
        group.getChildren().addAll(imageView, lifeBarBG, lifeBar, nameText);
    }

    public RedBird () {
        life = 5;
        active = false;
        searchIsland = true;
        movingToIsland = false;
        capturingIsland = false;
        otherReaction = false;
        enemyAttack = false;
        voenkom = false;
        onParade = false;

        killed = false;
        speed = 2.5;
        circles = 0;
    }

    public void youAreVoenkom() {
        voenkom = true;
        imageView.setImage(Grafica.grafica.get("Voenkom.png"));
        speed *= 2;
        Sea.voenkom = this;
        if(active) {
            active = !active;
        }
    }

    public void receiveDamage() {
        if (!onParade && !voenkom) {
            if (!otherReaction) {
                life--;
                lifeBar.setWidth(25 * life);

                if(life == 0) {
                    kill();
                }
            }
            else {
                kill();
            }
        }
    }

    public void receiveDamage(RedBird tmp) {
        if (!onParade && !voenkom) {
            tmp.receiveDamage();
            life--;
            lifeBar.setWidth(25 * life);

            if(life == 0) {
                kill();
            }
        }
    }

    public void kill() {
        if(!onParade && !voenkom) killed = true;
    }

    private void priziv() {
        double min = 10000;

        RedBird tmp = new RedBird();
        for (RedBird bird: Sea.birds) {
            if(bird != this && bird != enemyBird && !bird.exloded &&
                    vidstan(x, bird.getX(), y, bird.getY()) < min && !bird.onParade) {
                min = vidstan(x, bird.getX(), y, bird.getY());
                tmp = bird;
            }
        }

        if (min != 10000) {
            moveTo(tmp.getX(), tmp.getY());
            changeCords();
            if(vidstan(x, tmp.getX(), y, tmp.getY()) < 20) {
                enemyBird = tmp;
                enemyBird.toStroi();
            }
        }
    }

    private void toStroi() {

        if(riven == 1) {
            x = 0;
            y = 120 * Sea.voenkom.inArmy_1 + 20;
            Sea.voenkom.inArmy_1++;
        }
        if(riven == 2) {
            x = 0;
            y = 2160 - (120 * Sea.voenkom.inArmy_2) - 120;
            Sea.voenkom.inArmy_2++;
        }
        if(riven == 3) {
            x = 125 + 135 * Sea.voenkom.inArmy_3;
            y = 2160 - 120;
            Sea.voenkom.inArmy_3++;
        }
        changeCords();
        onParade = true;
    }

    public void interaction() {
        if(voenkom) {
            priziv();
        }
        else {
            if (!active && !exloded && !onParade) { //TODO: if something forbids interaction it goes here
                checkEnemy();
                checkForIsland();
                moveToIsland();
                captureIsland();
                changeCords();
            }
            if (active && !onParade) {
                for (Island island: Sea.islands) {
                    if (vidstan(x, island.getX(), y, island.getY()) < 230 * island.getScale()) {
                        movingToIsland = false;
                        capturingIsland = true;
                        captureX = island.getX() - 230 * island.getScale();
                        captureY = island.getY();
                        captureIsland = island;
                        swapActive();
                    }
                }
            }
            updateExplosion();
            fields.update(x, y, life, circles, active, searchIsland, otherReaction,
                    enemyAttack, capturingIsland, movingToIsland, getIndex(), captureX, captureY);
        }
    }

    protected void updateExplosion() {}

    protected void checkEnemy() {}

    protected void checkForIsland() {
        if (searchIsland) {

            double min = 10000;

            Island tmp = new Island();
            for (Island island: Sea.islands) {
                if(captureIsland != island && !island.getOwner().equals(vyd) &&
                            vidstan(x, island.getX(), y, island.getY()) < min) {
                    min = vidstan(x, island.getX(), y, island.getY());
                    tmp = island;
                }
            }

            if (min == 10000) {
                if(vyd == "Bird") {
                    captureIsland = Sea.birdIsland;
                    searchIsland = false;
                    movingToIsland = true;
                }
                else {
                    captureIsland = Sea.pigIsland;
                    searchIsland = false;
                    movingToIsland = true;
                }

            }
            else {
                captureIsland = tmp;
                searchIsland = false;
                movingToIsland = true;
            }
        }
    }

    protected void moveToIsland() {
        if (movingToIsland) {

            moveTo(captureIsland.getX(), captureIsland.getY());
            if (vidstan(x, captureIsland.getX(), y, captureIsland.getY()) < 230 * captureIsland.getScale()) {
                movingToIsland = false;
                capturingIsland = true;
                captureX = captureIsland.getX() - 230 * captureIsland.getScale();
                captureY = captureIsland.getY();
            }
        }
    }

    protected void captureIsland() {

        if (capturingIsland) {
            moveTo(captureX, captureY);

            if (circles == 4) {
                captureIsland.capture(vyd);
                circles = 0;
                if (!captureIsland.getOwner().equals(vyd) && !captureIsland.getOwner().equals("No owner")) {
                    receiveDamage();
                }

                if (otherReaction){
                    kill();
                }
                afterCapture();
            }
            else {
                if (vidstan(x, captureX, y, captureY) < 5) {
                    if(circles % 2 == 0) {
                        captureX += 4;
                        if(captureX >= captureIsland.getX() + 229 * captureIsland.getScale()) {
                            circles ++;
                        }
                        else {
                            captureY = sqrt (pow (230 * captureIsland.getScale(), 2)
                                    - pow(captureX - captureIsland.getX(), 2));
                            captureY += captureIsland.getY();
                        }
                    }
                    if(circles % 2 != 0) {
                        captureX -= 4;
                        if(captureX <= captureIsland.getX() - 229 * captureIsland.getScale()) {
                            circles ++;
                        }
                        else {
                            captureY = sqrt (pow (230 * captureIsland.getScale(), 2)
                                    - pow(captureX - captureIsland.getX(), 2));
                            captureY *= -1;
                            captureY += captureIsland.getY();
                        }
                    }
                }
            }
        }
    }

    protected void afterCapture() {
        capturingIsland = false;
        searchIsland = true;
    }

    @Override
    public RedBird clone() throws CloneNotSupportedException {
        RedBird tmp = (RedBird) super.clone();
        ImportantFields clonedFields = fields.clone();
        tmp.fields = clonedFields;
        return tmp;
    }

    @Override
    public String toString() {
        return String.format(vyd + " " + name);
    }

    public void swapReaction() {
        if(!onParade)
        otherReaction = !otherReaction;
    }
    protected void changeCords() {
        if(!active) {
            x += Math.sin(kut * (Math.PI / 180)) * speed;
            y -= Math.cos(kut * (Math.PI / 180)) * speed;
        }

        if(x > 1920 * 2 - 135) x = 1920 * 2 - 135;
        if(x < 10) x = 10;
        if(y > 1080 * 2 - 125) y = 1080 * 2 - 125;
        if(y < 30) y = 30;

        imageView.setX(x);
        imageView.setY(y);

        lifeBar.setX(x);
        lifeBar.setY(y - 20);
        lifeBarBG.setX(x);
        lifeBarBG.setY(y - 20);
        nameText.setX(x + 10);
        nameText.setY(y);
    }

    protected double vidstan (double x1, double x2, double y1, double y2) {
        return Math.sqrt (Math.pow ( (x1 - x2), 2) + Math.pow ( (y1 - y2), 2));
    }

    protected void moveTo(double x, double y) {
        kut = Math.atan2(y - this.y, x - this.x) * 180 / Math.PI + 90;
    }

    public boolean contains(double x, double y) {
        return imageView.boundsInParentProperty().get().contains(x, y);
    }

    public void swapActive() {
        if(!onParade && !voenkom) {
            if(active) {
                imageView.setImage(Grafica.grafica.get(notActive));
            }
            else {
                imageView.setImage(Grafica.grafica.get(activeImage));
            }
            active = !active;
        }
    }

    public void up() {
        if(!onParade && !voenkom) {
            y -= 20;
            changeCords();
        }
    }
    public void down() {
        if(!onParade && !voenkom) {
            y += 20;
            changeCords();
        }
    }
    public void left() {
        if(!onParade && !voenkom) {
            x -= 20;
            changeCords();
        }
    }
    public void right() {
        if(!onParade && !voenkom) {
            x += 20;
            changeCords();
        }
    }
}
