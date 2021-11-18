import javax.swing.*;
import java.awt.*;

public class ShapeShooter extends JFrame {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    public ShapeShooter() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        add(new Board());
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
