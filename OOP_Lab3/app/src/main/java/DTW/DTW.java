package DTW;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DTW {
    private List<TimeSerie> timeSeries;

    public DTW() {
        timeSeries = new ArrayList<>();
    }

    public void addTimeSerie(TimeSerie t) {
        timeSeries.add(t);
    }

    public List<TimeSerie> getTimeSeries() {
        return timeSeries;
    }

    public Pair<TimeSerie, Float> findBestTransformation(TimeSerie myTimeSerie) {
        List<Float> costs = new ArrayList<>();
        for(int i = 0; i < timeSeries.size(); i++) {
            TimeSerie timeSerie = timeSeries.get(i);
            float cost = getTransformationWayCost(timeSerie, myTimeSerie);
            costs.add(cost);
        }
        float bestCost = costs.get(0);
        int bestI = 0;
        for (int i = 0; i < costs.size()-1; i++) {
            if(bestCost > costs.get(i + 1)) {
                bestCost = costs.get(i + 1);
                bestI = i + 1;
            }
        }
        return new Pair<TimeSerie, Float>(timeSeries.get(bestI), bestCost);
    }

    public static float getTransformationWayCost(TimeSerie t1, TimeSerie t2) {
        float [][] disMatrix = getDistantMatrix(t1, t2);
        float [][] transMatrix = getTransformMatrix(disMatrix);
        List<Pair<Integer, Integer>> way = getTransformationWay(transMatrix);
        return getWayCost(way, disMatrix);
    }

    private static float [][] getDistantMatrix(TimeSerie t1, TimeSerie t2) {
        int n = t1.getSize();
        int m = t2.getSize();
        float [][] matrix = new float[n][m];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                matrix[i][j] = Point.distant(t1.getPoint(i), t2.getPoint(j));
        }
        return matrix;
    }

    private static float [][] getTransformMatrix(float [][] disMatrix) {
        int n = disMatrix.length;
        int m = disMatrix[0].length;
        float[][] matrix = new float[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                float d = disMatrix[i][j];
                if (i != 0 && j != 0) {
                    float minimal;
                    minimal = Math.min(matrix[i - 1][j], matrix[i - 1][j - 1]);
                    minimal = Math.min(minimal, matrix[i][j - 1]);
                    d += minimal;
                }
                matrix[i][j] = d;
            }
        }
        return matrix;
    }

    private static List<Pair<Integer, Integer>> getTransformationWay(float [][] traMatrix) {
        List<Pair<Integer, Integer>> way = new ArrayList<>();
        int n = traMatrix.length;
        int m = traMatrix[0].length;
        way.add(new Pair<>(0, 0));
        for (int i = 0, j = 0; (i != n-1) || (j != m-1);){
            if (i == n-1) j++;
            else if (j == m-1) i++;
            else {
                float item1 = traMatrix[i+1][j+1];
                float item2 = traMatrix[i][j+1];
                float item3 = traMatrix[i+1][j];
                float bestItem = Math.min(item1, Math.min(item2, item3));
                if(bestItem == item1) {
                    i++;
                    j++;
                } else if (bestItem == item2)
                    j++;
                else
                    i++;
            }
            way.add(new Pair<>(i, j));
        }
        way.add(new Pair<>(n-1, m-1));
        return way;
    }

    private static float getWayCost(List<Pair<Integer, Integer>> way, float [][] disMatrix) {
        float cost = 0;
        int n = disMatrix.length;
        int m = disMatrix[0].length;
        for (Pair<Integer, Integer> point : way) {
            cost += disMatrix[point.first][point.second];
        }
        return cost / way.size();
    }
}

