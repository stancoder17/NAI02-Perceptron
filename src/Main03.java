public class Main03 {

    public static void main(String[] args) {
        String trainFileName = "perceptron.data";
        String testFileName = "perceptron.test.data";
        int numberOfIterations = 100;

        DataLoader dataLoader = new DataLoader(trainFileName);

        Teacher teacher = new Teacher(trainFileName);
        Perceptron perceptron = new Perceptron(dataLoader.getDimensions());
        teacher.train(perceptron, numberOfIterations);

        Evaluator evaluator = new Evaluator(testFileName, teacher);
        evaluator.evaluate(perceptron);
    }
}
