import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private ShapeShooter SpaceShooter;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private boolean ingame;
    private int score;

    private final Random random = new Random();
    private final int ICRAFT_X = SpaceShooter.WIDTH / 2;
    private final int ICRAFT_Y = SpaceShooter.HEIGHT - 200;
    private final int B_WIDTH = SpaceShooter.WIDTH;
    private final int B_HEIGHT = SpaceShooter.HEIGHT;
    private final int DELAY = 15;

    private final int THRESHOLD = 35;

    private int countLeft = 0;
    private int countRight = 0;

    int count = 0;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        //addKeyListener(new TAdapter());
        setFocusable(true);
        ingame = true;
        score = 0;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                boolean isShiftDown = e.isShiftDown();
                boolean isWheelRotationPositive = e.getWheelRotation() > 0;

                // UP
                if (!isShiftDown && isWheelRotationPositive) {
                    countLeft = 0;
                    countRight = 0;
                    spaceship.currentMissle = "Missile1";
                }

                // DOWN
                if (!isShiftDown && !isWheelRotationPositive) {
                    countLeft = 0;
                    countRight = 0;
                    spaceship.currentMissle = "Missile2";
                }

                // LEFT
                if (isShiftDown && isWheelRotationPositive) {
                    countLeft++;
                    countRight = 0;

                    if (countLeft >= 30) {
                        countLeft = 0;
                        if (spaceship.direction.equals("up")) {
                            spaceship.direction = "left";
                        } else if (spaceship.direction.equals("down")) {
                            spaceship.direction = "right";
                        } else if (spaceship.direction.equals("left")) {
                            spaceship.direction = "down";
                        } else if (spaceship.direction.equals("right")) {
                            spaceship.direction = "up";
                        }
                        spaceship.initCraft();
                    }
                }

                // RIGHT
                if (isShiftDown && !isWheelRotationPositive) {
                    countLeft = 0;
                    countRight++;

                    if (countRight >= THRESHOLD) {
                        countRight = 0;
                        if (spaceship.direction.equals("up")) {
                            spaceship.direction = "right";
                        } else if (spaceship.direction.equals("down")) {
                            spaceship.direction = "left";
                        } else if (spaceship.direction.equals("left")) {
                            spaceship.direction = "up";
                        } else if (spaceship.direction.equals("right")) {
                            spaceship.direction = "down";
                        }
                        spaceship.initCraft();
                    }
                }

//                if (!isShiftDown) {
//                    if (isWheelRotationPositive) {
//                        // UP
//                        spaceship.currentMissle = "Missile1";
//                    } else {
//                        // DOWN
//                        spaceship.currentMissle = "Missile2";
//                    }
//                } else {
//                    if (isWheelRotationPositive) {
//                        // LEFT
//
//                    } else {
//                        // RIGHT
//                    }
//                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spaceship.fire();
            }
        });

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
        aliens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            generateAlien();
        }
    }

    private void generateAlien() {
        int x = random.nextInt(B_WIDTH - 100);
        aliens.add(new Alien(x, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(new File("src/resources/background.png")), 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ingame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
        }

        List<Missile> missiles = spaceship.getMissiles();

        for (Missile missile : missiles) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 5, 15);
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);

        msg = "Score: " + score;
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2 + 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updateShip();
        updateMissiles();
        updateAliens();
        checkCollisions();
        repaint();
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {
        if (spaceship.isVisible()) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(point, this);
            int dx = (int) (point.getX() - (spaceship.width / 2));
            int dy = (int) (point.getY() - (spaceship.height / 2));
            spaceship.move(dx, dy);
        }
    }

    private void updateMissiles() {
        List<Missile> missiles = spaceship.getMissiles();
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if (missile.isVisible()) {
                missile.move();
            } else {
                missiles.remove(i);
            }
        }
    }

    private void updateAliens() {
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);
            if (alien.isVisible()) {
                alien.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    public void checkCollisions() {
        Rectangle r3 = spaceship.getBounds();
        for (Alien alien : aliens) {
            Rectangle r2 = alien.getBounds();
            if (r3.intersects(r2)) {
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> missiles = spaceship.getMissiles();

        for (Missile missile : missiles) {
            Rectangle r1 = missile.getBounds();
            for (Alien alien : aliens) {
                Rectangle r2 = alien.getBounds();
                if (r1.intersects(r2)) {
                    missile.setVisible(false);
                    alien.setHealth(alien.getHealth() - missile.getDamage());
                    if (alien.getHealth() <= 0) {
                        alien.setVisible(false);
                        score++;
                    }
                }
            }
        }

        while (aliens.size() < 10) {
            generateAlien();
        }
    }

//    private class TAdapter extends KeyAdapter {
//        @Override
//        public void keyReleased(KeyEvent e) {
//            spaceship.keyReleased(e);
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            spaceship.keyPressed(e);
//        }
//    }
}