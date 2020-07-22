package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class YellowBird extends RedBird{

    public YellowBird(double x, double y, String vyd, String name, Pane group) {
        super(x, y, vyd, name, group);

        if(vyd.equals("Bird")) {
            activeImage = "Yellow_active.png";
            notActive = "Yellow.png";
        }
        else {
            activeImage = "Helmet_active.png";
            notActive = "Helmet.png";
        }
        imageView.setImage(Grafica.grafica.get(notActive));
        riven = 2;
        speed = 3;
        enemyAttack = true;
        searchIsland = false;
        fields = new ImportantFields(riven, vyd, name);
    }

    @Override
    protected void checkEnemy() {
        if (enemyAttack) {

            double min = 10000;

            RedBird tmp = new RedBird();
            for (RedBird bird: Sea.birds) {
                if(bird != this && !bird.getVyd().equals (this.getVyd()) &&
                        bird != enemyBird && !bird.onParade &&
                        vidstan(x, bird.getX(), y, bird.getY()) < min) {
                    min = vidstan(x, bird.getX(), y, bird.getY());
                    tmp = bird;
                }
            }

            if (min == 10000) {
                enemyAttack = false;
                searchIsland = true;
            }

            else {
                moveTo(tmp.getX(), tmp.getY());
                if(vidstan(x, tmp.getX(), y, tmp.getY()) < 10) {
                    enemyBird = tmp;
                    attack(tmp);
                }
            }
        }
    }

    protected void attack(RedBird tmp) {
        tmp.receiveDamage();
    }

    @Override
    protected void afterCapture() {
        capturingIsland = false;
        enemyAttack = true;
        enemyBird = new RedBird();
    }
}
