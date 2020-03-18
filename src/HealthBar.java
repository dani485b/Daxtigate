import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class HealthBar extends JPanel {
    MainPackagePanel mainPackagePanel = null;
    HealthBar() {
        setLayout(null);
        setBackground(new Color(100, 100, 100));

        int width = 2000;
        int height = MainPackagePanel.ROWHEIGHT;
        setMaximumSize(new Dimension(width,height));
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(new Dimension(width,height));
    }

    public void setMainPackagePanel(MainPackagePanel mainPackagePanel) {
        this.mainPackagePanel = mainPackagePanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(0.0f));

        int squareWidth = (int)(mainPackagePanel.getZoomScale()*50)+100;
        int squareHeight = 32;
        int cornerSize = 10;

        g2.setPaint(new Color(90, 90, 90));
        g2.fill(new Rectangle2D.Double(0,0, getBounds().height, getBounds().height));

        g2.setPaint(new Color(44, 143, 33));
        g2.fill(new RoundRectangle2D.Double(cornerSize+1000+mainPackagePanel.getDragX(), squareHeight/2f, squareWidth, squareHeight, cornerSize, cornerSize));

        //System.out.println("Test");
    }
}