import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class TopBar extends JPanel {
    TopBar(){
        setLayout(null);
        setBackground(new Color(130, 130, 130));

        int width = 2000;
        int height = 35;
        setMaximumSize(new Dimension(width,height));
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(new Dimension(width,height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("Test");
    }
}