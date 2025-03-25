import java.io.IOException;  // Import necessary class for handling IOExceptions
import java.util.Scanner;    // Import Scanner class for user input

public class Main {
    public static void main(String[] args) throws IOException {  // Main method throws IOException for handling potential IO issues
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object to read user input

        // Prompt the user to input the value for k
        System.out.println("enter k:");
        int k = scanner.nextInt();  // Read the integer value for k

        KNN knn = new KNN(k);  // Instantiate a KNN object with the user-provided k value

        // Load the dataset from the specified path (iris.txt)
        knn.loadDataset("./src/iris.txt");

        // Prompt the user to input values for classification
        System.out.println("Enter values:");

        String[] parameters = {"sepal length","sepal width","petal length","petal width"};
        // Create an array to hold 4 input values (features) for the dataset
        double[] input = new double[4];  // Adjust the array size to match the dataset features
        for (int i = 0; i < 4; i++) {  // Loop to read 4 feature values from user input
            System.out.println("please enter "+parameters[i]);
            input[i] = scanner.nextDouble();  // Read each value and store it in the array
        }

        // Use the KNN classifier to predict the class based on the input values
        String predictedClass = knn.classify(input);

        // Print the predicted class based on the input features
        System.out.println("Predicted Class: " + predictedClass);
    }
}
