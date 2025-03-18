import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private List<Double> weights;
    private double threshold; // theta
    private final double alpha;
    private final double beta;
    private final int dimension;
    private final int numberOfIterations;

    public Perceptron(int dimension) {
        this.dimension = dimension;
        this.alpha = 0.01;
        this.beta = 0.01;
        this.threshold = Math.random();
        this.numberOfIterations = 250;
        weights = new ArrayList<>();

        for (int i = 0; i < dimension; i++)
            weights.add(Math.random());
    }

    public Perceptron(int dimension, double alpha, int numberofIterations) {
        this.dimension = dimension;
        this.alpha = alpha;
        this.beta = 0.01;
        this.threshold = Math.random();
        this.numberOfIterations = numberofIterations;
        weights = new ArrayList<>();

        for (int i = 0; i < dimension; i++)
            weights.add(Math.random());
    }

    public int compute(List<Double> inputs) {
        if (inputs.size() != dimension) {
            System.out.println("Inputs size != dimension");
            return -1;
        }

        double sum = 0;
        for (int i = 0; i < dimension; i++)
            sum += inputs.get(i) * weights.get(i);

        double net = sum - threshold;

        if (net >= 0)
            return 1;
        else
            return 0;
    }

    public void learn(List<Double> inputs, int decision) {
        if (inputs.size() != dimension) {
            System.out.println("Inputs size != dimension");
            return;
        }

        List<Double> new_weights = weights;
        int y = compute(inputs);

        for (int i = 0; i < dimension; i++) {
            new_weights.set(i, new_weights.get(i) + alpha * (decision - y) * inputs.get(i));
        }

        weights = new_weights;
        threshold = threshold - beta * (decision - y);
    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "weights=" + weights +
                ", threshold=" + threshold +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", dimension=" + dimension +
                '}';
    }
}
