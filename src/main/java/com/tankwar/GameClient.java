package com.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient extends JComponent {

    private Tank playerTank;
    private List<Tank> enemyTanks;
    private List<Wall> walls;
    private List<Missile> missiles;
    private static final GameClient INSTANCE = new GameClient();

    static GameClient getInstance() {
        return INSTANCE;
    }


    public GameClient() {
        this.walls = Arrays.asList(
                new Wall(200, 140, true, 15),
                new Wall(200, 540, true, 15),
                new Wall(100, 80, false, 15),
                new Wall(700, 80, false, 15)
        );
        this.playerTank = new Tank(400,100,Direction.DOWN);
        this.enemyTanks = new ArrayList<>(12);
            for (int i=0; i<3; i++){
                for (int j=0; j<4; j++) {
                    this.enemyTanks.add(new Tank(200+j*120, 400+40*i,true,  Direction.UP));
                }
            }
        this.missiles = new ArrayList<>();

        //800*600 window:
        this.setPreferredSize(new Dimension(800,600));

    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        this.playerTank.draw(g);
        for (Tank tank : this.enemyTanks){
            tank.draw(g);
        }
        for (Wall wall : walls) {
            wall.draw(g);
        }
        for (Missile missile : missiles){
            missile.draw(g);
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        JFrame frame = new JFrame();
        frame.setTitle("CC 的坦克大战");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = GameClient.getInstance();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        //制于界面中间
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setVisible(true);

        while (true){
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
