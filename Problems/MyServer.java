import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

class MyServer {
    private ServerSocket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public MyServer() {
        try {
            socket = new ServerSocket(8888);
            Socket client = socket.accept();
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
        } catch (Exception r) {
            r.printStackTrace();
        }
    }
    public ExampleDataClass getData() {
        try {
            return  (ExampleDataClass) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

class User {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public User () {
        try {
            socket = new Socket("127.0.0.1", 8888);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeData(ExampleDataClass e) {
        try {
            outputStream.writeObject(e);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}

class ExampleDataClass implements Serializable {
    private int a = 10;
    private float b = 22.2f;

    public float getAB() {
        return a+b;
    }
}