package Tests;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import tariff.Tariff;
import tariff.TariffCompare;
import xmlreader.XMLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DisplayName("MyTests")
public class Tests {
    private static final String xsdPath = "./src/xmlsrc/tariffs.xsd";
    @Test
    @DisplayName("VALIDATED_TEST")
    void validateXML1() {
        XMLParser parser = new XMLParser("./src/xmlsrc/tariffs.xml");
        List<Tariff> tariffs;
        try {
            tariffs = parser.readXML(xsdPath);
            Assert.assertEquals(tariffs.size(), 3);
        } catch (Exception e) {
            Assertions.fail("Validation failed!\n"+e.getMessage());
        }

    }
    @Test
    @DisplayName("NOT_VALIDATED_TEST")
    void validateXML2() {
        XMLParser parser = new XMLParser("./src/Tests/tariffsTest.xml");
        List<Tariff> tariffs;
        try {
            tariffs = parser.readXML(xsdPath);
            Assertions.fail("Validated but couldn't");
        } catch (Exception e) {
            Assert.assertTrue(Objects.equals(e.getMessage(),
                 "cvc-minInclusive-valid: " +
                    "Value '-31.5111' is " +
                    "not facet-valid with respect to " +
                    "minInclusive '0.0E1' for type 'MyFLOAT'."));
        }
    }
    @Test
    @DisplayName("CORRECT_PARSING")
    void parseXMLCorrect() {
        XMLParser parser = new XMLParser("./src/xmlsrc/tariffs.xml");
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = parser.readXML(xsdPath);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        Assertions.assertEquals(tariffs.get(0).getAtribute("payroll"), 31.5111);
        Assertions.assertEquals(tariffs.get(0).gettName(), "B");
        Assertions.assertEquals(tariffs.get(0).getoName(), "Life");
        Assertions.assertEquals(tariffs.get(0).getAtribute("tarif12"), 1.5);
        Assertions.assertEquals(tariffs.get(0).getAtribute("tarif60"), 11.5);
    }
    @Test
    @DisplayName("CORRECT_PARSING")
    void SortResults() {
        XMLParser parser = new XMLParser("./src/xmlsrc/tariffs.xml");
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = parser.readXML(xsdPath);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
        String [] param = {"callIn", "payroll"};
        tariffs.sort(TariffCompare.getComparator(param));
        String [] shouldBe = {"B", "A", "C"};
        String [] result = {tariffs.get(0).gettName(),
                            tariffs.get(1).gettName(),
                            tariffs.get(2).gettName()};
        Assertions.assertArrayEquals(shouldBe, result);
    }
}
