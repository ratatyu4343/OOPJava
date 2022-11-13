package electricalappliance;

//Електроприлад, що споживає електроенергію по таймеру
//timer-разів спожив, стільки ж не споживає
public class EconomyAppliance extends ElectricalAppliance{
    //таймер
    private final int timer;
    //поточний стан таймера
    private int usedtimer;
    public EconomyAppliance(String name, float power, int timer) {
        super(name, power);
        this.timer = Math.max(timer, 0);
        usedtimer = timer;
    }
    //Визначення методу отримання поточного споживання електроенергії
    @Override
    public float getPower(){
        usedtimer--;
        if(usedtimer >= 0) {
            return power;
        } else if (usedtimer == -timer) {
            usedtimer = timer;
        }
        return 0;
    }
}
