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
}
