import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParserKML {
	
	static List<SewersNode> sewersNodes = new ArrayList<>();
	static List<SewersPipe> sewersPipes = new ArrayList<>();
	
	static String randomColor() {
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		return String.format("#ff%02x%02x%02x", r, g, b);  
	}
	
	static void parseKMLFile() throws SAXException, IOException, ParserConfigurationException {
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
			
			String color = randomColor();
			
			NodeList lineStrings = placemarkElem.getElementsByTagName("LineString");
			Node lineString = lineStrings.item(0);
			Element lineStringElem = (Element) lineString;
			
			NodeList coordinatesList = lineStringElem.getElementsByTagName("coordinates");
			String coordinates = coordinatesList.item(0).getTextContent();
			
			String[] coordinatesArray = coordinates.split(" ");
			for(int j = 0; j < coordinatesArray.length; j++) {
				
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
	}
	
	static void saveInsertsFile() {
		try {
			FileWriter myWriter = new FileWriter(".\\results\\sewersInserts.sql");
			for (SewersNode sewersNode : sewersNodes) {
				System.out.println(sewersNode.createInsert());
				myWriter.write(sewersNode.createInsert() + "\n");
			}
			for (SewersPipe sewersPipe : sewersPipes) {
				System.out.println(sewersPipe.createInsert());
				myWriter.write(sewersPipe.createInsert() + "\n");
			}
		    myWriter.close();
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
		parseKMLFile();
		saveInsertsFile();
	}
	
	

}
