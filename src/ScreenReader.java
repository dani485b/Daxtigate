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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    static TimeLineRecord[] getTimeLineRecords(String filepath) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<TimeLineRecord> foundTimeLineRecords = new ArrayList<>();
        HashMap<String, TimeLineRecordReadSample> existingClassNames = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( filepath ));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Get all employees
        NodeList nList = document.getElementsByTagName("Record");
        //System.out.println("============================");

        long lastTimeStamp = 0;

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            //System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;

                String className = eElement.getAttribute("className");
                TimeLineRecordReadSample tlrrs = new TimeLineRecordReadSample();
                tlrrs.type = eElement.getAttribute("type");
                tlrrs.timeStamp = Long.parseLong(eElement.getAttribute("timeStamp"));
                tlrrs.packageName = eElement.getAttribute("packageName");

                if (existingClassNames.containsKey(className)) { //Already present - Should record

                    TimeLineRecord tlr = new TimeLineRecord(
                            tlrrs.packageName,
                            existingClassNames.get(className).timeStamp,
                            tlrrs.timeStamp
                            );

                    if (tlrrs.type.equals("ACTIVITY_STOPPED")){
                        tlr.setState(ACTIVITY_STATE.inactive); //Is the stop of an inactive activity
                    } else {
                        tlr.setState(ACTIVITY_STATE.active); //Is the stop of an active activity
                    }

                    tlr.setClassName(className);

                    foundTimeLineRecords.add(tlr);
                    existingClassNames.remove(className);
                } else { //Otherwise, put in map
                    if (!tlrrs.type.equals("ACTIVITY_STOPPED"))
                        existingClassNames.put(className, tlrrs);
                }

                if (temp == nList.getLength()-1){
                    lastTimeStamp = tlrrs.timeStamp;
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
    String type;
    long timeStamp;
}