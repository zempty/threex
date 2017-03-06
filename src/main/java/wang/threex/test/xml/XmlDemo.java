package wang.threex.test.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jfinal.log.Log;

public class XmlDemo {

    private static final Log log = Log.getLog(XmlDemo.class);

    private Document document;
    private String filename;

    private void init() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        
    }

    public void createXml(String filename) {
        init();
        Element root = this.document.createElement("employees");
        this.document.appendChild(root);

        Element employee = this.document.createElement("employee");
        Element name = this.document.createElement("name");

        name.appendChild(this.document.createTextNode("chuchu"));
        employee.appendChild(name);

        Element age = this.document.createElement("age");
        age.appendChild(this.document.createTextNode("25"));
        employee.appendChild(age);

        root.appendChild(employee);
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);

            transformer.setOutputProperty(OutputKeys.ENCODING, "uft-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
            StreamResult result = new StreamResult(pw);

            transformer.transform(source, result);
            log.info("xml文件生成成功！");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void parserXml(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            //将xml文件解析
            Document document = db.parse(fileName);
            
            //获得所有节点，递归遍历节点
            NodeList employees = document.getChildNodes();
            for (int i = 0; i < employees.getLength(); i++) {
                //取得一个节点
                Node employee = employees.item(i);
                
                
                NodeList employeeInfo = employee.getChildNodes();
                for (int j = 0; j < employeeInfo.getLength(); j++) {
                    Node node = employeeInfo.item(j);
                    NodeList employeeMeta = node.getChildNodes();
                    for (int k = 0; k < employeeMeta.getLength(); k++) {
                        System.out.println(employeeMeta.item(k).getNodeName()
                                + ":" + employeeMeta.item(k).getTextContent());
                    }
                }
            }
            System.out.println("解析完毕");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
