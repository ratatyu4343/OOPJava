package DTW;

public class Point {
    public float x;
    public float y;
    public float z;

    public static float distant(Point p1, Point p2) {
        float dx = p1.x - p2.x;
        float dy = p1.y - p2.y;
        float dz = p1.z - p2.z;
        return (dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        return  x + " " + y + " " + z + "\n";
    }
}
