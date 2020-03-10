import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

class ScreenReader {
    static String[] getPackageNames(String filepath) throws ParserConfigurationException, IOException, SAXException {
        HashSet<String> foundPackageNames = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( filepath ));

        /*Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factoryS = SchemaFactory.newInstance(language);
            schema = factoryS.newSchema(new File(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert schema != null;
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));*/

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();
        //System.out.println(root.getNodeName());

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
