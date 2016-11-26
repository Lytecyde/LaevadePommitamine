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
    JPanel battleField = new JPanel();
    GamePanel gamePanel= new GamePanel();
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
    public static Player player1;
    public static Player player2;
    public static Player currentPlayer;
    int shipSize = 0;
    final private int xCoordinate =0;
    final private int yCoordinate =1;


    public JButton fill = new JButton("Fill");
    public JLabel ship4  = new JLabel("Battleship");
    public JButton ship4Button = new JButton("####");
    public JLabel ship3  = new JLabel("Destroyer");
    public JButton ship3Button = new JButton("###");
    public JLabel ship2  = new JLabel("Miner");
    public JButton ship2Button = new JButton("##");
    public JLabel ship1  = new JLabel("Patrol boat");
    public JButton ship1Button = new JButton("#");
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
                    currentPlayer = player1;
                    setFrameName("Laevadepommitamine: " + currentPlayer.name);
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

    public void setFrameName(String s){
        this.setTitle(s);
    }

    public JLabel[][] createBattleField() {

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
        planning.revalidate();
        this.add(planning);
        this.repaint();
        return battleFieldLocations;

    }

    public void createPlanningBoard(){

        JPanel switchboard = new JPanel();
        switchboard.setLayout(new GridLayout(8,2));

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
                //mõlemad mängijad on asetanud kõik laevad ja siis algab mäng;
                if(!(allShipsOnSea(currentPlayer))) {
                    //võtab info laeva suuruse, suuna ja algpunkti kohta

                    checkAndDisplayShipLocation();

                }else{
                    /////////////////////////////////////////////////////////////////////////
                    //changes players
                    ////////////////////////////////////////////////////////////////////////
                    if(currentPlayer == player1){
                        currentPlayer = player2;
                        setFrameName("Laevadepommitamine: " + currentPlayer.name);
                        //reset shipbuttons
                        resetShipButtons();
                        battleField.removeAll();
                        createBattleField();
                        planning.setVisible(true);
                    }else{
                        currentPlayer = player1;
                        System.out.println("Let the games begin!");
                        remove(planning);
                        remove(switchboard);
                        add(gamePanel);
                        pack();
                        revalidate();
                        repaint();
                    }

                }

            }

            private void checkAndDisplayShipLocation() {
                if (shipSize != 0 &&
                        direction != -1 &&
                        coordinatesAreSet(coordinates)) {

                    System.out.println("All data received for sailing a ship:size"
                            + shipSize + "d:" + direction + "x:" + coordinates[0] + "y" + coordinates[1]);


                    int[] direct = directionsForAdjency(direction);
                    if (coordinatesInBounds(coordinates)) {
                        System.out.println("Algkoordinaadid on sobilikud merele");
                        if (allShipCoordinatesInBounds(coordinates, direct)) {
                            System.out.println("Terve laev mahub väljakule");
                            if (allShipCoordinatesLegal(coordinates, direct)) {
                                System.out.println("Kõik laeva koordinaadid on reeglitepärased");
                                drawShipMap(direct);
                                drawAdjacentToShipMap(direct);
                                displayShips();
                                boolean isSailing = true;
                                int[] currentShipsCoordinates = {coordinates[0] , coordinates[1]};
                                //hits = 0
                                Ship currentShip = new Ship(shipSize, direction, currentShipsCoordinates, 0, isSailing);
                                resetShipData();
                                currentPlayer.getPlayerFleet().set(currentShip);
                                showAvailableShipButtons();
                                currentPlayer.getPlayerFleet().printFleet(); //ERROR: prints all coord values as same!!!
                                currentPlayer.printPlanningField();
                            }
                        }
                    }
                }
            }
            private boolean allShipCoordinatesLegal(int[] coordinates, int[] direct) {
                boolean areLegal = true;
                for(int d = 0;d < shipSize;d++) {

                    int mx = (d*direct[1])+coordinates[0] - 1;
                    int my = (d*direct[0])+coordinates[1] - 1;
                    boolean breakout = false;
                    for (int x = mx; x < mx + 3 ; x++) {
                        for (int y = my; y < my + 3; y++) {
                            if (x >= 0 && x < battleFieldSize && y >= 0 && y < battleFieldSize ) {
                                if (!coordinatesAreShip(new int[]{x, y})) {
                                    areLegal = true;
                                } else {
                                    System.out.println("ERIOLUKORD: laev ei sobi merele!");
                                    areLegal = false;
                                    breakout = true;
                                    break;
                                }
                            }
                            if(breakout) break;
                        }
                        if(breakout) break;
                    }
                }
                for(int d = 0;d < shipSize;d++) {

                    int mx = (d*direct[1])+coordinates[0];
                    int my = (d*direct[0])+coordinates[1];
                    boolean breakout = false;
                    for (int x = mx; x < mx + 1 ; x++) {
                        for (int y = my; y < my + 1; y++) {
                            if (x >= 0 && x < battleFieldSize && y >= 0 && y < battleFieldSize ) {
                                if (!coordinatesAreAdjacentToShip(new int[]{x, y})) {
                                    areLegal = true;
                                } else {
                                    System.out.println("ERIOLUKORD: laev ei sobi merele!");
                                    areLegal = false;
                                    breakout = true;
                                    return areLegal;
                                }
                            }
                        }
                    }
                }
                return areLegal;
            }
            private boolean coordinatesAreShip(int[] coordinates){
                boolean legal = false;

                if(currentPlayer.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.SHIP){
                    legal = true;
                }
                else{
                    //sea or adjacent to ship
                    legal = false;
                }

                return legal;
            }
            private boolean coordinatesAreAdjacentToShip(int[] coordinates){
                boolean legal = false;

                if(currentPlayer.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.ADJACENT_TO_SHIP){
                    legal = true;
                }
                else{
                    //sea or adjacent to ship
                    legal = false;
                }

                return legal;
            }


            private boolean allShipCoordinatesInBounds(int[] coordinates, int[] direct) {
                boolean inBounds = true;
                for(int d = 0;d < shipSize;d++) {

                    int mx = (d*direct[1])+coordinates[0];
                    int my = (d*direct[0])+coordinates[1];
                    boolean breakout = false;
                    for (int x = mx; x < mx + 1 ; x++) {
                        for (int y = my; y < my + 1; y++) {
                            if (coordinatesInBounds(new int[]{x, y})) {
                                inBounds = true;

                            } else {
                                System.out.println("Laev ei mahu mänguväljakule.");
                                inBounds = false;
                                breakout = true;
                                break;
                            }
                            if(breakout) break;
                        }
                    }
                }
                return inBounds;
            }

            private void drawShipMap(int[] direct) {
                for (int d = 0; d < shipSize; d++) {
                    int mx = (d * direct[1]) + coordinates[0];
                    int my = (d * direct[0]) + coordinates[1];
                    for (int x = mx; x < mx + 1; x++) {
                        for (int y = my; y < my + 1; y++) {
                            if (coordinatesInBounds(new int[]{x, y}) &&
                                    coordinatesAreLegal(new int[]{x, y})) {
                                currentPlayer.planningfield[x][y] = SeaConstants.SHIP;
                            }
                        }
                    }
                }
            }

            private void drawAdjacentToShipMap(int[] direct) {
                for (int d = 0; d < shipSize; d++) {
                    int mx = (d * direct[1]) + coordinates[0] - 1;
                    int my = (d * direct[0]) + coordinates[1] - 1;
                    for (int x = mx; x < mx + 3; x++) {
                        for (int y = my; y < my + 3; y++) {
                            if (x >= 0 && x < battleFieldSize && y >= 0 && y < battleFieldSize ) {
                                if (currentPlayer.planningfield[x][y] != SeaConstants.SHIP) {
                                    currentPlayer.planningfield[x][y] = SeaConstants.ADJACENT_TO_SHIP;
                                }
                            }
                        }
                    }
                }
            }

            public void resetShipData(){
                shipSize = 0;
                direction = -1;
                coordinates[0] = -1;
                coordinates[1] = -1;
            }
            public boolean coordinatesAreSet(int[] coordinates){

                return coordinates[0]!=-1 && coordinates[1] !=-1;
            }
            public boolean coordinatesAreLegal(int[] coordinates){
                boolean legal = false;

                if(currentPlayer.planningfield[coordinates[0]][coordinates[1]] == SeaConstants.SEA){
                    legal = true;
                }
                else{
                    //sea or adjacent to ship
                    legal = false;
                }

                return legal;
            }
            public boolean coordinatesInBounds(int[] coordinates){
                boolean inBounds = false;
                if((coordinates[0] >=0 )&& (coordinates[0] < battleFieldSize)){
                    if((coordinates[1] >= 0 )&& (coordinates[1] < battleFieldSize) )inBounds= true;

                }
                else {
                    inBounds = false;
                }
                return inBounds;
            }
        };
        ok.addActionListener(okActionListener);
        //switchboard.setVisible(true);
        ActionListener fillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.createPlanningField();
                displayShips();
                //set all ships to sail automatically
                for (int i = 0; i < currentPlayer.getPlayerFleet().ships.size(); i++) {
                    currentPlayer.getPlayerFleet().ships.get(i).sailing = true;
                }

            }
        };
        fill.addActionListener(fillListener);
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
        switchboard.add(fill);
        planning.add(switchboard);
        planning.setVisible(true);
        this.repaint();
    }
    public void displayShips(){

        for(int x = 0;x<battleFieldSize;x++){
            for (int y = 0; y < battleFieldSize; y++) {
                if(currentPlayer.planningfield[x][y] == SeaConstants.SHIP)
                    battleFieldLocations[x][y].setBackground(Color.black);
            }
        }
        planning.repaint();
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

    public int countShips(Player currentPlayer){

        return 0;
    }
    public boolean allShipsOnSea(Player p){
        for(Ship s: p.getPlayerFleet().ships){
            if(s.sailing ==  false){
                return false;
            }
        }
        return true;
    }
    public boolean finishedFleetSetup(){

        return false;
    }
    public int[] directionsForAdjency(int direction){
        int[] direct = new int[2];
        if (direction == 0){// north
            direct[0] =0; //x
            direct[1] =-1; //y
        }
        else if (direction == 1){ //east
            direct[0] =1;
            direct[1] =0;
        }
        else if (direction == 2){ //south
            direct[0] =0;
            direct[1] =+1;
        }
        else if (direction == 3){//west
            direct[0] =-1;
            direct[1] =0;
        }
        if(direction ==-1){
            System.out.println("KARJUN sest suund direction pole määratud!");
        }
        return direct;
    }

    public void showAvailableShipButtons(){
        ship1Button.setEnabled(!Fleet.noShipsLeft[0]);
        ship2Button.setEnabled(!Fleet.noShipsLeft[1]);
        ship3Button.setEnabled(!Fleet.noShipsLeft[2]);
        ship4Button.setEnabled(!Fleet.noShipsLeft[3]);
    }

    public void resetShipButtons(){
        ship1Button.setEnabled(true);
        ship2Button.setEnabled(true);
        ship3Button.setEnabled(true);
        ship4Button.setEnabled(true);

    }

}