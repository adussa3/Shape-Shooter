import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {
    private int dx;
    private int dy;
    int invincibility;
    static String direction = "up";
    private List<Missile> missiles = new ArrayList<>();;
    String currentMissle = "missile1";

    public SpaceShip(int x, int y) {
        super(x, y);
        direction = "up";
        invincibility = 0;
        initCraft();
    }

    public void initCraft() {
        if (invincibility == 0) {
            loadImage("src/resources/spaceship_" + direction + ".png");
        }
        if (invincibility == 1){
            loadImage("src/resources/spaceshipBlue_" + direction + ".png");
        }
        if (invincibility == 2){
            loadImage("src/resources/spaceshipGreen_" + direction + ".png");
        }
        if (invincibility == 3) {
            loadImage("src/resources/spaceshipRed_" + direction + ".png");
        }
        getImageDimensions();
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }

        if (x > SpaceShooter.WIDTH - width) {
            x = SpaceShooter.WIDTH - width;
        }

        if (y > SpaceShooter.HEIGHT - height - 28) {
            y = SpaceShooter.HEIGHT - height - 28;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void fire() {
        Missile missile = new Missile1(x + (width / 2), y);
        if (currentMissle.equals("Missile2")) {
            missile = new Missile2(x + (width / 2), y);
        }
        missiles.add(missile);
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 2;
        }

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}