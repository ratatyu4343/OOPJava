import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class MyThreatMatrixSLAU {
    static float[] getX(float [][] A, float [] b) {
        float [] X = new float[A.length];
        float [] y = new float[A.length];
        float [] alpha = new float[A.length-1];
        float [] betta = new float[A.length];
        Semaphore yToa = new Semaphore(1);
        Semaphore aToy = new Semaphore(1);
        Semaphore yTob = new Semaphore(1);

        Thread alphaCalc = new Thread(new CallculateAlfa(alpha, A, y, yToa, aToy));
        Thread bettaCalc = new Thread(new CallculateBetta(betta, A, b, y, yTob));

        try {
            yToa.acquire();
            aToy.acquire();
            yTob.acquire();
        } catch (Exception e) {e.printStackTrace();}

        alphaCalc.start();
        bettaCalc.start();

        y[0] = A[0][0];
        yToa.release();
        yTob.release();
        for (int i = 1; i < A.length; i++) {
            try {aToy.acquire();} catch (Exception e) {e.printStackTrace();}
            y[i] = A[i][i] + alpha[i-1] * A[i][i-1];
            yToa.release();
            yTob.release();
        }
        try {
            alphaCalc.join();
            bettaCalc.join();
        } catch (Exception e) {e.printStackTrace();}

        X[X.length-1] = betta[betta.length-1];
        for (int i = X.length-2; i >= 0; i--) {
            X[i] = alpha[i] * X[i+1] + betta[i];
        }
        System.out.println("y" + Arrays.toString(y));
        System.out.println("a" + Arrays.toString(alpha));
        System.out.println("b" + Arrays.toString(betta));
        return X;
    }
}

class CallculateAlfa implements Runnable {
    float [] alp;
    float [][] A;
    float [] y;
    Semaphore s1;
    Semaphore s2;
    public  CallculateAlfa (float [] alp, float [][] A, float [] y, Semaphore s1, Semaphore s2) {
        this.alp = alp;
        this.A = A;
        this.y = y;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void run() {
        try {s1.acquire();} catch (Exception e) {e.printStackTrace();}
        alp[0] = -A[0][1] / y[0];
        s2.release();
        for (int i = 1; i < A.length-1; i++) {
            try {s1.acquire();} catch (Exception e) {e.printStackTrace();}
            alp[i] = -A[i][i+1] / y[i];
            s2.release();
        }
    }
}

class CallculateBetta implements Runnable {
    float [] bet;
    float [][] A;
    float [] b;
    float [] y;
    Semaphore s;
    public CallculateBetta (float [] bet, float [][] A, float [] b, float [] y, Semaphore s) {
        this.bet = bet;
        this.A = A;
        this.y = y;
        this.b = b;
        this.s = s;
    }

    @Override
    public void run() {
        try {s.acquire();} catch (Exception e) {e.printStackTrace();}
        bet[0] = b[0] / y[0];

        for (int i = 1; i < A.length; i++) {
            try {s.acquire();} catch (Exception e) {e.printStackTrace();}
            bet[i] = (b[i] - A[i][i-1] * bet[i-1]) / y[i];
        }
    }
}