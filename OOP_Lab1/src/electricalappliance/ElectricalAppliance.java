package electricalappliance;

//Клас електроприлад, зберігає назву та потужнисть приладу
//абстрактний через метод отримання поточної потужності
//оскільки в різних приладів він різний
public abstract class ElectricalAppliance{
    //ім'я приладу
    protected final String name;
    //потужність приладу
    protected final float power;
    //флажок, чи включений прилад
    private boolean status;
    public ElectricalAppliance(String name, float power)
    {
        this.name = name;
        this.power = Math.max(power, 0);
    }
    //отримання назви приладу
    public  String getName()
    {
        return name;
    }
    //отримання поточної потужності приладу, абстрактний метод
    public abstract float getPower();
    //отримання максимальної потужності
    public float getPowerAll() {
        return power;
    }
    //увімкнути та вимкнути прилад
    public void On()
    {
        status = true;
    }
    public void Off()
    {
        status = false;
    }
    //отримання статусу прилада (увімкнутий, вимкнутий)
    public boolean getStatus()
    {
        return status;
    }
}
