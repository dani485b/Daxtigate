import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class PackageIconPanel extends ImagePanel {
    String packageName;
    PackageIconPanel(String packageName) {
        super("icons/"+packageName+".png");
        this.packageName = packageName;
    }

    private static final int sizeRectMax = 5;
    private static final int sizeRectMin = 0;
    private float sizeRectCurrent = sizeRectMin;
    private static final float speed = 0.2f;

    private int textX = 0;
    private int textY = 0;
    private String textToDisplay = "";
    private Rectangle2D.Double rect;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (drawImg) {
            if (sizeRectCurrent < sizeRectMax) {
                sizeRectCurrent += speed;
                rect = new Rectangle2D.Double((getWidth()-sizeRectCurrent*11)/2, getHeight()-20, sizeRectCurrent*11, sizeRectCurrent*3.5f);
                //Font font = new Font("Comic Sans MS", Font.BOLD, (int) (sizeRectCurrent*0.2));
                //g.setFont(font);

                textToDisplay = packageName;
                //FontMetrics metrics = g.getFontMetrics(font);
                // Determine the X coordinate for the text
                //textX = (int) (rect.x + (rect.width - metrics.stringWidth(textToDisplay)) / 2f);
                // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                //textY = (int) (rect.y + ((rect.height - metrics.getHeight()) / 2f) + metrics.getAscent());

                repaint();
            }
            Graphics2D g2 = (Graphics2D) g;

            g2.fillRoundRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height, 4,4);
            g2.setColor(new Color(255,255,255));
            g2.drawString(textToDisplay, textX, textY);
        } else {
            if (sizeRectCurrent > sizeRectMin) {
                sizeRectCurrent -= speed;
                repaint();
            }
        }
    }
}

class CenteredText extends JLabel {
    CenteredText(String text){
        super(text);
    }

    private boolean drawImg = false;

    private static final int sizeRectMax = PackageRow.ICON_SIZE;
    private static final int sizeRectMin = 48;
    private int sizeRectCurrent = sizeRectMin;

    private static final int speed = 1;

    public void setDrawImg(boolean drawImg) {
        this.drawImg = drawImg;
    }

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

        setFont(new Font("Comic Sans MS", Font.BOLD, sizeRectCurrent));
    }
}