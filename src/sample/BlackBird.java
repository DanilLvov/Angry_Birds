package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlackBird extends YellowBird{
    private String explosion = "Boom.png";
    private int timer;
    public BlackBird(double x, double y, String vyd, String name, Pane group) {
        super(x, y, vyd, name, group);

        if(vyd.equals("Bird")) {
            activeImage = "Bomb_active.png";
            notActive = "Bomb.png";
        }
        else {
            activeImage = "King_active.png";
            notActive = "King.png";
        }
        imageView.setImage(Grafica.grafica.get(notActive));
        riven = 3;
        timer = 0;
        speed = 3.5;
        fields = new ImportantFields(riven, vyd, name);
    }

    @Override
    protected void attack (RedBird tmp) {
        if(!otherReaction) {
            exloded = true;
            active = false;
            imageView.setImage(Grafica.grafica.get(explosion));
            nameText.setText("");
            lifeBar.setFill(Color.TRANSPARENT);
            lifeBarBG.setFill(Color.TRANSPARENT);
            tmp.kill();
        }
        else receiveDamage(tmp);
    }

    @Override
    protected void updateExplosion() {
        if(exloded) {
            timer++;
            if(timer == 80) {
                kill();
            }
        }
    }

    @Override
    protected void afterCapture() {
        capturingIsland = false;
        enemyAttack = true;
        enemyBird = new RedBird();
    }
}
