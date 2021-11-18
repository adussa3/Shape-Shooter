public class Missile1 extends Missile {
    public Missile1(int x, int y) {
        super(x, y, 1, 2);
    }

    @Override
    void initMissile() {
        loadImage("src/resources/missile1.png");
        getImageDimensions();
    }
}
