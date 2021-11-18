public abstract class Missile extends Sprite {
    private final int BOARD_HEIGHT = 600;
    private final int BOARD_WIDTH = 800;

    private int damage;
    private int speed;

    public Missile(int x, int y, int damage, int speed) {
        super(x, y);
        this.damage = damage;
        this.speed = speed;
        initMissile();
    }

    abstract void initMissile();

    public void move() {
        y -= speed;
        if (x < 0 || x > BOARD_WIDTH || y < 0 || y > BOARD_HEIGHT) {
            visible = false;
        }
    }

    public int getDamage() {
        return damage;
    }
}
