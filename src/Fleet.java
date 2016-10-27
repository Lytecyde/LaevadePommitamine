import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Fleet {
    LinkedList<Ship> ships = new LinkedList<Ship>();



    public Fleet(){
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));

        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(2));

        ships.add(new Ship(3));
        ships.add(new Ship(3));

        ships.add(new Ship(4));

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
        findNonSailingShip(s);
        //kontrollime kas seilab siinses laevastikus või võtame järgmise laeva
    }
    /*otsime mitteseilava laeva antud suurusega asukoha linkedlistis ships
    võtame linkedlistist välja leitud muutuja ja selle indexi ships linkedlistis
    */
    public void findNonSailingShip(Ship s){
        for(int i = 0; i < ships.size();i++) {
            if (ships.get(i).sailing == false && ships.get(i).size ==s.size) {
                ships.remove(i);
                ships.add(i, s);
                break;
            }
        }
    }
    //seame uute väärtustega ja lisame samale kohale
    public Ship setShipValues(Ship s, int i){
        Ship thisShip = new Ship(i);
        thisShip = ships.get(i);
        thisShip.sailing = s.sailing;
        thisShip.battlefieldSize = s.battlefieldSize;
        thisShip.coordinates = s.coordinates;
        thisShip.d = s.d;
        thisShip.heading = s.heading;
        thisShip.hits = s.hits;

        return thisShip;
    }

    public int findShipsBySize(int size){
        int count =0;
        for (int i =0; i < ships.size();i++){
            if(ships.get(i).size == size)count++;
        }
        return count;
    }

    public int totalPoints(){
        //TODO adjust this function for other fleetsizes !
        return findShipsBySize(1)*4 + findShipsBySize(2)*3 + findShipsBySize(3)*2 + findShipsBySize(4)*1;
    }
    public int size(){
        return ships.size();
    }
    public void printFleet(){
        System.out.println("Laevastik on:");
        int i = 0;
        for(Ship s : this.ships){
            System.out.println("Laev"+ i +" size:"+s.size+ " direction"+ s.heading +" coord"+s.coordinates[0]+"  "+s.coordinates[1]+ " hits" +s.hits
                    +" sailing" + s.sailing);
        }
    }
}
