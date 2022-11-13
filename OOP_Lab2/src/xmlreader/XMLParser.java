package xmlreader;

import tariff.Tariff;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    final private String path;
    public XMLParser(String path) {
        this.path = path;
    }
    public List<Tariff> readXML() {
        List<Tariff> tariffs = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(path));
            Tariff tariff = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if(event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String type = startElement.getName().getLocalPart();
                    if(type.equals("Tariff")) {
                        tariffs = new ArrayList<>();
                    } else
                    if (type.equals("TariffItem")) {

                        var name = startElement.getAttributeByName(new QName("name"));
                        var oname = startElement.getAttributeByName(new QName("oname"));
                        var id = startElement.getAttributeByName(new QName("id"));
                        tariff = new Tariff(id.getValue());
                        tariff.setAtribute("tName", name.getValue());
                        tariff.setAtribute("oName", oname.getValue());
                    } else {
                        event = reader.nextEvent();
                        double data = Double.parseDouble(event.asCharacters().getData());
                        String atribute =
                            switch (type) {
                                case "payroll" ->  "payroll";
                                case "callin" -> "callIn";
                                case "callout" -> "callOut";
                                case "callhome" -> "callHome";
                                case "sms" -> "sms";
                                case "likenum" -> "likeNum";
                                case "tarif12" -> "tarif12";
                                case "tarif60" -> "tarif60";
                                case "startcost" -> "startCost";
                                default -> "";
                            };
                        tariff.setAtribute(atribute, data);
                    }
                }
                if(event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals("TariffItem")) {
                        tariffs.add(tariff);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tariffs;
    }
}
