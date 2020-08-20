import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserKML {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		List<SewersNode> sewersNodes = new ArrayList<>();
		
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
			
			String placemarkName = placemarkElem.getElementsByTagName("name").item(0).getTextContent();
			
			NodeList lineStrings = placemarkElem.getElementsByTagName("LineString");
			Node lineString = lineStrings.item(0);
			Element lineStringElem = (Element) lineString;
			
			NodeList coordinatesList = lineStringElem.getElementsByTagName("coordinates");
			String coordinates = coordinatesList.item(0).getTextContent();
			
			String[] coordinatesArray = coordinates.split(" ");
			for(String pointCoordinates : coordinatesArray) {
				String[] lonLatHeight = pointCoordinates.split(",");
				int index = sewersNodes.size();
				SewersNode sewersNode = new SewersNode(index, Double.parseDouble(lonLatHeight[0]), Double.parseDouble(lonLatHeight[1]), "#FF0000FF", placemarkName);
				sewersNodes.add(sewersNode);
			}
			
		}
		
		for (SewersNode sewersNode : sewersNodes) {
			System.out.println(sewersNode.createInsert());
		}
	}

}
