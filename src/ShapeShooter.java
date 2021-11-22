import javax.swing.*;
import java.awt.*;

public class ShapeShooter extends JFrame {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    static CardLayout cardLayout = new CardLayout();
    static JPanel panel = new JPanel(cardLayout);
    static Board board = new Board();

    public ShapeShooter() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        Home home = new Home();
        Instructions instructions = new Instructions();
        panel.add(home, "home");
        panel.add(instructions, "instructions");



        add(board);
        pack();

        setResizable(false);
        setSize(WIDTH, HEIGHT);

        setTitle("Shape Shooter");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShapeShooter ex = new ShapeShooter();
            ex.setVisible(true);
        });
    }
}
