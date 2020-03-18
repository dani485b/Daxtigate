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

class HealthExtractor {
    static HealthRecordReadSample[] getHealthReadSamples(String filepath) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<HealthRecordReadSample> foundTimeLineReadRecords = new ArrayList<>();

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

                HealthRecordReadSample tlrrs = new HealthRecordReadSample();
                tlrrs.type = eElement.getAttribute("type");
                tlrrs.timeStart = Long.parseLong(eElement.getAttribute("startDate"));
                tlrrs.timeEnd = Long.parseLong(eElement.getAttribute("endDate"));
                tlrrs.value = Float.parseFloat(eElement.getAttribute("value"));
                foundTimeLineReadRecords.add(tlrrs);
            }
        }

        return foundTimeLineReadRecords.toArray(new HealthRecordReadSample[0]);
    }
}

class HealthRecordReadSample extends SimpleTimeLineRecordElement {
    String type;
    float value;
}