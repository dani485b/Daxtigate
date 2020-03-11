import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class MainPackagePanel extends JPanel {
    private float scrollOffsetY = 0;
    private ArrayList<PackageRow> componentList = new ArrayList<>();
    static final int ROWHEIGHT = 70;
    MainPackagePanel(){
        setLayout(null);
        setBackground(new Color(67, 67, 67));

        try {
            String[] packageNames = ScreenReader.getPackageNames("screenTime.xml");

            for (int i = 0; i < packageNames.length; i++) {
                PackageRow packRow = new PackageRow(packageNames[i], i);
                add(packRow);
                componentList.add(packRow);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        addMouseWheelListener(e -> {
            setScrollOffsetY(getScrollOffsetY()+e.getWheelRotation()*-10);

            for (PackageRow component : componentList) {
                component.updateLocationY();
                component.revalidate();
                component.repaint();
            }
        });
    }

    void setScrollOffsetY(float scrollOffsetY) {
        this.scrollOffsetY = scrollOffsetY;
    }

    float getScrollOffsetY() {
        return scrollOffsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Stroke dashedThick = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        Stroke dashedSmall = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2.setColor(new Color(100,100,100));

        for (int i = ROWHEIGHT+50; i < getWidth(); i += 50) {
            if((i-ROWHEIGHT)%200==0) {
                g2.setStroke(dashedThick);
            } else {
                g2.setStroke(dashedSmall);
            }

            g2.drawLine(i, 0, i, getHeight());
        }
        //System.out.println("Test");
    }
}
