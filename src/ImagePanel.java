import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    boolean drawImg = false;
    private BufferedImage image;
    ImagePanel(String path) {
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

        g.drawImage(image, (sizeRectMax-sizeRectCurrent)/2, ((sizeRectMax-sizeRectCurrent)/2), sizeRectCurrent, sizeRectCurrent, this);
    }
}

class MyMouseAdapter extends MouseAdapter {
    private final ImagePanel imgP;

    MyMouseAdapter(ImagePanel imgP) {
        this.imgP = imgP;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        imgP.drawImg = true;
        imgP.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        imgP.drawImg = false;
        imgP.repaint();
    }
}