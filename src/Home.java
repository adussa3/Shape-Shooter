import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Home extends JPanel {
    private SpaceShooter spaceShooter;

    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 60;

    public Home() {
        initHome();
    }

    private void initHome() {
        setPreferredSize(new Dimension(SpaceShooter.WIDTH, SpaceShooter.HEIGHT));
        setLayout(null);

        JButton instructionsButton = new JButton();
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceShooter.cardLayout.show(spaceShooter.panel, "instructions");
            }
        });
        instructionsButton.setBounds(SpaceShooter.WIDTH / 2 - BUTTON_WIDTH / 2, SpaceShooter.HEIGHT - 150 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        instructionsButton.setText("Instructions");
        add(instructionsButton);

        JButton startButton = new JButton();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = new Board();
                spaceShooter.panel.add(board, "board");
                spaceShooter.cardLayout.show(spaceShooter.panel, "board");
            }
        });
        startButton.setBounds(SpaceShooter.WIDTH / 2 - BUTTON_WIDTH / 2, SpaceShooter.HEIGHT - 150, BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setText("Start");
        add(startButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        URL url = getClass().getResource("resources/home.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        g.drawImage(image, 0, 0, this);
    }
}
