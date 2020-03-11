import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    boolean drawImg = false;
    private BufferedImage image;
    ImagePanel(String path) {
        setOpaque(false);
        setBounds(0, 0, PackageRow.ICON_SIZE, PackageRow.ICON_SIZE);
        addMouseListener(new MyImagePanelMouseListener());

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    private static final int sizeRectMax = 64;
    private static final int sizeRectMin = 48;
    private int sizeRectCurrent = sizeRectMin;

    private static final int speed = 1;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (drawImg) {
            if (sizeRectCurrent < sizeRectMax) {
                sizeRectCurrent += speed;
                repaint();
            }
        } else {
            if (sizeRectCurrent > sizeRectMin) {
                sizeRectCurrent -= speed;
                repaint();
            }
        }

        int currentX = (getWidth()-sizeRectCurrent)/2;
        int currentY = (getWidth()-sizeRectCurrent)/2;

        g.drawImage(image, currentX, currentY, sizeRectCurrent, sizeRectCurrent, this);
    }
}

class MyImagePanelMouseListener extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        ImagePanel imgP = (ImagePanel) e.getComponent();
        imgP.drawImg = true;
        imgP.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImagePanel imgP = (ImagePanel) e.getComponent();
        imgP.drawImg = false;
        imgP.repaint();
    }
}