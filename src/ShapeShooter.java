import javax.swing.*;
import java.awt.*;

public class ShapeShooter extends JFrame {
    public ShapeShooter() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        add(new Board());
        setTitle("Shape Shooter");
        setSize(800, 600);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShapeShooter ex = new ShapeShooter();
            ex.setVisible(true);
        });
    }
}
