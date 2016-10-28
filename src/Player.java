/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Player {
    public String name = "";
    private Fleet playerFleet;
    private int battleFieldSize = BattleWindow.getFieldSize();
    public int[][] planningfield = new int[battleFieldSize][battleFieldSize];

    public void setupPlanningField(){
        for (int x=0;x<battleFieldSize;x++) {
            for (int y = 0; y < battleFieldSize; y++) {
                planningfield[x][y] = SeaConstants.SEA;
            }
        }
    }

    public void createPlanningField(){
        for (int x=0;x<battleFieldSize;x++) {
            for (int y = 0; y < battleFieldSize; y++) {
                planningfield[x][y] = SeaConstants.SEA;

            }
        }
        //laev 1
        planningfield[0][0] = SeaConstants.SHIP;
        planningfield[0][2] = SeaConstants.SHIP;
        planningfield[0][4] = SeaConstants.SHIP;
        planningfield[0][6] = SeaConstants.SHIP;
        //laevad 2
        planningfield[2][0] = SeaConstants.SHIP;
        planningfield[2][1] = SeaConstants.SHIP;

        planningfield[2][3] = SeaConstants.SHIP;
        planningfield[2][4] = SeaConstants.SHIP;

        planningfield[2][6] = SeaConstants.SHIP;
        planningfield[2][7] = SeaConstants.SHIP;

        //laevad 3
        planningfield[4][0] = SeaConstants.SHIP;
        planningfield[4][1] = SeaConstants.SHIP;
        planningfield[4][2] = SeaConstants.SHIP;

        planningfield[6][0] = SeaConstants.SHIP;
        planningfield[6][1] = SeaConstants.SHIP;
        planningfield[6][2] = SeaConstants.SHIP;

        planningfield[8][0] = SeaConstants.SHIP;
        planningfield[8][1] = SeaConstants.SHIP;
        planningfield[8][2] = SeaConstants.SHIP;
        planningfield[8][3] = SeaConstants.SHIP;


    }


    public Player(String name) {
        this.name = name;
        setupPlanningField();
        playerFleet = new Fleet();
    }

    public Fleet getPlayerFleet() {
        return playerFleet;
    }

    public void printPlanningField(){
        for (int x=0;x<battleFieldSize;x++) {
            for (int y = 0; y < battleFieldSize; y++) {
                System.out.print(planningfield[x][y]);
            }
            System.out.println();
        }
    }
}
