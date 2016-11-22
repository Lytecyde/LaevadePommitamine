import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Markus on 22/11/16.
 */
public class  GamePanel extends JPanel {
    JPanel view1 = new JPanel();
    JPanel view2 = new JPanel();
    JPanel switchboard = new JPanel();
    JButton fire = new JButton("Fire");
    JLabel target = new JLabel("Target");
    JLabel score = new JLabel("Score");
    JLabel battleFieldLocations[][] = new JLabel[BattleWindow.battleFieldSize][BattleWindow.battleFieldSize];
    int[] coordinates = {-1, -1};


    public JLabel[][] createBattleField() {
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
        view1.setPreferredSize(new Dimension(200, 200));
        view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setPreferredSize(new Dimension(200, 200));
        view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                view1.add(battleFieldLocations[x][y]);
                view2.add(battleFieldLocations[x][y]);
            }
        }
        view1.setVisible(true);
        view2.setVisible(true);
        view1.revalidate();
        view2.revalidate();
        view1.repaint();
        view2.repaint();
        switchboard.add(fire);
        switchboard.add(target);
        switchboard.add(score);
        switchboard.setVisible(true);
        switchboard.repaint();
        add(view1);
        add(view2);
        add(switchboard);
        revalidate();
        repaint();
    }

    public GamePanel() {
        setLayout(new GridLayout(1, 3));
        createBattleField();
        displayGamePanelContents();
    }

}
