import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleWindow extends JFrame {

    String[] fleetSizes = {"20"};
    String[] fieldSizes = {"10x10"};
    static int battleFieldSize = 10;
    JPanel setup = new JPanel();
    JPanel planning = new JPanel();

    JLabel name = new JLabel("Name");
    JTextField nameTextField = new JTextField();
    JLabel fleetSize = new JLabel("Fleet Size");
    JComboBox fleetSizeComboBox = new JComboBox(fleetSizes);
    JLabel fieldSize = new JLabel ("Field Size");
    JComboBox fieldSizeComboBox = new JComboBox(fieldSizes);
    JLabel gameType = new JLabel("Game Type");
    JRadioButton hotseat = new JRadioButton("hotseat");
    JRadioButton ai = new JRadioButton("ai");
    JRadioButton network = new JRadioButton("network");
    ButtonGroup gameTypeGroup = new ButtonGroup();
    JButton ok =new JButton("OK");
    JButton startGame =new JButton("Start game!");
    int counterOk = 0;
    private int direction = -1;
    private int[] coordinates = {-1,-1};
    private Player player1;
    private Player player2;
    private Player currentPlayer = player1;
    int shipSize = 0;
    final private int xCoordinate =0;
    final private int yCoordinate =1;
    /**********************************
     * Battlefield variables
     */
    static JLabel[][] battleFieldLocations;

    public BattleWindow (){
        this.setSize(640,480);
        this.setLayout(new FlowLayout());
        createSetup(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Battleships");
        this.setVisible(true);
    }
    public void createSetup(BattleWindow bw){
        setup.setLayout(new GridLayout(5,2));
        setup.add(name);
        name.setVisible(false);
        setup.add(nameTextField);
        nameTextField.setVisible(false);
        setup.add(fieldSize);
        setup.add(fieldSizeComboBox);
        setup.add(fleetSize);
        setup.add(fleetSizeComboBox);
        setup.add(gameType);
        JPanel gameTypePanel = new JPanel();
        gameTypeGroup.add(hotseat);
        gameTypeGroup.add(ai);
        gameTypeGroup.add(network);
        gameTypePanel.add(hotseat);
        gameTypePanel.add(ai);
        gameTypePanel.add(network);
        setup.add(gameTypePanel);
        ActionListener startActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setup.removeAll();
                setup.repaint();

                createBattleField();
                createPlanningBoard();

                bw.add(planning);
                bw.pack();
                bw.setVisible(true);
            }
        };
        startGame.addActionListener(startActionListener);
        startGame.setEnabled(false);

        ActionListener okActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counterOk == 0) {
                    name.setVisible(true);
                    nameTextField.setVisible(true);
                    setup.repaint();
                    fieldSizeComboBox.setEnabled(false);
                    fleetSizeComboBox.setEnabled(false);
                    hotseat.setEnabled(false);
                    ai.setEnabled(false);
                    network.setEnabled(false);
                    setFieldSize(fieldSizeComboBox.getSelectedIndex());
                    counterOk++;
                }
                else if(counterOk == 1 && !nameTextField.getText().equals("")) {
                    player1 = new Player(nameTextField.getText());
                    nameTextField.setText("");

                    counterOk++;
                }
                else if(counterOk == 2 && !nameTextField.getText().equals("")) {
                    player2 = new Player(nameTextField.getText());
                    nameTextField.setText("");
                    ok.setEnabled(false);
                    startGame.setEnabled(true);
                    counterOk++;
                }

            }
        };
        ok.addActionListener(okActionListener);
        setup.add(ok);
        setup.add(startGame);



        this.add(setup);
    }

    public void createBattleField() {
        JPanel battleField = new JPanel();
        battleField.setPreferredSize(new Dimension(200, 200));
        battleField.setLayout(new GridLayout(battleFieldSize,battleFieldSize));
        battleFieldLocations = new JLabel[battleFieldSize][battleFieldSize];

        //init JLabels
        for (int x=0;x<battleFieldSize;x++){
            for (int y=0;y<battleFieldSize;y++){
                battleFieldLocations[x][y]= new JLabel("~");
                battleFieldLocations[x][y].setForeground(Color.cyan);
                battleFieldLocations[x][y].setBackground(Color.BLUE);
                battleFieldLocations[x][y].setOpaque(true);
                battleFieldLocations[x][y].setPreferredSize(new Dimension(20, 20));
                battleFieldLocations[x][y].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int locationx = -1;
                        int locationy = -1;
                        for (int x=0;x<battleFieldSize;x++) {
                            for (int y = 0; y < battleFieldSize; y++) {
                                if (e.getSource() == battleFieldLocations[x][y]) {
                                    locationx = x;
                                    locationy = y;
                                }
                            }
                        }
                        coordinates[xCoordinate] = locationx;
                        coordinates[yCoordinate] = locationy;
                    }
                });
                battleField.add(battleFieldLocations[x][y]);
            }
        }



        planning.add(battleField);
        planning.setVisible(true);
        this.repaint();
    }

    public void createPlanningBoard(){

        JPanel switchboard = new JPanel();
        switchboard.setLayout(new GridLayout(7,2));
        JLabel ship4  = new JLabel("Battleship");
        JButton ship4Button = new JButton("####");
        JLabel ship3  = new JLabel("Destroyer");
        JButton ship3Button = new JButton("###");
        JLabel ship2  = new JLabel("Miner");
        JButton ship2Button = new JButton("##");
        JLabel ship1  = new JLabel("Patrol boat");
        JButton ship1Button = new JButton("#");
        ActionListener sizeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()== ship4Button){
                    shipSize = 4;
                }
                else if (e.getSource() == ship3Button) {
                    shipSize = 3;
                }
                else if (e.getSource()== ship2Button){
                    shipSize=2;
                }
                else if (e.getSource()==ship1Button){
                    shipSize=1;
                }
            }
        };
        ship1Button.addActionListener(sizeListener);
        ship2Button.addActionListener(sizeListener);
        ship3Button.addActionListener(sizeListener);
        ship4Button.addActionListener(sizeListener);

        JButton northButton = new JButton("North");
        JButton eastButton = new JButton("East");
        JButton southButton = new JButton("South");
        JButton westButton = new JButton("West");
        ActionListener directionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == northButton){
                    direction = 0;
                }
                else if(e.getSource() == eastButton){
                    direction = 1;
                }
                else if(e.getSource() == southButton){
                    direction = 2;
                }
                else if(e.getSource() == westButton){
                    direction = 3;
                }
            }
        };
        northButton.addActionListener(directionListener);
        eastButton.addActionListener(directionListener);
        southButton.addActionListener(directionListener);
        westButton.addActionListener(directionListener);

        JLabel feedback = new JLabel();
        JButton ok = new JButton("OK!");
        JButton reset = new JButton("Reset");

        ActionListener okActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //peab võtma info laeva suuruse, suuna ja algpunkti kohta
                if(shipSize !=0 &&
                        direction != -1 &&
                        coordinatesAreSet(coordinates) ){
                    Ship currentShip = new Ship(shipSize);
                    System.out.println("All data received for sailing a ship:size"
                            + shipSize + "d:"+direction + "x:"+coordinates[0]+"y"+coordinates[1]);
                }

            }
            public boolean coordinatesAreSet(int[] coordinates){
                return coordinates[0]!=-1 && coordinates[1] !=-1;
            }
            public boolean coordinatesAreLegal(int[] coordinates){
                //peab kontrollima et eelmised laevad ei kattu ja ei tohi ka puutuda

                //laevad ei kattu
                boolean legal = true;

                if(currentPlayer.planningfield[coordinates[xCoordinate]][coordinates[yCoordinate]] ==1)
                legal = false;

                return legal;
            }
        };
        ok.addActionListener(okActionListener);
        //switchboard.setVisible(true);
        switchboard.add(ship1);
        switchboard.add(ship1Button);
        switchboard.add(ship2);
        switchboard.add(ship2Button);
        switchboard.add(ship3);
        switchboard.add(ship3Button);
        switchboard.add(ship4);
        switchboard.add(ship4Button);
        switchboard.add(northButton);
        switchboard.add(eastButton);
        switchboard.add(southButton);
        switchboard.add(westButton);
        switchboard.add(ok);
        switchboard.add(feedback);
        switchboard.add(reset);

        planning.add(switchboard);
        planning.setVisible(true);
        this.repaint();
    }


    public static void setFieldSize(int index){
        switch (index){
            case 0:
                battleFieldSize = 10;
                break;
            default : battleFieldSize = 10;
        }

    }



    public static int getFieldSize(){
        return battleFieldSize;
    }
}