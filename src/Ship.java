/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Ship {
    int size = 0;
    int heading = Direction.NORTHD.ordinal();
    int hits = 0;
    int battlefieldSize;
    int[][]  coordinates;
    boolean sailing = false;
    Direction d;
    public Ship(int size){
        this.size = size;
        setBattlefieldSize();
        coordinates = new int[battlefieldSize][2];
    }

    public void setDirection(Direction d){
        this.d = d;
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
