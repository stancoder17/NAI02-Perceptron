import java.util.*;

public class Teacher {
    private final ArrayList<List<Double>> trainingInputs;
    private final ArrayList<Integer> trainingOutputs;
    private final String trainFileName;

    public Teacher(String trainFileName) {
        this.trainFileName = trainFileName;
        this.trainingInputs = new ArrayList<>();
        this.trainingOutputs = new ArrayList<>();
    }

    public void train(Perceptron perceptron, int numberOfIterations) {
        DataLoader dataLoader = new DataLoader(trainFileName);
        dataLoader.loadData(trainingInputs, trainingOutputs);

        for (int i = 0; i < numberOfIterations; i++) {
            for (int j = 0; j < trainingInputs.size(); j++) {
                perceptron.learn(trainingInputs.get(j), trainingOutputs.get(j));
            }
            System.out.println("Iteration " + (i + 1));
        }
    }
}
