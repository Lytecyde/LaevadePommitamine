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
    }
    public void incrementHits(){
        this.hits++;
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

    public void setAllCoordinates(int[] direct, int size){
        for (int d = 0; d < size; d++) {
            int mx = (d * direct[1]) + coordinates[0];
            int my = (d * direct[0]) + coordinates[1];
            for (int x = mx; x < mx + 1; x++) {
                for (int y = my; y < my + 1; y++) {

                }
            }
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
