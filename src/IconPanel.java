import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class PackageIconPanel extends ImagePanel {
    PackageIconPanel(String packageName) {
        super("icons/"+packageName+".png");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.4;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(15,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        add(new CenteredText(packageName), c);
    }

    private static final int sizeRectMax = 5;
    private static final int sizeRectMin = 0;
    private float sizeRectCurrent = sizeRectMin;
    private static final float speed = 0.2f;


    private Rectangle2D.Double rect;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (drawImg) {
            if (sizeRectCurrent < sizeRectMax) {
                sizeRectCurrent += speed;
                rect = new Rectangle2D.Double((getWidth()-sizeRectCurrent*11)/2, getHeight()-20, sizeRectCurrent*11, sizeRectCurrent*3.5f);
                repaint();
            }
            Graphics2D g2 = (Graphics2D) g;

            g2.fillRoundRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height, 4,4);
            g2.setColor(new Color(255,255,255));
        } else {
            if (sizeRectCurrent > sizeRectMin) {
                sizeRectCurrent -= speed;
                repaint();
            }
        }

        CenteredText centeredText = ((CenteredText)getComponent(0));
        centeredText.setDrawImg(drawImg);

    }
}

class CenteredText extends JLabel {
    private final String text;
    CenteredText(String text){
        super(text);
        setFont(new Font("Comic Sans MS", Font.BOLD, sizeRectCurrent));
        setForeground(new Color(230,230,230));
        this.text = text + "    ";
    }

    private boolean drawImg = true;

    private static final int sizeRectMax = 14;
    private static final int sizeRectMin = 3;
    private int sizeRectCurrent = sizeRectMin;

    private static final int speed = 2;

    void setDrawImg(boolean drawImg) {
        this.drawImg = drawImg;

        if (drawImg) setVisible(true);
    }

    private float iter = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (drawImg) {
            setVisible(true);
            if (sizeRectCurrent < sizeRectMax) {
                sizeRectCurrent += speed;
                setFont(new Font("Comic Sans MS", Font.BOLD, sizeRectCurrent));
            }

            final int showAtTime = 6;

            iter += 0.01;
            StringBuilder setString = new StringBuilder();
            for (int i = 0; i < showAtTime; i++) {
                int j = (((int) iter) + i) % (text.length() - 1);
                setString.append(text.toCharArray()[j]);
            }

            setText(setString.toString());
            repaint();
        } else {
            if (sizeRectCurrent > sizeRectMin) {
                sizeRectCurrent -= speed;
                setFont(new Font("Comic Sans MS", Font.BOLD, sizeRectCurrent));
                repaint();
            } else {
                iter = 0;
                setVisible(false);
            }
        }

    }
}