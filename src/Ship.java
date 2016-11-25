/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Ship {
    int size = 0;
    int heading = Direction.NORTHD.ordinal();
    int hits = 0;
    int battlefieldSize;
    int[]  coordinates;
    int[][] allCoordinates;
    int index =  -1;
    boolean sunk = false;
    boolean sailing = false;
    Direction d;
    public Ship(int size){
        this.size = size;
        setBattlefieldSize();
        coordinates = new int[2];

    }

    public void setDirection(Direction d){
        this.d = d;
    }

    public Ship(int size, int direction, int[] coordinates, int hits, boolean sailing){
        this.size = size;
        this.heading =  direction;
        this.coordinates = coordinates;
        this.allCoordinates = new int[2][size];
        this.hits = hits;
        this.sailing = sailing;
        int[] direct = BattleWindow.directionsForAdjency(direction);
        allCoordinates = formAllCoordinates(size, direct ,coordinates);

    }

    public void incrementHits(){
        if(hits < size) hits++;
        else{
            BattleWindow.currentPlayer.getPlayerFleet().fleetHitPoints -=hits;
            sunk = true;

        }
    }

    public int[][] formAllCoordinates(int size, int[] direct, int[] coordinates){
        int[][] allCoordinates = new int[2][size];
        System.out.println("starting to create the coordinates for a ship " + size);
        for(int d = 0;d < size;d++) {
            int mx = (d*direct[1])+coordinates[0];
            int my = (d*direct[0])+coordinates[1];

            for (int x = mx; x < mx + 1 ; x++) {
                for (int y = my; y < my + 1; y++) {
                    allCoordinates[0][d] = x;
                    allCoordinates[1][d] = y;
                    System.out.println("allCoordinates::x"+ x+"y"+ y);
                }
            }
        }
        return allCoordinates;
    }

    public void setHeading(Direction d){
        setDirection(d);
        switch (d) {
            case NORTHD :
                heading =0;
                break;
            case EASTD :
                heading = 1;
                break;
            case SOUTHD :
                heading = 2;
                break;
            case WESTD :
                heading = 3;
                break;
            default : heading = 0;
        }
    }


    public void setBattlefieldSize(){
        BattleWindow.getFieldSize();
    }

    public enum Direction{
        NORTHD,
        EASTD,
        SOUTHD,
        WESTD
    }
}
