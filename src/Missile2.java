import java.awt.*;
import java.net.URL;

public class Missile2 extends Missile {
    public Missile2(int x, int y) {
        super(x, y, 2, 1);
    }

    @Override
    void initMissile() {
        URL url = getClass().getResource("resources/missile2_" + SpaceShip.direction + ".png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        loadImage(image);
        getImageDimensions();
    }
}