import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import  java.util.Random;

/**
 * Created by Markus on 22/11/16.
 */
public class  GamePanel extends JPanel {
    JPanel view1 = new JPanel();
    JPanel view2 = new JPanel();
    JPanel views = new JPanel();
    JPanel switchboard = new JPanel();
    JButton fire = new JButton("Fire");
    JLabel target = new JLabel("Target");
    JLabel score = new JLabel("Score: 0 - 0");
    JLabel clicksLabel = new JLabel("0");
    JLabel[][] bfl1;
    JLabel[][] bfl2;
    int[] coordinates = {-1, -1};
    private int player1score = 0;
    private int player2score = 0;
    private int maxplayerscore = 0;
    Random rand = new Random();
    LinkedList<Integer[]> aiShotCoordinates = new LinkedList<>();
    int[] lastSuccessfulShot = {-1, -1};
    int[] lastSuccessfulDirection = {-2, -2};
    int[] lastDirections = {0, 0, 0, 0};
    LinkedList<Integer[]> successfulShots = new LinkedList<>();
    int clicks = 0;
    String clicksString = "";

    public void getMaxPlayerScore() {
        maxplayerscore = BattleWindow.currentPlayer.getPlayerFleet().totalPoints();
    }

    // parameeter player on enda lisatud, teise välja y koordinaadid algavad 10-st, mitte 0-st
    public JLabel[][] createBattleField(int player) {
        JLabel battleFieldLocations[][] = new JLabel[BattleWindow.battleFieldSize][BattleWindow.battleFieldSize];
        //init JLabels
        for (int x=0;x<BattleWindow.battleFieldSize;x++){
            for (int y=0;y<BattleWindow.battleFieldSize;y++){
                battleFieldLocations[x][y]= new JLabel("~");
                battleFieldLocations[x][y].setForeground(Color.cyan);
                battleFieldLocations[x][y].setBackground(Color.BLUE);
                battleFieldLocations[x][y].setOpaque(true);
                battleFieldLocations[x][y].setPreferredSize(new Dimension(20, 20));
                battleFieldLocations[x][y].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int locationx = -1;
                        int locationy = -1;
                        for (int x=0;x<BattleWindow.battleFieldSize;x++) {
                            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                                if (e.getSource() == battleFieldLocations[x][y]) {
                                    locationx = x;
                                    locationy = y + player;
                                }
                            }
                        }

                        coordinates[0] = locationx;
                        coordinates[1] = locationy;
                        if (locationy < 10) {
                            target.setText(BattleWindow.player1.name + " x: " + locationx + " y: " + locationy);
                        }
                        else {
                            target.setText(BattleWindow.player2.name + " x: " + locationx + " y: " + (locationy-10));
                        }
                        //Kui fire kutsuda siin välja, siis tuleb AI käivitumine teha teistmoodi
                        //fire();
                    }
                });
            }
        }
        return battleFieldLocations;

    }

    public void displayGamePanelContents() {
        views.setPreferredSize(new Dimension(600,200));
        views.setLayout(new GridLayout(1,3));
        view1.setPreferredSize(new Dimension(200, 200));
        view1.setOpaque(true);
        view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setPreferredSize(new Dimension(200, 200));
        view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setOpaque(true);
        view1.setLocation(0,0);
        view2.setLocation(200,0);
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                bfl1 = createBattleField(0);
                bfl2 = createBattleField(10);
            }
        }
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                view1.add(bfl1[x][y]);
                view2.add(bfl2[x][y]);
            }
        }

        fire.addActionListener(fireListener);
        switchboard.setLayout(new GridLayout(4,1));
        switchboard.add(clicksLabel);
        switchboard.add(fire);
        switchboard.add(target);
        switchboard.add(score);
        switchboard.setVisible(true);
        switchboard.repaint();
        views.add(view1);
        views.add(switchboard);
        views.add(view2);
        views.setVisible(true);
        views.repaint();
        add(views);
        //add(switchboard);
        revalidate();
        repaint();
    }

    public GamePanel() {
        setLayout(new FlowLayout());
        createBattleField(0);
        displayGamePanelContents();
        for (int x = 0; x < 10; x++) {
            aiShotCoordinates.add(new Integer[]{-1, x});
            aiShotCoordinates.add(new Integer[]{10, x});
            aiShotCoordinates.add(new Integer[]{x, 9});
            aiShotCoordinates.add(new Integer[]{x, 20});
        }
    }

    private ActionListener fireListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (BattleWindow.currentPlayer.name == BattleWindow.player1.name) {
                fire();
            }
            if (BattleWindow.currentPlayer.name == BattleWindow.player2.name) {
                if (lastSuccessfulShot[0] == -1) {
                    generateRandomCoordinates();
                }
                else {
                    predictShipLocation();
                }
                //coordinates[0] = (int) (Math.random()*BattleWindow.battleFieldSize);
                //coordinates[1] = (int) (Math.random()*BattleWindow.battleFieldSize + 10);
                fire();
            }
        }

    };
    public void fire() {
        getMaxPlayerScore();
        if (BattleWindow.currentPlayer.name == BattleWindow.player1.name) {
            clicks++;
            clicksString = clicks + "";
            clicksLabel.setText(clicksString);
            if (coordinates[1] > 9) {
                target.setText("Vale laud");
            }
            else if (coordinates[1] < 10){
                if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.SHIP) {
                    BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] = SeaConstants.HIT_SHIP;
                    bfl1[coordinates[0]][coordinates[1]].setBackground(Color.RED);
                    bfl1[coordinates[0]][coordinates[1]].repaint();
                    player1score++;
                    if (shipSunk(coordinates[0],coordinates[1])){
                        target.setText("Player 1 ship sunk");
                    }
                    else {
                        target.setText("Player 1 ship hit");
                    }
                    setScore();
                    if (player1score == maxplayerscore) {
                        target.setText("Player1 võitis");
                    }
                }
                else {
                    target.setText("Laeva ei olnud");
                    bfl1[coordinates[0]][coordinates[1]].setBackground(Color.GREEN);
                    bfl1[coordinates[0]][coordinates[1]].repaint();
                    BattleWindow.currentPlayer = BattleWindow.player2;
                }
            }
        }
        else if (BattleWindow.currentPlayer.name == BattleWindow.player2.name) {
            if (coordinates[1] > 9) {
                aiShotCoordinates.add(new Integer[]{coordinates[0], coordinates[1]});
                if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1] - 10] == SeaConstants.SHIP ) {
                    BattleWindow.player2.planningfield[coordinates[0]][coordinates[1] - 10] = SeaConstants.HIT_SHIP;
                    bfl2[coordinates[0]][coordinates[1] - 10].setBackground(Color.RED);
                    bfl2[coordinates[0]][coordinates[1] - 10].repaint();
                    successfulShots.add(new Integer[]{coordinates[0], coordinates[1]});
                    player2score++;
                    if (shipSunk(coordinates[0],coordinates[1]-10)){
                        target.setText("Player2 ship sunk");
                        lastSuccessfulShot = new int[]{-1, -1};
                        lastDirections = new int[]{0, 0, 0, 0};
                        lastSuccessfulDirection = new int[]{-2, -2};
                        for (int x = -1; x < 2 ; x++) {
                            for (int y = -1; y < 2; y++) {
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+x, coordinates[1]+y});
                            }
                        }
                    }
                    else {
                        target.setText("Player 2 ship hit");
                        if (lastSuccessfulShot[0] != -2 && lastSuccessfulShot[0] != -1) {
                            lastSuccessfulDirection[0] = lastSuccessfulShot[0] - coordinates[0];
                            lastSuccessfulDirection[1] = lastSuccessfulShot[1] - coordinates[1];
                            if (lastSuccessfulShot[0] - coordinates[0] == 0) {
                                aiShotCoordinates.add(new Integer[]{coordinates[0]-1, coordinates[1]-1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]-1, coordinates[1]});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]-1, coordinates[1]+1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+1, coordinates[1]-1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+1, coordinates[1]});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+1, coordinates[1]+1});
                            }
                            if (lastSuccessfulShot[1] - coordinates[1] == 0) {
                                aiShotCoordinates.add(new Integer[]{coordinates[0]-1, coordinates[1]-1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0], coordinates[1]-1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+1, coordinates[1]-1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]-1, coordinates[1]+1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0], coordinates[1]+1});
                                aiShotCoordinates.add(new Integer[]{coordinates[0]+1, coordinates[1]+1});
                            }
                        }
                        lastSuccessfulShot =  new int[]{coordinates[0], coordinates[1]};
                        lastDirections = new int[]{0, 0, 0, 0};
                        predictShipLocation();
                    }
                    setScore();
                    if (player2score == maxplayerscore) {
                        target.setText("Player2 võitis");
                    }
                    if (lastSuccessfulShot[0] == -1) {
                        generateRandomCoordinates();
                    }
                    fire();

                }
                else {
                    target.setText("Laeva ei olnud");
                    bfl2[coordinates[0]][coordinates[1] - 10].setBackground(Color.GREEN);
                    bfl2[coordinates[0]][coordinates[1] - 10].repaint();
                    if (lastSuccessfulDirection[0] != -2) {
                        for (int i = successfulShots.size()-1; i > -1; i--) {
                            if (successfulShots.get(i)[0]+lastSuccessfulDirection[0] == coordinates[0]) {
                                lastSuccessfulShot[1] = successfulShots.get(i)[1];
                            }
                            if (successfulShots.get(i)[1]+lastSuccessfulDirection[1] == 0) {
                                lastSuccessfulShot[0] = successfulShots.get(i)[0];
                            }
                        }
                        lastSuccessfulDirection[0] = -lastSuccessfulDirection[0];
                        lastSuccessfulDirection[1] = -lastSuccessfulDirection[1];
                    }
                    //lastSuccessfulDirection = new int[]{-2, -2};
                    BattleWindow.currentPlayer = BattleWindow.player1;

                }
            }
            else if (coordinates[1] < 10) {
                target.setText("Vale laud");
                if (lastSuccessfulDirection[0] != -2) {
                    for (int i = successfulShots.size()-1; i > -1; i--) {
                        if (successfulShots.get(i)[0]+lastSuccessfulDirection[0] == coordinates[0]) {
                            lastSuccessfulShot[1] = successfulShots.get(i)[1];
                        }
                        if (successfulShots.get(i)[1]+lastSuccessfulDirection[1] == 0) {
                            lastSuccessfulShot[0] = successfulShots.get(i)[0];
                        }
                    }
                    lastSuccessfulDirection[0] = -lastSuccessfulDirection[0];
                    lastSuccessfulDirection[1] = -lastSuccessfulDirection[1];
                }
                BattleWindow.currentPlayer = BattleWindow.player1;
            }
        }

    }

    private boolean aiHasShotThisCoordinate() {
        for (Integer[] m : aiShotCoordinates) {
            if (m[0] == coordinates[0] && m[1] == coordinates[1]) {
                return true;
            }
        }
        return false;
    }

    private void generateRandomCoordinates() {
        coordinates[0] = rand.nextInt((9 - 0) + 1);
        coordinates[1] = rand.nextInt((9 - 0) + 1) + 10;
        if (aiHasShotThisCoordinate()) {
            generateRandomCoordinates();
        }
        //coordinates[0] = 8;
        //coordinates[1] = 12;
    }
    private void predictShipLocation() {
        int randomDirection = rand.nextInt(4-0);
        int[] direction = {0, 0};
        boolean directionSet = false;
        boolean allDirectionsTried = true;
        //Kontrollib kas mõni suuna variantidest on läbi proovitud
        for (int i = 0; i < lastDirections.length; i++) {
            if (lastDirections[i] == 1) {
                directionSet = true;
                break;
            }
        }
        //Kontrollib, kas kõik võimalikud suunad selle koordinaadi puhul on läbi proovitud
        for (int i = 0; i < lastDirections.length; i++) {
            if (lastDirections[i] == 0) {
                allDirectionsTried = false;
                break;
            }
        }
        if (lastSuccessfulDirection[0] == -2) {
            for (int i = 0; i < lastDirections.length; i++) {
                if (directionSet && lastDirections[i] == 0) {
                    randomDirection = i;
                    lastDirections[i] = 1;
                    break;
                }
            }

            switch (randomDirection) {
                case 0:
                    direction = new int[]{-1, 0};
                    lastDirections[0] = 1;
                    break;
                case 1:
                    direction = new int[]{0, -1};
                    lastDirections[1] = 1;
                    break;
                case 2:
                    direction = new int[]{1, 0};
                    lastDirections[2] = 1;
                    break;
                case 3:
                    direction = new int[]{0, 1};
                    lastDirections[3] = 1;
                    break;
            }
        }
        else {
            direction[0] = lastSuccessfulDirection[0];
            direction[1] = lastSuccessfulDirection[1];
        }
        coordinates[0] = lastSuccessfulShot[0] - direction[0];
        coordinates[1] = lastSuccessfulShot[1] - direction[1];
        if (allDirectionsTried) {
            lastSuccessfulShot = new int[]{-1, -1};
            lastDirections = new int[]{0, 0, 0, 0};
            generateRandomCoordinates();

        }
        if (aiHasShotThisCoordinate() && lastSuccessfulDirection[0] != -2) {
            lastSuccessfulDirection[0] = -lastSuccessfulDirection[0];
            lastSuccessfulDirection[1] = -lastSuccessfulDirection[1];
        }
        if (aiHasShotThisCoordinate()) {
            predictShipLocation();
        }

        //direction[0] = 1;
        //direction[1] = 0;

    }

    private void setScore() {
        score.setText("Score: " + player1score + " - " + player2score);
    }
        private boolean shipSunk(int locationx, int locationy) {
            LinkedList<Integer[]> shipCoordinates = new LinkedList<>();
            boolean breakout = false;
            boolean breakout2 = false;
            int[] direction = {-1, -1};
            BattleWindow.currentPlayer.planningfield[locationx][locationy] = SeaConstants.SEA;
            for (int x = locationx - 1; x < locationx + 2; x++) {
                for (int y = locationy - 1; y < locationy + 2; y++) {
                    if (x >= 0 && x < BattleWindow.battleFieldSize && y >= 0 && y < BattleWindow.battleFieldSize ) {
                        if (BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.SHIP || BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.HIT_SHIP) {
                            direction = new int[]{x - locationx, y - locationy};
                            //
                        }

                    }
                }
            }
            BattleWindow.currentPlayer.planningfield[locationx][locationy] = SeaConstants.HIT_SHIP;
            for(int d = 0;d < 5;d++) {
                int mx = (d*direction[0])+ locationx;
                int my = (d*direction[1])+ locationy;
                for (int x = mx; x < mx + 1 ; x++) {
                    for (int y = my; y < my + 1; y++) {
                        if (x >= 0 && x < BattleWindow.battleFieldSize && y >= 0 && y < BattleWindow.battleFieldSize) {
                            if (BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.SHIP || BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.HIT_SHIP) {
                                shipCoordinates.add(new Integer[]{x, y});
                            }
                            else {
                                breakout = true;
                            }
                            if (breakout) break;
                        }
                        if (breakout) break;
                    }
                    if (breakout) break;
                }
                if (breakout) break;
            }
            for(int d = 0;d < 5;d++) {
                int mx = -(d*direction[0])+ locationx;
                int my = -(d*direction[1])+ locationy;
                for (int x = mx; x < mx + 1 ; x++) {
                    for (int y = my; y < my + 1; y++) {
                        if (x >= 0 && x < BattleWindow.battleFieldSize && y >= 0 && y < BattleWindow.battleFieldSize) {
                            if (BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.SHIP || BattleWindow.currentPlayer.planningfield[x][y] == SeaConstants.HIT_SHIP) {
                                shipCoordinates.add(new Integer[]{x, y});
                            }
                            else {
                                breakout2 = true;
                            }
                            if (breakout2) break;
                        }
                        if (breakout2) break;
                    }
                    if (breakout2) break;
                }
                if (breakout2) break;
            }
            for (Integer[] m : shipCoordinates) {
                if (BattleWindow.currentPlayer.planningfield[m[0]][m[1]] != SeaConstants.HIT_SHIP) {
                    return false;
                }
            }

            return true;
        }





}
