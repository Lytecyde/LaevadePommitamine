import java.util.LinkedList;
import java.util.Objects;

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
    private void add(Ship s){
        ships.add(s);
    }

    public void set(Ship s){
        //SIIN ON KOMISTUSKIVI
        //TODO: otsime laevastikust vaba laeva ja märgime sellele Ship s väärtused nii nagu määratud BattleWindowsis
        //kontrollime suurust
        int size = s.size;
        Ship thisShip ;
        int countShipsOfSize = 0;
        switch (size) {
            case 4:
                countShipsOfSize = 1;

                for(int i = 0; i < countShipsOfSize;i++) {
                    if (ships4.get(i).sailing == false) {
                        thisShip = ships4.get(i);
                        thisShip.sailing = s.sailing;
                        thisShip.battlefieldSize = s.battlefieldSize;
                        thisShip.coordinates = s.coordinates;
                        thisShip.d = s.d;
                        thisShip.heading = s.heading;
                        thisShip.hits = s.hits;
                    }
                }

                break;
            case 3:
                countShipsOfSize =2;
                for(int i = 0; i < countShipsOfSize;i++) {
                    if (ships3.get(i).sailing == false) {

                    }
                }
                break;
            case 2:
                countShipsOfSize =3;
                for(int i = 0; i < countShipsOfSize;i++) {
                    if (ships2.get(i).sailing == false) {

                    }
                }
                break;
            case 1:
                countShipsOfSize =4;
                for(int i = 0; i < countShipsOfSize;i++) {
                    if (ships1.get(i).sailing == false) {

                    }
                }
                break;
        }
        Ship nextFreeShip;


        //kontrollime kas seilab siinses laevastikus või võtame järgmise laeva
    }

    public int totalPoints(){
        //TODO adjust this function for other fleetsizes !
        return ships1.size()*4 + ships2.size()*3 + ships3.size()*2 + ships4.size()*1;
    }
    public int size(){
        return ships.size();
    }
}
