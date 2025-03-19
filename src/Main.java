import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("enter k:");
        int k = scanner.nextInt();
        KNN knn = new KNN(k);
        knn.loadDataset("C:\\Users\\Taoufiq\\Desktop\\m2i\\JEE\\TDClassroom\\dataMining\\src\\iris.txt");

        System.out.println("Enter values:");
            double[] input = new double[4]; // Adjust for dataset features
        for (int i = 0; i < 4; i++) {
                input[i] = scanner.nextDouble();
            }

            String predictedClass = knn.classify(input);
        System.out.println("Predicted Class: " + predictedClass);
        }
}