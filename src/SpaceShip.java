import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {
    private int dx;
    private int dy;
    boolean invincible;

    static String direction = "up";

    private List<Missile> missiles = new ArrayList<>();;
    String currentMissle = "missile1";

    public SpaceShip(int x, int y) {
        super(x, y);
        direction = "up";
        invincible = false;
        initCraft();
    }

    public void initCraft() {
        if (!invincible) {
            loadImage("src/resources/spaceship_" + direction + ".png");
        } else {
            loadImage("src/resources/spaceshipInvincible_" + direction + ".png");
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

        if (x > ShapeShooter.WIDTH - width) {
            x = ShapeShooter.WIDTH - width;
        }

        if (y > ShapeShooter.HEIGHT - height - 28) {
            y = ShapeShooter.HEIGHT - height - 28;
        }
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }

        if (x > ShapeShooter.WIDTH - width) {
            x = ShapeShooter.WIDTH - width;
        }

        if (y > ShapeShooter.HEIGHT - height - 28) {
            y = ShapeShooter.HEIGHT - height - 28;
        }
    }

//    public void move(int dx, int dy) {
//        x = dx;
//        y = dy;
//
//        if (x < 1) {
//            x = 1;
//        }
//
//        if (y < 1) {
//            y = 1;
//        }
//
//        if (x > ShapeShooter.WIDTH - width) {
//            x = ShapeShooter.WIDTH - width;
//        }
//
//        if (y > ShapeShooter.HEIGHT - height - 28) {
//            y = ShapeShooter.HEIGHT - height - 28;
//        }
//    }

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

        if (key == KeyEvent.VK_Z) {
            if (currentMissle.equals("Missile2")) {
                currentMissle = "Missile1";
            } else {
                currentMissle = "Missile2";
            }
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