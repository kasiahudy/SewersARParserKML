import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserKML {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		List<SewersNode> sewersNodes = new ArrayList<>();
		List<SewersPipe> sewersPipes = new ArrayList<>();
		
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
			for(int j = 0; j < coordinatesArray.length; j++) {
				String color = "#FF0000FF";
				String[] lonLatHeight = coordinatesArray[j].split(",");
				if(lonLatHeight.length >= 3) {
					int index = sewersNodes.size();
					SewersNode sewersNode = new SewersNode(index, Double.parseDouble(lonLatHeight[0]), Double.parseDouble(lonLatHeight[1]), color, placemarkName);
					sewersNodes.add(sewersNode);
					if(j > 0) {
						int pipeIndex = sewersPipes.size();
						SewersPipe sewersPipe = new SewersPipe(pipeIndex, index-1, index, color);
						sewersPipes.add(sewersPipe);
					}
				}
			}
			
		}
		
		for (SewersNode sewersNode : sewersNodes) {
			System.out.println(sewersNode.createInsert());
		}
		for (SewersPipe sewersPipe : sewersPipes) {
			System.out.println(sewersPipe.createInsert());
		}
	}

}
