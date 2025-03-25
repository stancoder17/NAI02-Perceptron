import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Teacher {
    private Perceptron perceptron;
    private File trainFile;
    private File testFile;

    public Teacher(Perceptron perceptron, File trainFile, File testFile) {
        this.perceptron = perceptron;
        this.trainFile = trainFile;
        this.testFile = testFile;
    }

    public void teach(int numberOfIterations, String one) throws IOException {
        /*for (int i = 0; i < numberOfIterations; i++) {
            BufferedReader reader_train = new BufferedReader(new FileReader(trainFile));
            String line_train;

            while ((line_train = reader_train.readLine()) != null) {
                String[] line_split = line_train.split(",");
                String decisionAttribute = line_split[line_split.length - 1];

                List<Double> attributes = Main.getAttributes(Arrays.copyOfRange(line_split, 0, line_split.length - 1));
                int decision_correct = decisionAttribute.equals(one) ? 1 : 0;
                int decision_computed = perceptron.compute(attributes);

                // Learn if wrong
                if (decision_computed != decision_correct) {
                    perceptron.learn(attributes, decision_correct);
                }
            }
        }*/
    }


}
