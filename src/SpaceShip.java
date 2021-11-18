import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {
    private int dx;
    private int dy;
    private List<Missile> missiles;

    String currentMissle = "missile1";

    public SpaceShip(int x, int y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {
        missiles = new ArrayList<>();
        loadImage("src/resources/spaceship.png");
        getImageDimensions();
    }

    public void move(int dx, int dy) {
        // rotate


        //
        x = dx;
        y = dy;

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

//    public void keyPressed(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        if (key == KeyEvent.VK_SPACE) {
//            fire();
//        }
//
//        if (key == KeyEvent.VK_LEFT) {
//            dx = -1;
//        }
//
//        if (key == KeyEvent.VK_RIGHT) {
//            dx = 1;
//        }
//
//        if (key == KeyEvent.VK_UP) {
//            dy = -1;
//        }
//
//        if (key == KeyEvent.VK_DOWN) {
//            dy = 1;
//        }
//
//        if (key == KeyEvent.VK_Z) {
//            if (currentMissle.equals("Missile2")) {
//                currentMissle = "Missile1";
//            } else {
//                currentMissle = "Missile2";
//            }
//        }
//    }

//    public void keyReleased(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        if (key == KeyEvent.VK_LEFT) {
//            dx = 0;
//        }
//
//        if (key == KeyEvent.VK_RIGHT) {
//            dx = 0;
//        }
//
//        if (key == KeyEvent.VK_UP) {
//            dy = 0;
//        }
//
//        if (key == KeyEvent.VK_DOWN) {
//            dy = 0;
//        }
//    }
}