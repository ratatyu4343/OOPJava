package electricalappliance;

//Стандартний тип електроприбора, споживає електроенергію щосекунди
public class ConstantlyAppliance extends ElectricalAppliance{
    public ConstantlyAppliance(String name, float power)
    {
        super(name, power);
    }
    //Визначений метод споживання енергії
    @Override
    public float getPower() {
        return power;
    }
}

