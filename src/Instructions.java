import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Instructions extends JPanel {
    private SpaceShooter spaceShooter;

    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 60;

    public Instructions() {
        initInstructions();
    }

    private void initInstructions() {
        setPreferredSize(new Dimension(SpaceShooter.WIDTH, SpaceShooter.HEIGHT));
        setLayout(null);

        JButton backButton = new JButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceShooter.cardLayout.show(spaceShooter.panel, "home");
            }
        });
        backButton.setBounds(SpaceShooter.WIDTH / 2 - BUTTON_WIDTH / 2, SpaceShooter.HEIGHT - 115, BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton.setText("Back");
        add(backButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(new File("src/resources/instructions.png")), 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
