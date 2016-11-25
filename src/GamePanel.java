import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Markus on 22/11/16.
 */
public class  GamePanel extends JPanel {
    JPanel view1 = new JPanel();
    JPanel view2 = new JPanel();
    JPanel views = new JPanel();
    JPanel switchboard = new JPanel();
    JButton fire = new JButton("Fire!");
    JLabel target = new JLabel("Target");
    JLabel score = new JLabel("Score");
    JLabel[][] battleFieldLabels1;
    JLabel[][] battleFieldLabels2;

    int[] coordinates = {-1, -1};

    final boolean FIRST = false;
    final boolean SECOND = true;
    boolean turnOfPlayer = FIRST;
    int gameTurn =0;

    public GamePanel() {
        BattleWindow.currentPlayer = BattleWindow.player1;
        System.out.println("player1 name:" + BattleWindow.player1.name);
        setLayout(new FlowLayout());
        createBattleField();
        createBattleFieldSea();
        displayGamePanelContents();
    }

    public JLabel[][] createBattleField() {
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
                                    locationy = y;
                                }
                            }
                        }
                        coordinates[0] = locationx;
                        coordinates[1] = locationy;


                    }
                });

            }
        }
        return battleFieldLocations;

    }

    public void displayGamePanelContents() {

        views.setPreferredSize(new Dimension(400,200));
        views.setLayout(new GridLayout(1,2,10,10));
        view1.setPreferredSize(new Dimension(200, 200));
        view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setPreferredSize(new Dimension(200, 200));
        view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));

        //create all the labels for battlefield view1 and view2

        //add mouse listener to each label
        addCoordinateShowToAllLabels();


        //add all battlefield labels to view1 and view2
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                view1.add(battleFieldLabels1[x][y]);
                view2.add(battleFieldLabels2[x][y]);
            }
        }
        //fire
        fire.addActionListener(fireListener);

        switchboard.add(fire);
        switchboard.add(target);
        switchboard.add(score);
        switchboard.setVisible(true);
        switchboard.repaint();
        view2.setBorder(BorderFactory.createLineBorder(Color.gray));
        view1.setBorder(BorderFactory.createLineBorder(Color.red));
        views.add(view1);
        views.add(view2);
        views.setVisible(true);
        views.repaint();
        add(views);
        add(switchboard);
        revalidate();
        repaint();
    }

    private void addCoordinateShowToAllLabels() {
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                battleFieldLabels1[x][y].addMouseListener(
                        new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                target.setText(BattleWindow.player1.name+":x:" +coordinates[0]+"y:"+coordinates[1] );}});
                battleFieldLabels2[x][y].addMouseListener(
                        new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                target.setText(BattleWindow.player2.name+"x:" +coordinates[0]+"y:"+coordinates[1] );}});
            }

        }
    }

    private void createBattleFieldSea() {
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                battleFieldLabels1 = createBattleField();
                battleFieldLabels2 = createBattleField();
            }
        }
    }


    ActionListener fireListener = new ActionListener(){
        public void actionPerformed(ActionEvent e) {

            if(coordinates[0] >= 0) {
                System.out.println(BattleWindow.currentPlayer.name+"p"+BattleWindow.player1.name + " first:"+turnOfPlayer);
                if (BattleWindow.currentPlayer.name.equals(BattleWindow.player1.name) && turnOfPlayer == FIRST) {

                    if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 1) {
                        target.setText("HIT!");
                        BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] = 3;
                        if(isSunk(coordinates[0], coordinates[1]))target.setText("SUNK!");
                        //laevastikust saab laev viga
                        //battlefield märgitakse ära

                    } else if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 0) {
                        target.setText("MISS!");

                    } else if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 2) {
                        target.setText("MISS!");

                    }else{

                    }
                    view1.setBorder(BorderFactory.createLineBorder(Color.gray));
                    view2.setBorder(BorderFactory.createLineBorder(Color.red));
                } else if (BattleWindow.currentPlayer.name.equals(BattleWindow.player2.name) && turnOfPlayer == SECOND) {

                    if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.SHIP) {
                        target.setText("HIT!");
                        //battlefield märgitakse ära
                        BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] = SeaConstants.SHIP_AFLAME;
                        if(isSunk(coordinates[0], coordinates[1]))target.setText("SUNK!");
                        //laevastikust läheb laev ehk põhja


                    } else if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.SEA) {
                        target.setText("MISS!");

                    } else if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.ADJACENT_TO_SHIP) {
                        target.setText("MISS!");

                    }
                    else{

                    }
                    view2.setBorder(BorderFactory.createLineBorder(Color.gray));
                    view1.setBorder(BorderFactory.createLineBorder(Color.red));
                }//
                else {
                    System.out.println("Wrong Table!");
                }
            }else{
                System.out.println("Missed correct target!");
            }
            view1.removeAll();
            view2.removeAll();

            //displayGamePanelContents();
            view1.setPreferredSize(new Dimension(200, 200));
            view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
            view2.setPreferredSize(new Dimension(200, 200));
            view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
            views.setLayout(new GridLayout(1,2));
            //battleFieldLabels1 = redisplayBattleView(BattleWindow.player1.getPlanningfield());
            //showView(battleFieldLabels1);
            //battleFieldLabels2 = redisplayBattleView(BattleWindow.player2.getPlanningfield());
            //showView(battleFieldLabels2);


            battleFieldLabels1 = new JLabel[BattleWindow.battleFieldSize][BattleWindow.battleFieldSize];
            battleFieldLabels2 = new JLabel[BattleWindow.battleFieldSize][BattleWindow.battleFieldSize];

            printPlanningField(BattleWindow.player1.getPlanningfield());
            battleFieldLabels1 = redisplayBattleView(BattleWindow.player1.getPlanningfield());
            showView(battleFieldLabels1, view1);
            battleFieldLabels2 = redisplayBattleView(BattleWindow.player2.getPlanningfield());
            showView(battleFieldLabels2, view2);
            addCoordinateShowToAllLabels();
            views.setLayout(new GridLayout(1,2,10,10));

            views.add(view1);
            views.add(view2);
            views.revalidate();
            views.repaint();
            //BattleWindow data changes
            BattleWindow.gameTurn++;
            if(turnOfPlayer){
                BattleWindow.currentPlayer = BattleWindow.player1;
            }else{
                BattleWindow.currentPlayer = BattleWindow.player2;
            }
            System.out.println(BattleWindow.gameTurn);
            turnOfPlayer = !turnOfPlayer;
            ((JButton)e.getSource()).setText(BattleWindow.currentPlayer.name+": Fire!");
        }
    };

    public void showView(JLabel[][] currentLabels, JPanel view){

        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                view.add(currentLabels[x][y]);
            }
        }
    }

    public void printPlanningField(int[][] planningfield){
        for (int x=0;x<BattleWindow.battleFieldSize;x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                System.out.print(planningfield[x][y]);
            }
            System.out.println();
        }
    }


    public JLabel[][] redisplayBattleView(int[][] planningField){
        JLabel battleFieldLocations[][] = new JLabel[BattleWindow.battleFieldSize][BattleWindow.battleFieldSize];
        for (int x=0;x<BattleWindow.battleFieldSize;x++){
            for (int y=0;y<BattleWindow.battleFieldSize;y++){
                battleFieldLocations[x][y] = new JLabel();
                if(planningField[x][y] == 0 || planningField[x][y] == 2) {
                    battleFieldLocations[x][y] = new JLabel("~");
                    battleFieldLocations[x][y].setForeground(Color.cyan);
                    battleFieldLocations[x][y].setBackground(Color.BLUE);
                    battleFieldLocations[x][y].setOpaque(true);
                    battleFieldLocations[x][y].setPreferredSize(new Dimension(20, 20));
                    battleFieldLocations[x][y].addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            int locationx = -1;
                            int locationy = -1;
                            for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
                                for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                                    if (e.getSource() == battleFieldLocations[x][y]) {
                                        locationx = x;
                                        locationy = y;
                                    }
                                }
                            }
                            coordinates[0] = locationx;
                            coordinates[1] = locationy;


                        }
                    });
                }
                else if(planningField[x][y] == 1){
                    //for cheating purposes
                    battleFieldLocations[x][y] = new JLabel("~");
                    battleFieldLocations[x][y].setForeground(Color.cyan);
                    //comment this out to cheat
                    battleFieldLocations[x][y].setBackground(Color.BLUE);
                    battleFieldLocations[x][y].setOpaque(true);
                    battleFieldLocations[x][y].setPreferredSize(new Dimension(20, 20));
                    battleFieldLocations[x][y].addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            int locationx = -1;
                            int locationy = -1;
                            for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
                                for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                                    if (e.getSource() == battleFieldLocations[x][y]) {
                                        locationx = x;
                                        locationy = y;
                                    }
                                }
                            }
                            coordinates[0] = locationx;
                            coordinates[1] = locationy;


                        }
                    });
                }
                else if(planningField[x][y]==3){
                    battleFieldLocations[x][y].setBackground(Color.RED);
                    battleFieldLocations[x][y].setOpaque(true);
                    battleFieldLocations[x][y].setPreferredSize(new Dimension(20, 20));
                    battleFieldLocations[x][y].addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            int locationx = -1;
                            int locationy = -1;
                            for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
                                for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                                    if (e.getSource() == battleFieldLocations[x][y]) {
                                        locationx = x;
                                        locationy = y;
                                    }
                                }
                            }
                            coordinates[0] = locationx;
                            coordinates[1] = locationy;


                        }
                    });
                }
                else{
                    System.out.println("ERI! : mingi muu number");
                }
            }
        }
        return battleFieldLocations;
    }
    public void scoring(String s){

        score.setText(s);
    }

    public boolean isSunk(int x, int y){
        //find ship with coordinates x,y
        boolean sunk = false;
        boolean breakout = false;
        int indexOfShip = 0;
        for(Ship s: BattleWindow.currentPlayer.getPlayerFleet().ships){
            System.out.println("NOF ships"+BattleWindow.currentPlayer.getPlayerFleet().ships.size());
            for (int n = 0; n < s.size; n++) {
                if(s.allCoordinates != null) {
                    //System.out.println("size" + s.size + "allCoordinates of this ship x" + s.allCoordinates[0][n] + " " + s.allCoordinates[1][n]);// +" y" +s.allCoordinates[1][n]
                    if (s.allCoordinates[0][n] == x && s.allCoordinates[1][n] == y) {
                        s.incrementHits();
                        System.out.println("hits" + s.hits);
                        sunk = true;
                        scoring("P1:"+BattleWindow.player1.getPlayerFleet().fleetHitPoints+"P2:"
                                +BattleWindow.player2.getPlayerFleet().fleetHitPoints);
                        breakout = true;
                    }
                    if (breakout) break;
                }    else{
                    System.out.println(indexOfShip+"KARJUB NPE");//TODO: lahendus???
                }
            }
            if(breakout)break;
            indexOfShip++;
        }

        return sunk;
    }
}
