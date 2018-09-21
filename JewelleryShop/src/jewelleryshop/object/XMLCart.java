package jewelleryshop.object;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLCart {

    public String fileNamae;

    public XMLCart(String s) {
        fileNamae = s;
    }

    public boolean clear() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document cart = docBuilder.newDocument();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(cart);
            StreamResult result = new StreamResult(new File(fileNamae));
            transformer.transform(source, result);
            return true;
        } catch (ParserConfigurationException | TransformerException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public boolean add(String id, String name, String type, String price) {
        Element rootElement, node, idElement, nameElement, priceElement;
        Attr attr;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document cart = docBuilder.newDocument();
            rootElement = cart.createElement("ShoppingCart");
            cart.appendChild(rootElement);

            // node elements
            node = cart.createElement("item");
            rootElement.appendChild(node);

            // set attribute to node element
            attr = cart.createAttribute("type");
            attr.setValue(type);
            node.setAttributeNode(attr);

            // shorten way
            // node.setAttribute("type", "order");
            // id elements
            idElement = cart.createElement("id");
            idElement.appendChild(cart.createTextNode(id));
            node.appendChild(idElement);

            // nameElement elements
            nameElement = cart.createElement("name");
            nameElement.appendChild(cart.createTextNode(name));
            node.appendChild(nameElement);

            // priceElement elements
            priceElement = cart.createElement("price");
            priceElement.appendChild(cart.createTextNode(price));
            node.appendChild(priceElement);

            try {
                File fXmlFile = new File(fileNamae);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document readFile = dBuilder.parse(fXmlFile);

                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                readFile.getDocumentElement().normalize();

                System.out.println("Root element :" + readFile.getDocumentElement().getNodeName());

                NodeList nList = readFile.getElementsByTagName("item");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        node = cart.createElement("item");
                        rootElement.appendChild(node);

                        attr = cart.createAttribute("type");
                        attr.setValue(eElement.getAttribute("type"));
                        node.setAttributeNode(attr);

                        idElement = cart.createElement("id");
                        idElement.appendChild(cart.createTextNode(eElement.getElementsByTagName("id").item(0).getTextContent()));
                        node.appendChild(idElement);

                        nameElement = cart.createElement("name");
                        nameElement.appendChild(cart.createTextNode(eElement.getElementsByTagName("name").item(0).getTextContent()));
                        node.appendChild(nameElement);

                        priceElement = cart.createElement("price");
                        priceElement.appendChild(cart.createTextNode(eElement.getElementsByTagName("price").item(0).getTextContent()));
                        node.appendChild(priceElement);

                        System.out.println("Type       : " + eElement.getAttribute("type"));
                        System.out.println("ID         : " + eElement.getElementsByTagName("id").item(0).getTextContent());
                        System.out.println("Item Name  : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                        System.out.println("Price      : " + eElement.getElementsByTagName("price").item(0).getTextContent());

                    }
                }
            } catch (IOException | ParserConfigurationException | DOMException | SAXException ex) {
                System.err.println(ex.getMessage());
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(cart);
            StreamResult result = new StreamResult(new File(fileNamae));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("Data Entered in XML File");
            return true;
        } catch (ParserConfigurationException | TransformerException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}