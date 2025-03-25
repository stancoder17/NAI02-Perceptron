import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator {
    private final String testFileName;
    private final ArrayList<Integer> testCalculatedOutputs;
    private final ArrayList<Integer> testCorrectOutputs;
    private final Map<String, Integer> labels;
    private final Teacher teacher;
    private int TP = 0; // true positives
    private int FP = 0; // false positives
    private int TN = 0; // true negatives
    private int FN = 0; // false negative

    public Evaluator(String testFileName, Teacher teacher) {
        this.testFileName = testFileName;
        this.testCorrectOutputs = new ArrayList<>();
        this.testCalculatedOutputs = new ArrayList<>();
        this.labels = new HashMap<>();
        this.teacher = teacher;
    }

    // Code needs to be tidied up
    public void evaluate(Perceptron perceptron) {
        try (BufferedReader in = new BufferedReader(new FileReader(testFileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.split(",");
                int n = tokens.length;
                List<Double> attributes = new ArrayList<>();
                for (int j = 0; j < n - 1; j++) {
                    attributes.add(Double.parseDouble(tokens[j].trim()));
                }

                String labelName = tokens[n - 1];
                int label;
                if (labelName.equals(teacher.getFirstLabelName())) {
                    label = 1;
                } else {
                    label = labels.getOrDefault(labelName, 0);
                }
                labels.put(labelName, label);

                testCorrectOutputs.add(label);
                testCalculatedOutputs.add(perceptron.compute(attributes));
            }

            for (int i = 0; i < testCalculatedOutputs.size(); i++) {
                if (testCalculatedOutputs.get(i) == 1 && testCorrectOutputs.get(i) == 1)
                    TP++;
                else if (testCalculatedOutputs.get(i) == 1 && testCorrectOutputs.get(i) == 0)
                    FP++;
                else if (testCalculatedOutputs.get(i) == 0 && testCorrectOutputs.get(i) == 0)
                    TN++;
                else if (testCalculatedOutputs.get(i) == 0 && testCorrectOutputs.get(i) == 1)
                    FN++;
            }

            System.out.println("+--------------------------------------+");
            System.out.println("| Actual \\ Pred | Positive | Negative |");
            System.out.println("+--------------------------------------+");
            System.out.printf("| Positive      |    %s    |    %s    |\n", TP, FN);
            System.out.println("+--------------------------------------+");
            System.out.printf("| Negative      |    %s    |    %s    |\n", FP, TN);
            System.out.println("+--------------------------------------+");

            System.out.printf("Accuracy: %.2f%n", getAccuracy());
            System.out.printf("Precision: %.2f%n", getPrecision());
            System.out.printf("Recall: %.2f%n", getRecall());
            System.out.printf("F-Measure: %.2f%n", getFMeasure());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getAccuracy() {
        return (double) (TP + TN) / (TP + TN + FP + FN);
    }

    public double getPrecision() {
        return (double) TP / (TP + FP);
    }

    public double getRecall() {
        return (double) TP / (TP + FN);
    }

    public double getFMeasure() {
        double P = getPrecision();
        double R = getRecall();
        return (2 * P * R) / (P + R);
    }
}
