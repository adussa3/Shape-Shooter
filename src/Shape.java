import javax.swing.*;
import java.awt.*;

public abstract class Shape extends JPanel {
    private Color color;
    private int health;
    private int sides;

    public Shape(Color color, int health, int sides) {
        this.color = color;
        this.health = health;
        this.sides = sides;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }
}
