import java.util.LinkedList;

/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Fleet {
    LinkedList<Ship> ships = new LinkedList<Ship>();
    LinkedList<Ship> ships1 = new LinkedList<Ship>();
    LinkedList<Ship> ships2 = new LinkedList<Ship>();
    LinkedList<Ship> ships3 = new LinkedList<Ship>();
    LinkedList<Ship> ships4 = new LinkedList<Ship>();

    public Fleet(){
        ships1.add(new Ship(1));
        ships1.add(new Ship(1));
        ships1.add(new Ship(1));
        ships1.add(new Ship(1));

        ships2.add(new Ship(2));
        ships2.add(new Ship(2));
        ships2.add(new Ship(2));

        ships3.add(new Ship(3));
        ships3.add(new Ship(3));

        ships4.add(new Ship(4));
        group();
    }
    public void group(){
        ships.addAll(ships1);
        ships.addAll(ships2);
        ships.addAll(ships3);
        ships.addAll(ships4);
    }
    public void add(Ship s){
        ships.add(s);
    }
    public int totalPoints(){
        //TODO adjust this function for other fleetsizes !
        return ships1.size()*4 + ships2.size()*3 + ships3.size()*2 + ships4.size()*1;
    }
}
