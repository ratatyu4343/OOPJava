public class MyCyclicBarrier {
    private final int count;
    private int count_now = 0;
    private final Runnable runnable;
    public MyCyclicBarrier(int count, Runnable runnable) {
        this.count = count;
        this.runnable = runnable;
    }
    public synchronized void await() {
        count_now++;
        try {
            if (count_now == count) {
                count_now = 0;
                Thread thread = new Thread(runnable);
                thread.start();
                thread.join();
                notifyAll();
            } else {
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
