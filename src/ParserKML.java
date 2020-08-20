import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class ParserKML {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(new File(".\\kml_file\\SewersAR.kml"));
		
		NodeList folders =  doc.getElementsByTagName("Folder");
		Node folder = folders.item(0);
		Element folderElem = (Element) folder;
		NodeList placemarks = folderElem.getElementsByTagName("Placemark");
		
		for(int i = 0; i < placemarks.getLength(); i++) {
			Node placemark = placemarks.item(i);
			Element placemarkElem = (Element) placemark;
			
			NodeList lineStrings = placemarkElem.getElementsByTagName("LineString");
			Node lineString = lineStrings.item(0);
			Element lineStringElem = (Element) lineString;
			
			NodeList coordinatesList = lineStringElem.getElementsByTagName("coordinates");
			String coordinates = coordinatesList.item(0).getTextContent();
			System.out.println(coordinates);
		}
	}

}
