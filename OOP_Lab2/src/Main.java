import org.xml.sax.SAXException;
import tariff.Tariff;
import tariff.TariffCompare;
import xmlreader.XMLParser;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //створюємо об'єкт парсер, вказуємо шлях до xml
        XMLParser parser = new XMLParser("./src/xmlsrc/tariffs.xml");
        //зчитуємо в список тарифів
        try{
            List<Tariff> t = parser.readXML("./src/xmlsrc/tariffs.xsd");
            //вказуємо, за чим і в якій послідовності сортуємо
            String [] p = {"tName", "payroll"};
            //сортуємо
            t.sort(TariffCompare.getComparator(p));
            //виводимо тарифи на екран
            for (var o : t) {
                System.out.println(o);
            }
        } catch (SAXException e) {
            System.out.println("Cannot validate XML by XSD:\n"+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}