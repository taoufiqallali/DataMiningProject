import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * Implementation of a Naive Bayesian classifier for categorical data
 * Used for making predictions based on probabilistic calculations
 */
public class Bayesian {
    // Dataset containing all training examples
    private List<DataPoint> dataset = new ArrayList<>();

    // List of all possible class labels
    private List<String> label_list = new ArrayList<>();

    // 2D list of features and their possible values
    // Each inner list starts with the feature name followed by all possible values
    private List<List<String>> feature_list = new ArrayList<>();

    // Path to the input data file
    private String dataPath;

    // Maps to store calculated probabilities:
    // prCl: P(C) - Prior probability of each class
    // prXCl: P(X|C) - Conditional probability of feature value given class
    // prInput: P(C|X) - Posterior probability for each class given input
    private Map<String, Float> prCl, prXCl, prInput;

    /**
     * Constructor that initializes the classifier with a data file
     * @param dataPath Path to the CSV data file
     */
    public Bayesian(String dataPath) {
        this.dataPath = dataPath;
        this.prCl = new HashMap<>();
        this.prXCl = new HashMap<>();
        this.prInput = new HashMap<>();
        this.load_DataSet();
        this.calculate_Pr();
    }

    /**
     * Loads and parses the dataset from the specified file
     * First line is expected to be a header with feature names
     * Last column in each row is expected to be the class label
     */
    private void load_DataSet() {
        // open the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataPath));

            String line;

            // Read header line and initialize feature lists
            line = br.readLine();
            String[] header = line.split(",");
            for (int i = 0; i < header.length - 1; i++) {
                feature_list.add(new ArrayList<>(Arrays.asList(header[i])));
            }

            // Process each data line
            while ((line = br.readLine()) != null) {
                // Split the line into features and label
                String[] parts = line.split(",");
                String[] features = new String[parts.length - 1];

                // Extract feature values and add to feature_list if not already present
                for (int i = 0; i < parts.length - 1; i++) {
                    features[i] = parts[i];
                    if (!feature_list.get(i).contains(features[i])) {
                        feature_list.get(i).add(features[i]);
                    }
                }

                // Extract the class label and add to label_list if not already present
                String label = parts[parts.length - 1];
                if (!label_list.contains(label)) {
                    label_list.add(label);
                }

                // Add the data point to the dataset
                dataset.add(new DataPoint(features, label));
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found at path: " + dataPath);
        } catch (IOException e) {
            System.out.println("File not found at path: " + dataPath);
        }
    }

    /**
     * Calculates all the probabilities needed for the Naive Bayes algorithm
     * This includes prior probabilities and conditional probabilities
     */
    private void calculate_Pr() {
        // Calculate prior probabilities P(C) for each class
        for (String l : label_list) {
            prCl.put(l, calculate_number(l) / dataset.size());
        }

        // Calculate conditional probabilities P(X|C) for each feature value given each class
        for (String l : label_list) {
            int j = 0;
            for (List el : feature_list) {
                for (int i = 1; i < el.size(); i++) {
                    // Key format: "class,feature_name,feature_value"
                    prXCl.put((l + "," + el.get(0) + "," + el.get(i)),
                            calculate_number(l, j, el.get(i).toString()) / calculate_number(l));
                }
                j++;
            }
            j = 0;
        }
    }

    /**
     * Counts how many data points have the given class label
     * @param label The class label to count
     * @return Number of data points with the specified label
     */
    private Float calculate_number(String label) {
        Float n = 0f;
        for (DataPoint point : dataset) {
            if (point.getLabel().equals(label)) {
                n++;
            }
        }
        return n;
    }

    /**
     * Counts how many data points have the given class label AND
     * the specified feature value at the given feature index
     * @param label The class label to match
     * @param feature_index Index of the feature to check
     * @param value Value of the feature to match
     * @return Number of matching data points
     */
    private Float calculate_number(String label, int feature_index, String value) {
        Float n = 0f;
        for (DataPoint point : dataset) {
            if (point.getLabel().equals(label) && point.getFeatures()[feature_index].equals(value)) {
                n++;
            }
        }
        return n;
    }

    /**
     * Makes a prediction based on the input feature values
     * Uses the Naive Bayes formula to calculate the most likely class
     * @param input Array of feature values in the same order as the feature_list
     * @return The predicted class label
     */
    public String predict(String[] input) {
        String maxC = "";
        Float maxV = 0f;
        Float temp;

        // For each possible class label
        for (String l : label_list) {
            // Start with the prior probability P(C)
            temp = prCl.get(l);

            // Multiply by each conditional probability P(X_i|C)
            for (int i = 0; i < input.length; i++) {
                temp = temp * prXCl.get(l + "," + (feature_list.get(i).getFirst()) + "," + input[i]);
            }

            // Store this result and check if it's the highest so far
            prInput.put(l, temp);
            if (temp > maxV) {
                maxV = temp;
                maxC = l;
            }
        }

        // Return the class with the highest probability
        return maxC;
    }

    /**
     * Verifies if the input feature values are valid
     * (i.e., they exist in the training set)
     * @param input Array of feature values to verify
     * @return true if all input values are valid, false otherwise
     */
    public boolean verifyInput(String[] input){
        Boolean response=true;
        for(int i=0 ; i<input.length;i++){
            if(!feature_list.get(i).contains(input[i])){
                response=false;
            }
        }
        return response;
    }

    /**
     * Getter for the feature_list attribute
     * @return List of features and their possible values
     */
    public List<List<String>> getFeature_list() {
        return feature_list;
    }
}