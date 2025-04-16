/**
 * Represents a single data point in a dataset for classification
 * Contains both features and the corresponding class label
 */
public class DataPoint {
    // Array to store the feature values of this data point
    private String[] features;

    // The class label associated with this data point
    private String label;

    /**
     * Constructor to create a new DataPoint with specified features and label
     * @param features Array of feature values for this data point
     * @param label The class label for this data point
     */
    public DataPoint(String[] features, String label){
        this.features = features;
        this.label = label;
    }

    /**
     * Returns the class label of this data point
     * @return The label as a String
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the feature values of this data point
     * @return Array of feature values
     */
    public String[] getFeatures() {
        return features;
    }
}