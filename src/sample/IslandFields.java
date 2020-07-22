package sample;

import java.io.Serializable;

public class IslandFields implements Serializable {

    public int points;
    public String owner;

    @Override
    public ImportantFields clone() throws CloneNotSupportedException { return (ImportantFields) super.clone(); }

    public IslandFields(int points, String owner) {
        this.points = points;
        this.owner = owner;
    }
}
