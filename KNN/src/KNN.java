import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Random;

public class KNN {

    private List<DataPoint> dataSet = new ArrayList<>();
    private List<DataPoint> TrainSet = new ArrayList<>();
    private List<DataPoint> TestSet = new ArrayList<>();
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

        Random rand = new Random(67);
        int i;
        for(i=0;i<dataSet.size();i++){

        if(rand.nextInt(10) > 7){TestSet.add(dataSet.get(i));}
        else{TrainSet.add(dataSet.get(i));}
        }
        System.out.println("test size");
        System.out.println(TestSet.size());
        System.out.println("training size");
        System.out.println(TrainSet.size());

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
        neighbors.addAll(TrainSet);

        Map<String, Integer> votes = new HashMap<>();
        for(int i = 0; i < k; i++){
            DataPoint neighbor = neighbors.poll();
            votes.put(neighbor.getLabel(), votes.getOrDefault(neighbor.getLabel(), 0) + 1);
        }
        return Collections.max(votes.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void test(){
        int[][] matrix  =new int[3][3];
        String[] classes= {"Iris-setosa","Iris-versicolor","Iris-virginica"};
        int i,real,pred;

        System.out.println("confusion matrix");
        System.out.println();

        for(i=0;i<TestSet.size();i++){
            real=findIndex(TestSet.get(i).getLabel());
            pred=findIndex(classify(TestSet.get(i).getFeatures()));
            matrix[real][pred]++;

        }


        for (i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(); // new line after each row
        }
        System.out.println();
    }
    public int findIndex(String name){
        switch(name){
            case "Iris-setosa":
                return 0;
            case "Iris-versicolor":
                return 1;
            case "Iris-virginica":
                return 2;
        }
        return 3;

    }
}
