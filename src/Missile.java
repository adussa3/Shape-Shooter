public abstract class Missile extends Sprite {

    private int damage;
    private String direction;
    private int speed;

    public Missile(int x, int y, int damage, int speed) {
        super(x, y);
        this.damage = damage;
        this.speed = speed;
        this.direction = SpaceShip.direction;
        initMissile();
    }

    abstract void initMissile();

    public void move() {
        if (direction.equals("up")) {
            y -= speed;
        } else if (direction.equals("down")) {
            y += speed;
        } else if (direction.equals("left"))  {
            x -= speed;
        } else if (direction.equals("right"))  {
            x += speed;
        }

        if (x < 0 || x > SpaceShooter.WIDTH || y < 0 || y > SpaceShooter.HEIGHT) {
            visible = false;
        }
    }

    public int getDamage() {
        return damage;
    }
}
