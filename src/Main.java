import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Perceptron perceptron;
        try {
            perceptron = new Perceptron(getNumberOfDimensions(new File("perceptron.data")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            for (int i = 0; i < 100; i++) {
                BufferedReader reader_train = new BufferedReader(new FileReader("perceptron.data"));
                String line_train;

                while ((line_train = reader_train.readLine()) != null) {
                    String[] line_split = line_train.split(",");
                    String decisionAttribute = line_split[line_split.length - 1];

                    List<Double> attributes = getAttributes(line_split);
                    int decision_correct = decisionAttribute.equals("Iris-versicolor") ? 1 : 0;
                    int decision_computed = perceptron.compute(attributes);

                    // Learn if wrong
                    if (decision_computed != decision_correct) {
                        perceptron.learn(attributes, decision_correct);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            int correctCount = 0;
            int incorrectCount = 0;

            BufferedReader reader_test = new BufferedReader(new FileReader("perceptron.test.data"));
            String line_test;

            while ((line_test = reader_test.readLine()) != null) {
                String[] line_split = line_test.split(",");
                String decisionAttribute = line_split[line_split.length - 1];

                List<Double> line_attributes = getAttributes(line_split);
                int decision_correct = decisionAttribute.equals("Iris-versicolor") ? 1 : 0;
                int decision_computed = perceptron.compute(line_attributes);

                if (decision_computed == decision_correct)
                    correctCount++;
                else
                    incorrectCount++;
            }

            double accuracy = ((double) correctCount / (correctCount + incorrectCount));

            // Display statistics
            System.out.println("Correct count: " + correctCount);
            System.out.println("Incorrect count: " + incorrectCount);
            System.out.printf("Accuracy: %.2f%%\n", accuracy * 100.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Double> getAttributes(String[] line_split) {
        List<Double> attributes = new ArrayList<>();

        for (int i = 0; i < line_split.length - 1; i++) {
            attributes.add(Double.parseDouble(line_split[i]));
        }

        return attributes;
    }

    private static int getNumberOfDimensions(File file) throws IOException {
        return new BufferedReader(new FileReader(file))
                .readLine()
                .split(",")
                .length - 1;
    }
}
