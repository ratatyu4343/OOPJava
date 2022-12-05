import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        float [][] A = {{5, -1, 0, 0},
                        {2, 4.6f, -1, 0},
                        {0, 2, 3.6f, -0.8f},
                        {0, 0, 3, 4.4f}};
        float [] b = {2f, 3.3f, 2.6f, 7.2f};
        float [] result = MyThreatMatrixSLAU.getX(A, b);
        System.out.println("X"+Arrays.toString(result));
        System.out.println("\n============================\n");
        Thread sT = new Thread(new Runnable() {
            @Override
            public void run() {
                MyServer server = new MyServer();
                ExampleDataClass data2 = server.getData();
                System.out.println(data2.getAB());
            }
        });
        Thread uT = new Thread(new Runnable() {
            @Override
            public void run() {
                ExampleDataClass data = new ExampleDataClass();
                User user = new User();
                user.writeData(data);
            }
        });
        sT.start();
        uT.start();
        try {
            sT.join();
            uT.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}