package apartment;

import electricalappliance.ElectricalAppliance;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//електролічильник підраховує енергію, яку використовують
//прилади, передані йому у списку, щосекунди
class ElectricMeter implements Runnable {
    //список приладів
    private List<ElectricalAppliance> appliances;
    //показники, що були при попередньому запиті
    private float lastPower = 0;
    //показники зараз
    private float nowPower = 0;
    //локи на читання, запис
    private final Lock readLock;
    private final Lock writeLock;
    //перевірка, чи лічильник потрібно зупинити
    private boolean isStoped = false;
    public ElectricMeter(List<ElectricalAppliance> appliancesUsingNow)
    {
        //передаємо список підключених приладів
        this.appliances = appliancesUsingNow;
        //додаємо локи
        ReadWriteLock r = new ReentrantReadWriteLock();
        readLock = r.readLock();
        writeLock = r.writeLock();
    }
    //отримати показники лічильника
    //повертає масив з 2 елементів
    //1-приріст з минулого запиту, 2-самі показники
    public float[] getPower()
    {
        readLock.lock();
        float[] arr = {nowPower-lastPower, nowPower};
        lastPower = nowPower;
        readLock.unlock();
        return arr;
    }
    //зупинка лічильника
    public  void stopIt()
    {
        isStoped = true;
    }
    //підрахунок показників кожної секунди
    @Override
    public void run() {
        try {
            while (!isStoped) {
                writeLock.lock();
                for (int i = 0; i < appliances.size(); i++) {
                    nowPower += appliances.get(i).getPower();
                }
                writeLock.unlock();
                Thread.sleep(1000);
            }
        }catch (Exception e){
            Thread.currentThread().interrupt();
        }
    }
}
