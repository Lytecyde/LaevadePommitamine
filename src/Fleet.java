import java.util.LinkedList;

/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Fleet {
                    //TODO all false muidu true ajutiselt
    static public boolean[] noShipsLeft = {false, false, false, false}; //sizes 1,2,3,4
    LinkedList<Ship> ships;


    public Fleet() {
        ships = new LinkedList<>();
        //vaike versioon 20 punktiga
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

    private void add(Ship s) {
        ships.add(s);
    }

    public void set(Ship s) {
        //SIIN ON KOMISTUSKIVI
        //TODO: otsime laevastikust vaba laeva ja märgime sellele Ship s väärtused nii nagu määratud BattleWindowsis
        //kontrollime suurust


        int index = findNonSailingShip(s);

        if (index == -1) {
            System.out.println("Ei ole laevu selles suuruses enam");
            noShipsLeft[s.size - 1] = true;
        } else {
            ships.remove(index);
            ships.add(index, s);
        }
        //kontrollime kas seilab siinses laevastikus või võtame järgmise laeva
    }

    /*otsime mitteseilava laeva antud suurusega asukoha linkedlistis ships
    võtame linkedlistist välja leitud muutuja ja selle indexi ships linkedlistis
    */
    public int findNonSailingShip(Ship s) {
        int searchedIndex = -1;//NO ships of that size

        for (int i = 0; i < ships.size(); i++) {

            if (ships.get(i).sailing == false && ships.get(i).size == s.size) {
                searchedIndex = i;
                if(isLongestShip(s.size)){
                    noShipsLeft[s.size-1] = true;
                }
                if(i+1 < ships.size()){
                    System.out.println("MSG1 : ei ole viimane laev" );
                    if (ships.get(i + 1).size != s.size ){
                        System.out.println("MSG2 : viimane laev on märgitud seilama");
                        noShipsLeft[s.size-1] = true;
                    }
                    else{}
                }else{}
                break;
            }
        }

        return searchedIndex;
    }

    private boolean isLongestShip(int s) {
        boolean longest = false;
        if (s == SeaConstants.longestShip)longest = true;
        return longest;
    }

    //seame uute väärtustega ja lisame samale kohale
    public Ship setShipValues(Ship s, int i) {
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

    public int findShipsBySize(int size) {
        int count = 0;
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).size == size) count++;
        }
        return count;
    }

    public int totalPoints() {
        //TODO adjust this function for other fleetsizes !
        return findShipsBySize(1) * 4 + findShipsBySize(2) * 3 + findShipsBySize(3) * 2 + findShipsBySize(4) * 1;
    }

    public int size() {
        return ships.size();
    }

    public void printFleet() {
        System.out.println("Laevastik on:");
        int i = 0;
        for (Ship s : this.ships) {
            System.out.println("Laev" + i + " size:" + s.size + " direction" + s.heading + " coord" + s.coordinates[0] + "  " + s.coordinates[1] + " hits" + s.hits
                    + " sailing" + s.sailing);
        }
    }

}
