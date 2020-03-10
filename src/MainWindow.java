import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

public class MainWindow {
    private JFrame frame;

    MainWindow() {
        frame = new JFrame("My First GUI");
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setSize(400,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(67, 67, 67));
        frame.setContentPane(mainPanel);

        JPanel mainPicturePanel = new JPanel();
        mainPicturePanel.setLayout(null);
        mainPicturePanel.setBackground(new Color(100,100,100));
        mainPicturePanel.setBounds(0,0,90, 2000);
        //mainPicturePanel.setOpaque(false);

        mainPanel.add(mainPicturePanel);
        mainPicturePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                for (Component component : ((JPanel)e.getComponent()).getComponents()) {
                    ImagePanel imgP = (ImagePanel) component;

                    imgP.setOffsetY(imgP.getOffsetY()+e.getWheelRotation()*10);
                    imgP.repaint();
                }
            }
        });

        try {
            String[] packageNames = ScreenReader.getPackageNames("screenTime.xml");

            for (int i = 0; i < packageNames.length; i++) {
                ImagePanel img = new ImagePanel("icons/"+packageNames[i]+".png");
                img.setOpaque(false);
                img.setBounds(10,i*64+10,64, 64);//x axis, y axis, width, height
                img.setyMain(i*64+10);
                img.addMouseListener(new MyMouseAdapter(img));

                mainPicturePanel.add(img);//adding button in JFrame
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    void run(){
        frame.setVisible(true);
    }
}
