import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Initialize a 2D list to store feature categories and their possible values
        List<List<String>> feature_list ;

        // Create a scanner object to read user input from console
        Scanner scan = new Scanner(System.in);

        // Create a new Bayesian classifier object and load data from a file
        // The file is expected to be at "./src/play_tennis.txt"
        Bayesian bayes = new Bayesian("./src/play_tennis.txt");

        // Get the feature list from the Bayesian object
        // This contains all the features and their possible values
        feature_list = bayes.getFeature_list();

        // Create an array to store user input for prediction
        // Size is determined by the number of features
        String[] input = new String[feature_list.size()];

        // Counter for tracking which feature we're collecting input for
        int j = 0;

        // Loop through each feature in the feature list
        for (List f : feature_list) {
            // Display the name of the current feature (stored as first element in the list)
            System.out.println("valeur de " + f.getFirst() + " :");

            // Display all possible values for this feature with their index numbers
            for (int i = 1; i < f.size(); i++) {
                System.out.println(i + "- " + f.get(i));
            }

            // Read user's choice (as an integer) and get the corresponding feature value
            // Store this value in our input array
            input[j] = f.get(scan.nextInt()).toString();
            j++;
        }

        // Display the collected input values for confirmation
        System.out.println("les valeurs entrees sont : ");
        for (String i : input) {
            System.out.print(i+" , ");
        }
        System.out.println();
        // Use the Bayesian classifier to make a prediction based on the input values
        // and display the result
        System.out.println("la prediction est : " + bayes.predict(input));
    }
}