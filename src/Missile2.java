public class Missile2 extends Missile {
    public Missile2(int x, int y) {
        super(x, y, 2, 1);
    }

    @Override
    void initMissile() {
        loadImage("src/resources/missile2_" + SpaceShip.direction + ".png");
        getImageDimensions();
    }
}