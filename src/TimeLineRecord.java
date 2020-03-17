import javax.swing.*;
import java.text.DateFormat;

class TimeLineRecord {
    private String packageName;
    private String className = "N/A";
    private long timeStart;
    private long timeEnd;
    private ACTIVITY_STATE state = ACTIVITY_STATE.inactive;


    TimeLineRecord(String name, long timeStart, long timeEnd) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        packageName = name;
    }

    public void setClassName(String className) {
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

class TimeLineRecordPanel extends JPanel {
    TimeLineRecord record;
}

enum ACTIVITY_STATE {
    active,
    inactive,
}