import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PackageRow extends JPanel {
    private final String packageName;
    static final int ICON_SIZE = 64;
    private int elementCount;

    PackageRow(String packageName, int elementCount) {
        this.packageName = packageName;
        this.elementCount = elementCount;

        setOpaque(false);
        setBounds(0,elementCount*70, 2000,70);
        setLayout(null);

        ImagePanel iconPanel = new ImagePanel("icons/"+packageName+".png");
        add(iconPanel);

        addMouseWheelListener(e -> {
            MainPackagePanel mpp = ((MainPackagePanel)e.getComponent().getParent());
            mpp.setScrollOffsetY(mpp.getScrollOffsetY()+e.getWheelRotation()*-10);

            for (Component component : (e.getComponent().getParent()).getComponents()) {
                component.repaint();
                component.revalidate();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(0.0f));

        int squareWidth = 1280;
        int squareHeight = 32;
        int cornerSize = 10;

        setLocation(0, (int) (elementCount*70+((MainPackagePanel)getParent()).getScrollOffsetY()));;

        g2.setPaint(new Color(100, 100, 100));
        g2.fill(new Rectangle2D.Double(0,0, getBounds().height, getBounds().height));

        g2.setPaint(new Color(143, 20, 130));
        g2.fill(new RoundRectangle2D.Double(ICON_SIZE+cornerSize+10, squareHeight/2, squareWidth, squareHeight, cornerSize, cornerSize));

        super.paintComponent(g);
    }
}