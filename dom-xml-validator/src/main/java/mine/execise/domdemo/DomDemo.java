package mine.execise.domdemo;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DomDemo {
	public static void main(String[] args) throws Exception {
		new DomDemo().validateXML();
	}

	private Document parseXML() {
		try {
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
			Document doc = docBuilder.parse(this.getClass().getClassLoader()
					.getResourceAsStream("Input.xml"));
			System.out.println("Content of \"Enote\" is:"
					+ doc.getElementsByTagName("Enote").item(0)
							.getTextContent());
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void validateXML() {

		try {
			SchemaFactory schemaFac = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFac.newSchema(this.getClass()
					.getClassLoader().getResource("Input.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(parseXML()));
			System.out.println("Your XML has been validated againt local XSD!");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
