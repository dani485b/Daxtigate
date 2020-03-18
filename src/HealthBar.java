import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

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

    void update(){
        for (Component cp : getComponents()) {
            if (cp instanceof TimeLineRecordPanel)
                ((TimeLineRecordPanel) cp).updateSize();
        }
    }

    void setMainPackagePanel(MainPackagePanel mainPackagePanel) {
        this.mainPackagePanel = mainPackagePanel;

        /*try {
            HealthRecordReadSample[] hrrs = HealthExtractor.getHealthReadSamples("healthData.xml");
            for (int i = 0; i < hrrs.length; i++) {
                TimeLineRecordPanel timeLineRecordPanel = new TimeLineRecordPanel(hrrs[i], mainPackagePanel);
                add(timeLineRecordPanel);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(0.0f));
        g2.setPaint(new Color(90, 90, 90));
        g2.fill(new Rectangle2D.Double(0,0, getBounds().height, getBounds().height));

        //System.out.println("Test");
    }
}