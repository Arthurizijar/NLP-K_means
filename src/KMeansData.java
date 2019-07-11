public class KMeansData {
    public double[][] data;
    public int length;
    public int dim;
    public int[] labels;
    public double[][] centers;
    public int[] centerCounts;

    public KMeansData(double[][] data, int length, int dim) {
        this.data = data;
        this.length = length;
        this.dim = dim;
    }
}
