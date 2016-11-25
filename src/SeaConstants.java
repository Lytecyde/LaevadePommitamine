/**
 * Created by mik.seljamaa on 25.10.2016.
 */
public class SeaConstants {
    final static public int SEA =0;
    final static public int SHIP = 1;
    final static public int ADJACENT_TO_SHIP = 2;
    final static public int SHIP_AFLAME = 3;

    final static int shipSize1Count = 4;
    final static int shipSize2Count = 3;
    final static int shipSize3Count = 2;
    final static int shipSize4Count = 1;

    final static int longestShip = 4;
    public static final int[][] DEFAULTCOORDINATES = {{0,0},{2,0},{4,0},{6,0},{0,2},{3,2},{6,2},{0,4},{4,4},{0,6}};
    public static final int[] DEFAULTSIZES = {1,1,1,1,2,2,2,3,3,4};
}
