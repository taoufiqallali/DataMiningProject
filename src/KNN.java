import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KNN {

    private List<DataPoint> dataSet = new ArrayList<>();
    private int k;

    public KNN (int k ){
        this.k = k;
    }

    public void loadDataset(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split(",");
            double[] features = new double[parts.length - 1];
            for(int i = 0; i < parts.length - 1; i++){
                features[i] = Double.parseDouble(parts[i]);
            }
            String label = parts[parts.length - 1];
            dataSet.add(new DataPoint(features, label));
        }
        br.close();
    }

    private double euclideanDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    public String classify(double[] input){
        PriorityQueue<DataPoint> neighbors = new PriorityQueue<>(Comparator.comparingDouble(db -> euclideanDistance(db.getFeatures(), input)));
        neighbors.addAll(dataSet);

        Map<String, Integer> votes = new HashMap<>();
        for(int i = 0; i < k; i++){
            DataPoint neighbor = neighbors.poll();
            votes.put(neighbor.getLabel(), votes.getOrDefault(neighbor.getLabel(), 0) + 1);
        }
        return Collections.max(votes.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
