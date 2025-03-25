import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Teacher {
    private final String trainFileName;
    private final ArrayList<List<Double>> trainingInputs;
    private final ArrayList<Integer> trainingOutputs;
    private final Map<String, Integer> labels = new HashMap<>();

    public Teacher(String trainFileName) {
        this.trainFileName = trainFileName;
        this.trainingInputs = new ArrayList<>();
        this.trainingOutputs = new ArrayList<>();
    }

    public void train(Perceptron perceptron, int numberOfIterations) {
        DataLoader dataLoader = new DataLoader(trainFileName);
        dataLoader.loadData(trainingInputs, trainingOutputs, labels);

        for (int i = 0; i < numberOfIterations; i++) {
            for (int j = 0; j < trainingInputs.size(); j++) {
                perceptron.learn(trainingInputs.get(j), trainingOutputs.get(j));
            }
        }
    }

    public String getFirstLabelName() {
        try (BufferedReader in = new BufferedReader(new FileReader(trainFileName))) {
            String[] tokens = in.readLine().split(",");
            return tokens[tokens.length - 1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
