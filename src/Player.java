/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Player {
    public String name = "";
    private Fleet playerFleet = new Fleet();
    private int battleFieldSize = BattleWindow.getFieldSize();
    public int[][] planningfield = new int[battleFieldSize][battleFieldSize];

    public void setupPlanningField(){
        for (int x=0;x<battleFieldSize;x++) {
            for (int y = 0; y < battleFieldSize; y++) {
                planningfield[x][y] = SeaConstants.SEA;
            }
        }
    }



    public Player(String name) {
        this.name = name;
        setupPlanningField();
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
