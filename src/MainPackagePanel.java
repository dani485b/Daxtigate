import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class MainPackagePanel extends JPanel {
    private float scrollOffsetY = 0;
    MainPackagePanel(){
        setLayout(null);
        setBackground(new Color(67, 67, 67));

        try {
            String[] packageNames = ScreenReader.getPackageNames("screenTime.xml");

            for (int i = 0; i < packageNames.length; i++) {
                PackageRow packRow = new PackageRow(packageNames[i], i);
                add(packRow);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
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
        System.out.println("Test");
    }
}
