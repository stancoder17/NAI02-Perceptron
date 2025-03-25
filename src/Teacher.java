import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Teacher {
    private ArrayList<List<Double>> trainingInputs;
    private ArrayList<Integer> trainingOutputs;
    private final String trainFileName;
    private final Map<String, Integer> labels;

    public Teacher(Perceptron perceptron, String trainFileName) {
        this.trainFileName = trainFileName;
        this.labels = new HashMap<>();
    }

    public void loadData(String fileName, ArrayList<List<Double>> inputs, ArrayList<Integer> outputs) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
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
                if (labels.isEmpty()) {
                    label = 1;
                } else {
                    label = labels.getOrDefault(labelName, 0);
                }
                labels.put(labelName, label);

                inputs.add(attributes);
                outputs.add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void train(Perceptron perceptron, int numberOfIterations) {
        loadData(trainFileName, trainingInputs, trainingOutputs);

        for (int i = 0; i < numberOfIterations; i++) {
            for (int j = 0; j < trainingInputs.size(); j++) {
                perceptron.learn(trainingInputs.get(j), trainingOutputs.get(j));
            }
        }
    }
}
