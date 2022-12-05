
public class MyReentrantLock {
    private boolean blocked = false;

    public synchronized void lock() {
        try {
            while (blocked) wait();
            blocked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean tryLock() {
        if (!blocked) {
            blocked = true;
            return true;
        } else
            return false;
    }

    public synchronized void unlock() {
        blocked = false;
        notifyAll();
    }
}
