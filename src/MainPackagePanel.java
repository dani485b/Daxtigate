import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPackagePanel extends JPanel {
    HealthBar healthBar = null;

    //Stuff
    private float scrollOffsetY = 0;
    private float zoomScale = 5f;
    private float dragX = 0f;

    //Stuff
    private ArrayList<PackageRow> componentList = new ArrayList<>();
    static final int ROWHEIGHT = 70;
    HashMap<String, ArrayList<TimeLineRecord>> timeLineRecords;

    //Stuff
    private Point origin = new Point(0,0);
    private Point mousePt;
    MainPackagePanel(HealthBar hb){
        healthBar = hb;
        setLayout(null);
        setBackground(new Color(67, 67, 67));
        healthBar.setMainPackagePanel(this);

        try {
            timeLineRecords = ScreenReader.getAggregateTimeLineRecords("screenTime.xml");
            String[] packageNames = ScreenReader.getPackageNamesSortedByUsage(timeLineRecords);

            for (int i = 0; i < packageNames.length; i++) {
                PackageRow packRow = new PackageRow(packageNames[i], i, timeLineRecords.get(packageNames[i]),this);
                add(packRow);
                componentList.add(packRow);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        addMouseWheelListener(e -> {
            setZoomScale(getZoomScale()+e.getWheelRotation()*-0.1f);

            for (PackageRow component : componentList) {
                component.updateLocationY();
                component.revalidate();
                component.repaint();
            }

            healthBar.update();
            healthBar.repaint();
        });

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                origin.setLocation(origin.x + dx, origin.y + dy);
                mousePt = e.getPoint();

                setScrollOffsetY(getScrollOffsetY()+dy);
                setDragX(getDragX()+dx);

                for (PackageRow component : componentList) {
                    component.updateLocationY();
                    component.revalidate();
                    component.repaint();
                }

                healthBar.update();
                healthBar.repaint();
            }
        });
    }

    private void setScrollOffsetY(float scrollOffsetY) {
        float heightMax = getComponentCount()*ROWHEIGHT;
        heightMax -= getHeight();
        this.scrollOffsetY = Math.max(Math.min(scrollOffsetY, 0), -heightMax);
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    float getScrollOffsetY() {
        return scrollOffsetY;
    }

    float getZoomScale() {
        return zoomScale;
    }

    private void setZoomScale(float zoomScale) {
        this.zoomScale = Math.min(Math.max(zoomScale, 0.01f), 20f);
    }

    float getDragX() {
        return dragX;
    }

    private void setDragX(float dragX) {
        this.dragX = Math.min(Math.max(dragX, -10000), 10000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Stroke dashedThick = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        Stroke dashedSmall = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2.setColor(new Color(100,100,100));

        //System.out.println(zoomScale);
        float pixelAmount = zoomScale*50;
        float centerX = ((getWidth()-ROWHEIGHT)/2f)+ROWHEIGHT;

        for (int i = 0; i < 20; i++) {
            //g2.setStroke(dashedThick);
            g2.setStroke(dashedSmall);

            int x = (int)(ROWHEIGHT+i*pixelAmount+dragX);

            g2.drawLine(x, 0, x, getHeight());
        }

        Point p = getMousePosition();
        float stuff = ((p.x-getDragX())/getZoomScale());
        System.out.println(stuff);
        g.setColor(Color.white);
        g.drawString(DateFormat.getDateTimeInstance().format(stuff*1000+1584530000000L), p.x, p.y);
        //System.out.println("Test");
    }
}
