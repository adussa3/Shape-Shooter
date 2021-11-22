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

public class Board extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private Timer timer;
    private ShapeShooter SpaceShooter;
    private SpaceShip spaceship;
    public List<Alien> aliens;
    private boolean ingame;
    private int score;
    private static DollarRecognizer dollarRecognizer;
    private boolean isStroke;
    private boolean isDragging;
    private ArrayList<Point2D> strokePoints;
    private Result lastResult = null;

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


//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                spaceship.fire();
//            }
//        });
//
//        addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                Point p = SwingUtilities.convertPoint(this, e.getPoint(), SpaceShooter.panel);
//                points.add(p);
//                repaint();
//                if (e.isPopupTrigger()) {
//                    isStroke = true;
//                    strokePoints.add(e.getPoint());
//                    repaint();
//                } else {
//                    //if it just started a drag
//                    if (!isDragging) {
//                        isDragging = true;
//                    }
//                }
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//
//            }
//        });
//
//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (isStroke) {
//                    isStroke = false;
//                    stroke(strokePoints);
//                    strokePoints = new ArrayList<>();
//                    repaint();
//                }
//                isDragging = false;
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
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

    public void stroke(ArrayList<Point2D> p) {
        Result result = dollarRecognizer.recognize(p);
        String shape = result.getName();

        if (result.getName().equals("star")) {
            aliens = new ArrayList<>();
        } else if (result.getName().equals("triangle")) {
            for (int i = 0; i < aliens.size() / 2; i++) {
                aliens.remove(i);
            }
        } else if (result.getName().equals("circle")) {
            spaceship.invincible = true;
            spaceship.changeCraft();
        } else {
            spaceship.fire();
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

        JButton backButton = new JButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeShooter.cardLayout.show(ShapeShooter.panel, "home");
            }
        });
        backButton.setBounds(ShapeShooter.WIDTH / 2 - BUTTON_WIDTH / 2, ShapeShooter.HEIGHT - 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton.setText("Back");
        add(backButton);
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
            spaceship.move();
        }
    }

//    private void updateShip() {
//        if (spaceship.isVisible()) {
//            Point point = MouseInfo.getPointerInfo().getLocation();
//            SwingUtilities.convertPointFromScreen(point, this);
//            int dx = (int) (point.getX() - (spaceship.width / 2));
//            int dy = (int) (point.getY() - (spaceship.height / 2));
//            spaceship.move(dx, dy);
//        }
//    }

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
                if (spaceship.invincible) {
                    spaceship.invincible = false;
                    spaceship.changeCraft();
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        strokePoints = new ArrayList<Point2D>();
        // translate to canvas coordinate system
        Point p = SwingUtilities.convertPoint(this, e.getPoint(), SpaceShooter.panel);
        strokePoints.add(p);
        lastResult = null;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // translate to canvas coordinate system
        Point p = SwingUtilities.convertPoint(this, e.getPoint(), SpaceShooter.panel);
        strokePoints.add(p);
        Result result = dollarRecognizer.recognize(strokePoints);
        stroke(strokePoints);
        System.out.println(result.getName());
        this.lastResult = result;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isPopupTrigger()) {
            Point p = SwingUtilities.convertPoint(this, e.getPoint(), SpaceShooter.panel);
            strokePoints.add(p);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

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