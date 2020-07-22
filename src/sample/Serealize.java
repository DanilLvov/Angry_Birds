package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serealize {
    static HashMap<Integer, ArrayList> map;
    static ArrayList<ImportantFields> arr = new ArrayList<>();
    static ArrayList<IslandFields> arr2 = new ArrayList<>();
    public static void serialize(String fileName) {

        for (RedBird bird: Sea.birds){
            arr.add(bird.fields);
        }

        for (Island island: Sea.islands){
            arr2.add(new IslandFields(island.getPoints(), island.getOwner()));
            System.out.println(island.getOwner());
        }

        map = new HashMap<>();
        map.put(1, arr);
        map.put(2, arr2);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserialization(String fileName) {
        for (RedBird bird: Sea.birds){
            bird.removeAll(Sea.move);
        }
        Sea.birds.clear();

        map = new HashMap<>();
        arr = new ArrayList<>();
        arr2 = new ArrayList<>();



        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            map = (HashMap<Integer, ArrayList>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        arr = map.get(1);
        arr2 = map.get(2);

        int i = 0;
        for(ImportantFields field: arr) {
            Sea.addNew(field.riven, field.x, field.y, field.vyd, field.name, Sea.move);
            if(field.active) {
                Sea.birds.get(i).swapActive();
            }
            Sea.birds.get(i).searchIsland = field.searchIsland;
            Sea.birds.get(i).otherReaction = field.otherReaction;
            Sea.birds.get(i).enemyAttack = field.enemyAttack;
            Sea.birds.get(i).capturingIsland = field.capturingIsland;
            Sea.birds.get(i).movingToIsland = field.movingToIsland;
            Sea.birds.get(i).capturingIsland = field.capturingIsland;
            Sea.birds.get(i).setLife(field.life);
            Sea.birds.get(i).setCircles(field.circles);
            Sea.birds.get(i).setIsland(field.index);
            Sea.birds.get(i).setCaptureX(field.captX);
            Sea.birds.get(i).setCaptureY(field.captY);
            i++;
        }
        i = 0;
        for (IslandFields field: arr2) {
            Sea.islands.get(i).setOwner(field.owner);
            Sea.islands.get(i).setCapturePoints(field.points);
            i++;
        }
    }
}
