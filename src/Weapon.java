import java.awt.*;

public class Weapon {
    private Color color;
    private int damage;
    private int speed;
    private int size;

    public Weapon(Color color, int damage, int speed, int size) {
        this.color = color;
        this.damage = damage;
        this.speed = speed;
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
