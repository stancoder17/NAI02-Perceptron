import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        initLabels();
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
                int label = labels.get(labelName);

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

    public void initLabels() {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void evaluteWithK_NN() {
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
                K_NN_compute(line, 100);
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

    public void K_NN_compute(String test_record, int k) {
        String[] test_record_split = test_record.split(",");

        Map<String, Double> distances = new LinkedHashMap<>();

        File trainFile = new File("perceptron.data");
        try {
            BufferedReader in_train = new BufferedReader(new FileReader(trainFile));
            String line_train;
            int dimensionNumber = test_record_split.length - 1;
            while ((line_train = in_train.readLine()) != null) {
                Double distance = calculateDistance(line_train, test_record, dimensionNumber);
                distances.put(line_train, distance);
            }
            in_train.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        distances = sortAndLimitMap(distances, k);
        testCalculatedOutputs.add(labels.get(extractMostCountedAttribute(distances)));
    }

    private Double calculateDistance(String object1, String object2, int dimensionsNumber) throws IllegalArgumentException {
        String[] object1_allElements = object1.split(",");
        List<Double> object1_dimensions = new ArrayList<>();
        for (int i = 0; i < dimensionsNumber; i++)
            object1_dimensions.add(Double.parseDouble(object1_allElements[i]));

        String[] object2_allElements = object2.split(",");
        List<Double> object2_dimensions = new ArrayList<>();
        for (int i = 0; i < dimensionsNumber; i++) {
            object2_dimensions.add(Double.parseDouble(object2_allElements[i]));
        }

        // Sum of (x1 - x2)^2
        double sum = 0.0;
        for (int i = 0; i < dimensionsNumber; i++)
            sum += Math.pow(object2_dimensions.get(i) - object1_dimensions.get(i), 2);

        return Math.sqrt(sum);
    }

    private Map<String, Double> sortAndLimitMap(Map<String, Double> map, int limit) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, _) -> e1, LinkedHashMap::new
                ));
    }

    private String extractMostCountedAttribute(Map<String, Double> distances) {
        Map<String, Integer> counts = new HashMap<>();

        for (Map.Entry<String, Double> entry : distances.entrySet()) {
            if (counts.containsKey(entry.getKey()))
                counts.put(entry.getKey(), counts.get(entry.getKey()) + 1);
            else
                counts.put(entry.getKey(), 1);
        }

        String[] record_mostCount = Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey().split(",");

        return record_mostCount[record_mostCount.length - 1];
    }
}