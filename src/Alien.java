import java.awt.*;
import java.net.URL;

public class Alien extends Sprite {
    private final int INITIAL_Y = 600;
    private int health = 4;

    public Alien(int x, int y) {
        super(x, y);
        initAlien();
    }

    private void initAlien() {
        URL url = getClass().getResource("resources/alien.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        loadImage(image);
        getImageDimensions();
    }

    public void move() {
        if (y > INITIAL_Y) {
            y = 0;
        }
        y += 1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}