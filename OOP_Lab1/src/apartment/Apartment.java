package apartment;

import electricalappliance.BatteryAppliance;
import electricalappliance.ConstantlyAppliance;
import electricalappliance.EconomyAppliance;
import electricalappliance.ElectricalAppliance;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


//квартира, тут зберігаються прилади, електролічильник
//виконується основна логіка по додаванню, видаленню, сортуванню
public class Apartment {
    //кількість розеток
    private int rosette_count;
    //прилади, що використовуються зараз
    private List<ElectricalAppliance> appliancesUsingNow;
    //усі прилади
    private List<ElectricalAppliance> allAppliances;
    //електролічильник
    private ElectricMeter electricMeter;
    public Apartment() {
        this(0);
    }
    public Apartment(int rosette_count)
    {
        //count of rosettes
        this.rosette_count = Math.max(rosette_count, 0);
        //add new lists
        allAppliances = new ArrayList<>();
        appliancesUsingNow = new ArrayList<>();
        //add new ElectricMetter
        electricMeter = new ElectricMeter(appliancesUsingNow);
        //run counting in another thread
        var threadElectric = new Thread(electricMeter);
        threadElectric.start();
    }
    //додавання пристрою
    public void addAppliance(ElectricalAppliance appliance)
    {
        if(appliance == null)
            return;
        allAppliances.add(appliance);
    }
    //видалення пристрою
    public void deleteAppliance(ElectricalAppliance appliance)
    {
        allAppliances.remove(appliance);
        appliancesUsingNow.remove(appliance);
    }
    //знаходження пристрою за назвою та вольтажем
    public ElectricalAppliance getAppliance(String name, float power)
    {
        for (int i = 0; i < allAppliances.size(); i++)
        {
            if(allAppliances.get(i).getName().equals(name))
            {
                if(allAppliances.get(i).getPowerAll() == power)
                    return allAppliances.get(i);
            }
        }
        return null;
    }
    //під'єднання пристрою до розетки
    public boolean connect(ElectricalAppliance appliance)
    {
        if(appliancesUsingNow.size() == rosette_count || appliance == null)
            return false;
        appliancesUsingNow.add(appliance);
        if(!allAppliances.contains(appliance)){
            allAppliances.add(appliance);
        }
        appliance.On();
        return true;
    }
    //від'єднання пристрою від розетки
    public void disconnect(ElectricalAppliance appliance)
    {
        if(appliance == null)
            return;
        appliancesUsingNow.remove(appliance);
        appliance.Off();
    }
    //отримати показники лічильника
    public float[] getCounterReadings(){
        return electricMeter.getPower();
    }
    //сортування приборів
    public List<ElectricalAppliance> sortAppliances() {
        allAppliances.sort(new Comparator<ElectricalAppliance>() {
            @Override
            public int compare(ElectricalAppliance o1, ElectricalAppliance o2) {
                return (int)(o1.getPowerAll()-o2.getPowerAll());
            }
        });
        return allAppliances;
    }
    //довання, видалення розеток
    public void addRossete(int n)
    {
        rosette_count += n;
    }
    public void deleteRoseete(int n)
    {
        rosette_count = Math.max(0, rosette_count-n);
    }
    //зупинка потоку лічильника
    public void stopSimulation() {
        electricMeter.stopIt();
    }
    //перевірка чи увімкнутий прибор
    public boolean isWork(ElectricalAppliance appliance)
    {
        return appliance.getStatus();
    }
    //ініціалізація через файл
    public boolean getInitialization(String path) {
        Path p = Paths.get(path);
        List<String> initialization;
        try {
            initialization = Files.readAllLines(p);
        }catch (Exception e){return false;}
        rosette_count = Integer.parseInt(initialization.get(0));
        for (int i = 1; i < initialization.size();) {
            int isConnected = Integer.parseInt(initialization.get(i+1));
            String name = initialization.get(i+2);
            float power = Float.parseFloat(initialization.get(i+3));
            int timerOrbattery;
            ElectricalAppliance appliance;
            switch (initialization.get(i)) {
                case "timer" :
                    timerOrbattery = Integer.parseInt(initialization.get(i+4));
                    appliance = new EconomyAppliance(name, power, timerOrbattery);
                    i += 5;
                    break;
                case "battery" :
                    timerOrbattery  = Integer.parseInt(initialization.get(i+4));
                    appliance = new BatteryAppliance(name, power, timerOrbattery);
                    i += 5;
                    break;
                case "simpl" :
                    appliance = new ConstantlyAppliance(name, power);
                    i += 4;
                    break;
                default:
                    appliance = null;
            }
            addAppliance(appliance);
            if(isConnected == 1) connect(appliance);
        }
        return true;
    }
}