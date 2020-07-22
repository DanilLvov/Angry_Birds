package sample;

import java.io.Serializable;

public class ImportantFields implements Cloneable, Serializable {

    public double x, y, captX, captY;
    public boolean active, searchIsland, otherReaction;
    public boolean enemyAttack, capturingIsland, movingToIsland;
    public int life, circles, index;
    public String riven, name, vyd;

    public ImportantFields (int riven, String vyd, String name) {
        if(riven == 1){
            this.riven = "First";
        }
        if(riven == 2){
            this.riven = "Second";
        }
        if(riven == 3){
            this.riven = "Third";
        }

        this.name = name;
        this.vyd = vyd;
    }

    @Override
    public ImportantFields clone() throws CloneNotSupportedException { return (ImportantFields) super.clone(); }

    public void update (double x, double y, int life, int circles,
                        boolean active, boolean searchIsland, boolean otherReaction,
                        boolean enemyAttack, boolean capturingIsland, boolean movingToIsland,
                        int index, double captX, double captY) {
        this.x = x;
        this.y = y;
        this.life = life;
        this.circles = circles;
        this.active = active;
        this.searchIsland = searchIsland;
        this.otherReaction = otherReaction;
        this.enemyAttack = enemyAttack;
        this.capturingIsland = capturingIsland;
        this.movingToIsland = movingToIsland;
        this.index = index;
        this.captX = captX;
        this.captY = captY;
    }

}
