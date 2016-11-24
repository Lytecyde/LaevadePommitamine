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
    JButton fire = new JButton("Fire");
    JLabel target = new JLabel("Target");
    JLabel score = new JLabel("Score");
    JLabel[][] battleFieldLabels1;
    JLabel[][] battleFieldLabels2;
    JLabel[][] currentLabels;
    int[] coordinates = {-1, -1};
    JLabel burningShip = new JLabel();
    final boolean FIRST = false;
    final boolean SECOND = true;
    boolean turnOfPlayer = FIRST;
    int gameTurn =0;

    public GamePanel() {
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
        views.setLayout(new GridLayout(1,2));
        view1.setPreferredSize(new Dimension(200, 200));
        view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setPreferredSize(new Dimension(200, 200));
        view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));

        //create all the labels for battlefield view1 and view2

        //add mouse listener to each label
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
        views.add(view1);
        views.add(view2);
        views.setVisible(true);
        views.repaint();
        add(views);
        add(switchboard);
        revalidate();
        repaint();
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


            if(BattleWindow.currentPlayer.name.equals(BattleWindow.player1.name) && turnOfPlayer ==SECOND) {
                burningShip.setOpaque(true);
                if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 1) {
                    target.setText("HIT!");
                    BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] = 3;
                    //laevastikust saab laev viga
                    //battlefield märgitakse ära
                    burningShip.setOpaque(true);
                    burningShip.setBackground(Color.RED);
                    burningShip.setPreferredSize(new Dimension(20,20));
                    battleFieldLabels2[coordinates[0]][coordinates[1]] = burningShip;
                }
                else if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 0) {
                    target.setText("MISS!");

                }
                else if (BattleWindow.player1.planningfield[coordinates[0]][coordinates[1]] == 2) {
                    target.setText("MISS!");

                }
            }

            else if(BattleWindow.currentPlayer.name.equals(BattleWindow.player2.name) && turnOfPlayer ==FIRST){
                if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == 1) {
                    target.setText("HIT!");
                    BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] = 3;
                    //laevastikust saab laev viga
                    //battlefield märgitakse ära
//                    burningShip.setOpaque(true);
//                    burningShip.setBackground(Color.RED);
//                    burningShip.setPreferredSize(new Dimension(20,20));
//                    battleFieldLabels1[coordinates[0]][coordinates[1]] = burningShip;
                }
                else if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == 0) {
                    target.setText("MISS!");

                }
                else if (BattleWindow.player2.planningfield[coordinates[0]][coordinates[1]] == 2) {
                    target.setText("MISS!");

                }
            }//
            else {
                System.out.println("Wrong Table!");
            }
            view1.removeAll();
            view2.removeAll();
            //displayGamePanelContents();
            view1.setPreferredSize(new Dimension(200, 200));
            view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
            view2.setPreferredSize(new Dimension(200, 200));
            view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
            views.setLayout(new GridLayout(1,2));
            battleFieldLabels1 = redisplayBattleView(BattleWindow.player1.getPlanningfield());
            showView(battleFieldLabels1);
            battleFieldLabels2 = redisplayBattleView(BattleWindow.player2.getPlanningfield());
            showView(battleFieldLabels2);
            views.add(view1);
            views.add(view2);
            views.revalidate();
            views.repaint();

            BattleWindow.gameTurn++;
            System.out.println(BattleWindow.gameTurn);
            turnOfPlayer = !turnOfPlayer;
        }
    };

    public void showView(JLabel[][] currentLabels){
        if(BattleWindow.currentPlayer.name.equals(BattleWindow.player1.name)) {
            for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
                for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                    view2.add(currentLabels[x][y]);
                }
            }
        }else if (BattleWindow.currentPlayer.name.equals(BattleWindow.player2.name)){
            for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
                for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                    view1.add(currentLabels[x][y]);
                }
            }
        }else{
            System.out.println("ERI showview");
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

}
