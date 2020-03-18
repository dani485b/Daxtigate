import javafx.animation.Timeline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class ScreenReader {
    static String[] getPackageNames(String filepath) throws ParserConfigurationException, IOException, SAXException {
        HashSet<String> foundPackageNames = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( filepath ));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Get all employees
        NodeList nList = document.getElementsByTagName("Record");
        //System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            //System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;
                //System.out.println("Package nam : "    + eElement.getAttribute("packageName"));
                foundPackageNames.add(eElement.getAttribute("packageName"));
            }
        }

        return foundPackageNames.toArray(new String[0]);
    }

    static String[] getPackageNamesSortedByUsage(HashMap<String, ArrayList<TimeLineRecord>> timeLineRecords) {
        HashMap<String, Long> foundPackageNames = new HashMap<>();

        for (String key : timeLineRecords.keySet()) {
            ArrayList<TimeLineRecord> arrayList = timeLineRecords.get(key);
            long val = 0;
            for (TimeLineRecord timelineRecord : arrayList) {
                val += timelineRecord.getTimeEnd() - timelineRecord.getTimeStart();
            }

            foundPackageNames.put(key, val);
        }

        String[] idk = new String[foundPackageNames.size()];

        List<Map.Entry<String, Long>> list = new ArrayList<>(foundPackageNames.entrySet());
        list.sort(Map.Entry.comparingByValue());

        for (int i = 0; i < foundPackageNames.size(); i++) {
            idk[foundPackageNames.size()-1-i] = list.get(i).getKey();
        }

        return idk;
    }

    static TimeLineRecordReadSample[] getTimeLineRReadSamples(String filepath) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<TimeLineRecordReadSample> foundTimeLineReadRecords = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( filepath ));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Get all employees
        NodeList nList = document.getElementsByTagName("Record");
        //System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            //System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;

                TimeLineRecordReadSample tlrrs = new TimeLineRecordReadSample();
                tlrrs.type = eElement.getAttribute("type");
                tlrrs.timeStamp = Long.parseLong(eElement.getAttribute("timeStamp"));
                tlrrs.packageName = eElement.getAttribute("packageName");
                tlrrs.className = eElement.getAttribute("className");
                foundTimeLineReadRecords.add(tlrrs);

                /*if (tlrrs.type.equals("ACTIVITY_PAUSED")){
                    foundTimeLineReadRecords.add(tlrrs);
                }*/
            }
        }

        return foundTimeLineReadRecords.toArray(new TimeLineRecordReadSample[0]);
    }

    static TimeLineRecord[] getTimeLineRecords(String filepath) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<TimeLineRecord> foundTimeLineRecords = new ArrayList<>();
        HashMap<String, TimeLineRecordReadSample> existingClassNames = new HashMap<>();

        TimeLineRecordReadSample[] timeLineRecordReadSamples = getTimeLineRReadSamples(filepath);
        long firstTimeStamp = timeLineRecordReadSamples[0].timeStamp;
        long lastTimeStamp = timeLineRecordReadSamples[timeLineRecordReadSamples.length-1].timeStamp;

        for (TimeLineRecordReadSample tlrrs : timeLineRecordReadSamples) {
            if (existingClassNames.containsKey(tlrrs.className)) { //Already present - Should record
                TimeLineRecordReadSample prevSample = existingClassNames.get(tlrrs.className);
                existingClassNames.replace(tlrrs.className, tlrrs);

                TimeLineRecord tlr = new TimeLineRecord(
                        tlrrs.packageName,
                        prevSample.timeStamp,
                        tlrrs.timeStamp
                );

                if (tlrrs.type.equals("ACTIVITY_STOPPED")){
                    tlr.setState(ACTIVITY_STATE.inactive); //Is the stop of an inactive activity
                    existingClassNames.remove(tlrrs.className);
                } else if (tlrrs.type.equals("ACTIVITY_RESUMED")) {
                    tlr.setState(ACTIVITY_STATE.inactive); //Is the stop of an active activity
                } else {
                    tlr.setState(ACTIVITY_STATE.active); //Is the stop of an active activity
                }

                tlr.setClassName(tlrrs.className);

                foundTimeLineRecords.add(tlr);

            } else { //Otherwise, put in map
                if (tlrrs.type.equals("ACTIVITY_RESUMED"))
                    existingClassNames.put(tlrrs.className, tlrrs);
                else {
                    TimeLineRecord tlr = new TimeLineRecord (
                            tlrrs.packageName,
                            firstTimeStamp,
                            tlrrs.timeStamp
                    );

                    tlr.setClassName(tlrrs.className);
                }
            }
        }

        for (String key : existingClassNames.keySet()) {
            TimeLineRecordReadSample readSample = existingClassNames.get(key);

            TimeLineRecord tlr = new TimeLineRecord(
                    readSample.packageName,
                    readSample.timeStamp,
                    lastTimeStamp
            );

            foundTimeLineRecords.add(tlr);
        }

        return foundTimeLineRecords.toArray(new TimeLineRecord[0]);
    }

    static HashMap<String, ArrayList<TimeLineRecord>> getAggregateTimeLineRecords(String filepath) throws ParserConfigurationException, IOException, SAXException {
        TimeLineRecord[] timeLineRecords = getTimeLineRecords(filepath);
        HashMap<String, ArrayList<TimeLineRecord>> aggregateRecords = new HashMap<>();

        for (TimeLineRecord tlr : timeLineRecords) {
            if (aggregateRecords.containsKey(tlr.getPackageName())) {
                aggregateRecords.get(tlr.getPackageName()).add(tlr);
            } else {
                ArrayList<TimeLineRecord> startOfArray = new ArrayList<>();
                startOfArray.add(tlr);
                aggregateRecords.put(tlr.getPackageName(), startOfArray);
            }
        }

        return aggregateRecords;
    }
}

class TimeLineRecordReadSample {
    String packageName;
    String className;
    String type;
    long timeStamp;
}