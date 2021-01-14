package com.tankwar;

import java.awt.*;

public class Missile {
    private static final int SPEED = 10;
    private int x;
    private int y;
    private final boolean enemy;
    private final Direction direction;

    public Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    private void move() {
        x += direction.x * SPEED;
        y += direction.y * SPEED;
    }

    public Image getImage(){
        switch (direction) {
            case UP:
                return Tools.getImage("missileU.gif");
            case LEFT_UP:
                return Tools.getImage("missileLU.gif");
            case RIGHT_UP:
                return Tools.getImage("missileRU.gif");
            case DOWN:
                return Tools.getImage("missileD.gif");
            case LEFT_DOWN:
                return Tools.getImage("missileLD.gif");
            case RIGHT_DOWN:
                return Tools.getImage("missileRD.gif");
            case LEFT:
                return Tools.getImage("missileL.gif");
            case RIGHT:
                return Tools.getImage("missileR.gif");
        }
        return null;
    }

    public void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            return;
        }
        g.drawImage(this.getImage(), this.x, this.y, null);
    }
}
