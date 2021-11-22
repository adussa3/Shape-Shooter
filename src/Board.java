import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
    public List<Alien> aliens;
    private static DollarRecognizer dollarRecognizer = new DollarRecognizer();
    private ArrayList<Point2D> strokePoints = new ArrayList<>();
    private final Random random = new Random();

    private final int ICRAFT_X = SpaceShooter.WIDTH / 2 - 50;
    private final int ICRAFT_Y = SpaceShooter.HEIGHT - 200;
    private final int B_WIDTH = SpaceShooter.WIDTH;
    private final int B_HEIGHT = SpaceShooter.HEIGHT;
    private final int DELAY = 15;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 60;
    private final int WEAPONS_THRESHOLD = 10;
    private final int ROTATION_THRESHOLD = 40;

    private boolean ingame;
    private int score;
    private int countUp = 0;
    private int countDown = 0;
    private int countLeft = 0;
    private int countRight = 0;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        ingame = true;
        score = 0;
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        addKeyListener(new TAdapter());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spaceship.fire();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                strokePoints = new ArrayList<Point2D>();
                strokePoints.add(e.getPoint());
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                strokePoints.add(e.getPoint());
                Result result = stroke(strokePoints);
                strokePoints = new ArrayList<>();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                strokePoints.add(e.getPoint());
                repaint();
            }
        });

        addMouseWheelListener(e -> {
            boolean isShiftDown = e.isShiftDown();
            boolean isWheelRotationPositive = e.getWheelRotation() > 0;

            // UP
            if (!isShiftDown && isWheelRotationPositive) {
                countUp++;
                countDown = 0;

                if (countUp >= WEAPONS_THRESHOLD) {
                    countUp = 0;
                    spaceship.currentMissle = "Missile1";
                }
            }

            // DOWN
            if (!isShiftDown && !isWheelRotationPositive) {
                countUp = 0;
                countDown++;

                if (countDown >= WEAPONS_THRESHOLD) {
                    countDown = 0;
                    spaceship.currentMissle = "Missile2";
                }
            }

            // LEFT
            if (isShiftDown && isWheelRotationPositive) {
                countUp = 0;
                countDown = 0;
                countLeft++;
                countRight = 0;

                if (countLeft >= ROTATION_THRESHOLD) {
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
                countUp = 0;
                countDown = 0;
                countLeft = 0;
                countRight++;

                if (countRight >= ROTATION_THRESHOLD) {
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

    public Result stroke(ArrayList<Point2D> p) {
        Result result = dollarRecognizer.recognize(p);

        if (result.getName().equals("star")) {
            score += aliens.size();
            aliens = new ArrayList<>();
        } else if (result.getName().equals("triangle")) {
            for (int i = 0; i <= (aliens.size() / 2); i++) {
                score++;
                aliens.remove(0);
            }
        }

        if (spaceship.invincibility == 0) {
            if (result.getName().equals("circle")) {
                spaceship.invincibility = 1;
                spaceship.initCraft();
            } else if (result.getName().equals("rectangle")) {
                spaceship.invincibility = 2;
                spaceship.initCraft();
            } else if (result.getName().equals("pigtail")) {
                spaceship.invincibility = 3;
                spaceship.initCraft();
            }
        }

        return result;
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

        // draw stroke on screen for user to see
        g.setColor(Color.white);
        for (int i = 0; i < strokePoints.size() - 1; i++) {
            g.drawLine((int) strokePoints.get(i).getX(), (int) strokePoints.get(i).getY(), (int) strokePoints.get(i + 1).getX(), (int) strokePoints.get(i + 1).getY());
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

        JButton backButton = new JButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpaceShooter.cardLayout.show(SpaceShooter.panel, "home");
            }
        });
        backButton.setBounds(SpaceShooter.WIDTH / 2 - BUTTON_WIDTH / 2, SpaceShooter.HEIGHT - 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton.setText("Back");
        add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        grabFocus();

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
            spaceship.move();
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
                spaceship.invincibility--;
                if (spaceship.invincibility >= 0) {
                    score++;
                    alien.setVisible(false);
                    spaceship.initCraft();
                } else {
                    spaceship.setVisible(false);
                    alien.setVisible(false);
                    ingame = false;
                }
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

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}