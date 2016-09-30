/**
 * Created by mik.seljamaa on 29.09.2016.
 */
public class Player {
    public String name = "";
    private Fleet playerFleet = new Fleet();

    public Player(String name) {
        this.name = name;
    }

    public Fleet getPlayerFleet() {
        return playerFleet;
    }
}
