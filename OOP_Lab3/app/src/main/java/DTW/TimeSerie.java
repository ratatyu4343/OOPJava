package DTW;

import android.os.Environment;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimeSerie {
    private String name;
    private List<Point> serie;

    public TimeSerie(String name) {
        this.name = name;
        serie = new ArrayList<>();
    }

    public TimeSerie(String name, String fileName, String path) {
        this.name = name;
        serie = new ArrayList<>();
        try {
            File file = new File(path, fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                Scanner scanner = new Scanner(line);
                Point p = new Point();
                p.x = scanner.nextFloat();
                p.y = scanner.nextFloat();
                p.z = scanner.nextFloat();
                addPoint(p);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Point getPoint(int timeIndex) {
        return serie.get(timeIndex);
    }

    public void addPoint(Point p) {
        serie.add(p);
    }

    public int getSize() {
        return serie.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name + serie.toString();
    }
}
