package com.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

//用来控制它的坐标，从而达到移动的效果
public class Tank {
    private int x;

    private int y;

    private boolean enemy;

    private Direction direction;

    private static final int MOVE_SPEED = 5;

    public Tank(int x, int y, Direction direction) {
        this(x,y,false,direction);
    }

    public Tank(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    void move() {
        if (this.stopped) return;
        x += direction.x  * MOVE_SPEED;
        y += direction.y  * MOVE_SPEED;
    }

    void draw(Graphics g) {
        int oldX = x;
        int oldY = y;
        this.determineDirection();
        this.move();

        //boundary setting
        if (x < 0) x = 0;
        else if (x > 800- getImage().getWidth(null)) x = 800 - getImage().getWidth(null);
        if (y < 0) y = 0;
        else if (y > 600 - getImage().getHeight(null)) y = 600 - getImage().getHeight(null);

        Rectangle recTank = getRectangle();

        for(Wall wall : GameClient.getInstance().getWalls()){
            if(recTank.intersects(wall.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }

        for (Tank tank : GameClient.getInstance().getEnemyTanks()){
            if (recTank.intersects(tank.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }

        g.drawImage(this.getImage(), this.x, this.y, null);
    }



    public Rectangle getRectangle(){
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    Image getImage() {
        String prefix = enemy ? "e" : "";
        return direction.getImage(prefix + "tank");
        }

    private boolean up, down, right, left;
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up=true; break;
            case KeyEvent.VK_DOWN: down=true; break;
            case KeyEvent.VK_RIGHT: right=true; break;
            case KeyEvent.VK_LEFT: left=true; break;
            case KeyEvent.VK_CONTROL: fire(); break;
            case KeyEvent.VK_A: superFire(); break;
        }
        this.determineDirection();
    }

    private void superFire() {
        for (Direction direction : Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);

            GameClient.getInstance().getMissiles().add(missile);
        }

        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";
        playAudio(audioFile);
    }

    private void playAudio(String fileName) {
        Media sound = new Media(new File("assets/audios/" + fileName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);
        GameClient.getInstance().getMissiles().add(missile);

        playAudio("shoot.wav");
    }

    boolean stopped;

    private void determineDirection() {
        if (!up && !right && !down && !left) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
            else if (up && !left && !down && right) this.direction = Direction.RIGHT_UP;
            else if (up && !left && !down && !right) this.direction = Direction.UP;
            else if (!up && !left && down && !right) this.direction = Direction.DOWN;
            else if (!up && left && down && !right) this.direction = Direction.LEFT_DOWN;
            else if (!up && !left && down && right) this.direction = Direction.RIGHT_DOWN;
            else if (!up && left && !down && !right) this.direction = Direction.LEFT;
            else if (!up && !left && !down && right) this.direction = Direction.RIGHT;
            this.stopped = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up=false; break;
            case KeyEvent.VK_DOWN: down=false; break;
            case KeyEvent.VK_RIGHT: right=false; break;
            case KeyEvent.VK_LEFT: left=false; break;
        }
    }
}

