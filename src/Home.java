import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Home extends JPanel {
    private ShapeShooter shapeShooter;

    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 60;

    public Home() {
        initHome();
    }

    private void initHome() {
        setPreferredSize(new Dimension(ShapeShooter.WIDTH, ShapeShooter.HEIGHT));
        setLayout(null);

        JButton instructionsButton = new JButton();
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeShooter.cardLayout.show(shapeShooter.panel, "instructions");
            }
        });
        instructionsButton.setBounds(ShapeShooter.WIDTH / 2 - BUTTON_WIDTH / 2,ShapeShooter.HEIGHT - 150 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        instructionsButton.setText("Instructions");
        add(instructionsButton);

        JButton startButton = new JButton();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = new Board();
                shapeShooter.panel.add(board, "board");
                shapeShooter.cardLayout.show(shapeShooter.panel, "board");
            }
        });
        startButton.setBounds(ShapeShooter.WIDTH / 2 - BUTTON_WIDTH / 2,ShapeShooter.HEIGHT - 150, BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setText("Start");
        add(startButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(new File("src/resources/home.png")), 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
