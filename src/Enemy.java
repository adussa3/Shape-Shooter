import java.awt.*;
import java.util.Random;

public class Enemy extends Shape {
    private static final Random RANDOM = new Random();
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 5;

    private int speed;
    private Direction direction;

    public Enemy(Color color, int health, int sides) {
        this(color, health, sides, 1);
    }

    public Enemy(Color color, int health, int sides, int speed) {
        super(color, health, sides);
        this.speed = speed;
        this.direction = getRandomDirection();
    }

//    public Enemy(Color color, int health, int sides, int speed, Direction direction) {
//        super(color, health, sides);
//        this.speed = speed;
//        this.direction = direction;
//    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private int getRandomSpeed() {
        return RANDOM.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[RANDOM.nextInt(directions.length)];
    }
}
