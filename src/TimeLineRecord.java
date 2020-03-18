import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;

class TimeLineRecord extends SimpleTimeLineRecordElement {
    private String packageName;
    private String className = "N/A";
    private ACTIVITY_STATE state = ACTIVITY_STATE.unknown;


    TimeLineRecord(String name, long timeStart, long timeEnd) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        packageName = name;
    }

    ACTIVITY_STATE getState() {
        return state;
    }

    String getPackageName() {
        return packageName;
    }

    void setClassName(String className) {
        this.className = className;
    }

    void setState(ACTIVITY_STATE state) {
        this.state = state;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        return "This Record: "
                + "\n   * " + "Name: " + packageName
                + "\n   * " + "State: " + state
                + "\n   * " + "Start: " + dateFormat.format(timeStart)
                + "\n   * " + "End: " + dateFormat.format(timeEnd)
                + "\n   * " + "Class: " + className
                ;
    }
}

class SimpleTimeLineRecordElement {
    long timeStart;
    long timeEnd;

    long getTimeStart() {
        return timeStart;
    }

    long getTimeEnd() {
        return timeEnd;
    }
}

class TimeLineRecordPanel extends JPanel {
    private final SimpleTimeLineRecordElement record;
    private final MainPackagePanel mpp;
    boolean hoverOver = false;

    TimeLineRecordPanel(SimpleTimeLineRecordElement record, MainPackagePanel mpp){
        this.record = record;
        this.mpp = mpp;
        updateSize();
        setOpaque(false);
        addMouseListener(new MyTimelineMouseListener());
    }

    private float convertToScaledTime(long val){
        return (val/1000f)*mpp.getZoomScale();
    }

    void updateSize(){
        float x = convertToScaledTime(record.getTimeStart()-1584530000000L) + mpp.getDragX();
        float width = ((record.getTimeEnd()-record.getTimeStart())/1000f)*mpp.getZoomScale();

        //System.out.println(record.getTimeStart() + " - " + x + " - " + width);

        setBounds((int)x, 17, (int)width, 34);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (record instanceof TimeLineRecord) {
            if (((TimeLineRecord) record).getState() == ACTIVITY_STATE.active) {
                g2.setColor(new Color(135, 15, 132));
            } else {
                g2.setColor(new Color(183, 57, 53));
            }
        }

        if(hoverOver) {
            g2.setColor(new Color(183, 15, 113));
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);

        if (hoverOver) {
            g2.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            g.drawString(String.valueOf((record.getTimeEnd()-record.getTimeStart())/1000f/60f), getWidth()/2,20);
        }
    }
}

class MyTimelineMouseListener extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        TimeLineRecordPanel imgP = (TimeLineRecordPanel) e.getComponent();
        imgP.hoverOver = true;
        imgP.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        TimeLineRecordPanel imgP = (TimeLineRecordPanel) e.getComponent();
        imgP.hoverOver = false;
        imgP.repaint();
    }
}

enum ACTIVITY_STATE {
    active,
    inactive,
    unknown
}