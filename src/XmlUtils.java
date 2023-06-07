import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class XmlUtils {

    private static XMLInputFactory xmlIf = null;
    private static XMLStreamReader xmlR = null;
    private static XMLOutputFactory xmlOf = null;
    private static XMLStreamWriter xmlW = null;

    private static void initializeXMLReader(String filename) {
        try {
            xmlIf = XMLInputFactory.newInstance();
            xmlR = xmlIf.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Error in initializing XML stream reader:\n" + e.getMessage());
        }
    }

    public static ArrayList<Node> readerMaps() {

        ArrayList<Node> map = new ArrayList<>();
        String filename = "Mappe.xml";
        initializeXMLReader(filename);

        try {
            while (xmlR.hasNext()) {
                switch (xmlR.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        if (xmlR.getLocalName().equals("NODO")) {
                            map.add(new Node());
                            map.get(map.size() - 1).setId(Integer.parseInt(xmlR.getAttributeValue(0)));
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if(xmlR.getText().trim().length() > 0) {
                            if(xmlR.getText().equals("INIZIO") || xmlR.getText().equals("INTERMEDIO") || xmlR.getText().equals("FINE")) {
                                map.get(map.size() - 1).setType(xmlR.getText());
                            } else {
                                map.get(map.size() - 1).addLink(Integer.parseInt(xmlR.getText()));
                            }
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if(xmlR.getLocalName().equals("MAPPA")) {
                            return map;
                        }
                    }
                }
                xmlR.next();
            }
        } catch (XMLStreamException e) {
            System.out.println("Reading error: " + e.getMessage());
        }

        return map;
    }

    public static ArrayList<Person> readerPeople() {

        String filename = "PersoneID.xml";
        initializeXMLReader(filename);
        ArrayList<Person> people = new ArrayList<>();

        try {
            ArrayList<String> personData = new ArrayList<>();
            while (xmlR.hasNext()) {
                if (xmlR.getEventType() == XMLStreamConstants.CHARACTERS && xmlR.getText().trim().length() > 0) {
                    personData.add(xmlR.getText());
                }
                if (personData.size() == 7) {
                    people.add(new Person(personData));
                    personData.clear();
                }
                xmlR.next();
            }
        } catch (XMLStreamException e) {
            System.out.println("Reading error: " + e.getMessage());
        }

        return people;
    }

    public static HashMap<String, String> readCities() {

        HashMap<String, String> cities = new HashMap<>();

        String filename = "Comuni.xml";
        initializeXMLReader(filename);

        try {
            while (xmlR.hasNext()) {
                if (xmlR.getEventType() == XMLStreamConstants.CHARACTERS && xmlR.getText().trim().length() > 0) {
                    String cityName = xmlR.getText();
                    do {
                        xmlR.next();
                        if (xmlR.getEventType() == XMLStreamConstants.CHARACTERS && xmlR.getText().trim().length() > 0) {
                            cities.put(cityName, xmlR.getText());
                            break;
                        }
                    } while (xmlR.hasNext());
                }
                xmlR.next();
            }
        } catch (XMLStreamException | NoSuchElementException e) {
            System.out.println("Reading error: " + e.getMessage());
        }

        return cities;
    }
}
