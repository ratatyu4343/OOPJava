package electricalappliance;

//Електроприлад з батареєю, використовує 1 раз за n разів
//потім n разів не використовує і так по кругу
public class BatteryAppliance extends ElectricalAppliance{
    public BatteryAppliance(String name, float power, int timeOfwork){
        super(name, power);
        //час, на який вистачає енергії
        this.timeOfwork = timeOfwork;
    }
    //Перевизначення методу отримання витраченої енргії
    @Override
    public float getPower() {
        if(timeOfworkNow >= 0) {
            timeOfworkNow--;
            return 0;
        }
        else{
            timeOfworkNow = timeOfwork;
            return timeOfwork*power;
        }
    }
    //час, на який вистачає енергії
    private final int timeOfwork;
    //час, що залишився для використання енегрії
    private int timeOfworkNow = 0;
}
