import java.awt.*;
import java.net.URL;

public class Missile1 extends Missile {
    public Missile1(int x, int y) {
        super(x, y, 1, 2);
    }

    @Override
    void initMissile() {
        URL url = getClass().getResource("resources/missile1.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        loadImage(image);
        getImageDimensions();
    }
}
