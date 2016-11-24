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
    JPanel views = new JPanel();
    JPanel switchboard = new JPanel();
    JButton fire = new JButton("Fire");
    JLabel target = new JLabel("Target");
    JLabel score = new JLabel("Score");
    JLabel[][] bfl1;
    JLabel[][] bfl2;
    int[] coordinates = {-1, -1};


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
        view1.setOpaque(true);
        view1.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setPreferredSize(new Dimension(200, 200));
        view2.setLayout(new GridLayout(BattleWindow.battleFieldSize,BattleWindow.battleFieldSize));
        view2.setOpaque(true);
        view1.setLocation(0,0);
        view2.setLocation(200,0);
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                bfl1 = createBattleField();
                bfl2 = createBattleField();
            }
        }
        for (int x = 0; x < BattleWindow.battleFieldSize; x++) {
            for (int y = 0; y < BattleWindow.battleFieldSize; y++) {
                view1.add(bfl1[x][y]);
                view2.add(bfl2[x][y]);
            }
        }
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

    public GamePanel() {
        setLayout(new FlowLayout());
        createBattleField();
        displayGamePanelContents();
    }

}
