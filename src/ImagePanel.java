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
    private int yMain = 0;
    ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    public void setyMain(int yMain) {
        this.yMain = yMain;
    }

    private static final int sizeRectMax = 64;
    private static final int sizeRectMin = 48;
    private int sizeRectCurrent = sizeRectMin;

    private static final int speed = 1;
    private int offsetY = 0;

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getOffsetY() {
        return offsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBounds(getBounds().x, yMain+offsetY, getBounds().width, getBounds().height);

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