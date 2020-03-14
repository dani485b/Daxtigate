import javax.swing.*;
import java.awt.*;

public class HealthBar extends JPanel {
    HealthBar(){
        setLayout(null);
        setBackground(new Color(100, 100, 100));

        int width = 2000;
        int height = MainPackagePanel.ROWHEIGHT;
        setMaximumSize(new Dimension(width,height));
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(new Dimension(width,height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("Test");
    }
}