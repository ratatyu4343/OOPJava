package Tests;

import apartment.Apartment;
import electricalappliance.BatteryAppliance;
import electricalappliance.ConstantlyAppliance;
import electricalappliance.EconomyAppliance;
import electricalappliance.ElectricalAppliance;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApplianceTest")
class ElectricalApplianceTest {

    private ElectricalAppliance appliance;

    @BeforeEach
    void setApartment() {
        appliance = new ConstantlyAppliance("name", 123.2f);
    }

    @AfterEach
    void clearApartment() {
        appliance = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "", "12", "\0"})
    void getName(String name) {
        appliance = new ConstantlyAppliance(name, 0);
        assertEquals(name, appliance.getName());
    }

    @Test
    void getPowerAll() {
        assertAll(
                ()->assertEquals(12.1f, new ConstantlyAppliance("", 12.1f).getPowerAll()),
                ()->assertEquals(0, new ConstantlyAppliance("", 0).getPowerAll()),
                ()->assertEquals(0, new ConstantlyAppliance("", -1.1f).getPowerAll())
        );
    }

    //У ВСІХ ПІДКЛАСІВ РІЗНИЙ САМЕ ЦЕЙ МЕТОД
    @Test
    void getPower() {
        //Звичайний прилад
        assertEquals(12.1f, new ConstantlyAppliance("", 12.1f).getPower());
        assertEquals(0, new ConstantlyAppliance("", 0).getPowerAll());
        assertEquals(0, new ConstantlyAppliance("", -1.1f).getPowerAll());
        //Прилад з таймером
        appliance = new EconomyAppliance("Economy", 1.1f, 2);
        for (int i = 0; i < 3; i++)
            appliance.getPower();
        assertEquals(0, appliance.getPower());
        assertEquals(1.1f, appliance.getPower());
        //Прилад з батареєю
        appliance = new BatteryAppliance("", 2.2f, 10);
        assertEquals(0f, appliance.getPower());
        for(int i = 0; i < 10; i++)
            System.out.print(appliance.getPower());
        assertEquals(22f, appliance.getPower());
    }

    @Test
    void on() {
        appliance.On();
        assertTrue(appliance.getStatus());
    }

    @Test
    void off() {
        assertFalse(appliance.getStatus());
        appliance.On();
        appliance.Off();
        assertFalse(appliance.getStatus());
    }

    @Test
    void getStatus() {
        assertFalse(appliance.getStatus());
        appliance.On();
        assertTrue(appliance.getStatus());
    }
}