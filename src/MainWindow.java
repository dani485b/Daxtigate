import javafx.animation.Timeline;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class MainWindow {
    private JFrame frame;
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 1000;

    MainWindow() {
        frame = new JFrame("My First GUI");

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(new Color(100, 100, 100));
        frame.setContentPane(mainPanel);

        TopBar topBar = new TopBar();
        mainPanel.add(topBar);

        HealthBar healthBar = new HealthBar();
        mainPanel.add(healthBar);

        MainPackagePanel mainPackagePanel = new MainPackagePanel();
        mainPanel.add(mainPackagePanel);

        try {
            HashMap<String, ArrayList<TimeLineRecord>> timeLineRecords = ScreenReader.getAggregateTimeLineRecords("screenTime.xml");
            ArrayList<TimeLineRecord> discordUsage = timeLineRecords.get("com.discord");

            for (int i = 0; i < discordUsage.size(); i++) {
                System.out.println(discordUsage.get(i));
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    void run(){
        frame.setVisible(true);
    }
}
