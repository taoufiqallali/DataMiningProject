public class DataPoint {

    private double[] features;
    private String label;

    public DataPoint(double[] features, String label) {
        this.features = features;
        this.label = label;
    }

    public double[] getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }
}
