import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
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
            URL url = getClass().getResource("resources/spaceship_" + direction + ".png");
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            loadImage(image);
        }
        if (invincibility == 1) {
            URL url = getClass().getResource("resources/spaceshipBlue_" + direction + ".png");
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            loadImage(image);
        }
        if (invincibility == 2) {
            URL url = getClass().getResource("resources/spaceshipGreen_" + direction + ".png");
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            loadImage(image);
        }
        if (invincibility == 3) {
            URL url = getClass().getResource("resources/spaceshipRed_" + direction + ".png");
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            loadImage(image);
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