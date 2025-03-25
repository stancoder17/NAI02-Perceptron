import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*Perceptron perceptron;
        int numberOfDimensions;

        try {
            numberOfDimensions = new BufferedReader(new FileReader("perceptron.data"))
                    .readLine()
                    .split(",")
                    .length - 1;
            perceptron = new Perceptron(numberOfDimensions);
            perceptronLearn(perceptron);

            int correctCount = 0;
            int incorrectCount = 0;

            BufferedReader reader_test = new BufferedReader(new FileReader("perceptron.test.data"));
            String line_test;

            while ((line_test = reader_test.readLine()) != null) {
                String[] line_split = line_test.split(",");
                String decisionAttribute = line_split[line_split.length - 1];

                List<Double> line_attributes = getAttributes(Arrays.copyOfRange(line_split, 0, line_split.length - 1));
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

        System.out.println("=============================================");

        // User input
        Scanner scanner = new Scanner(System.in);
        double alpha;
        int numberOfIterations;
        Perceptron userPerceptron;
        System.out.println("== Parameters for Perceptron ==");

        System.out.print("Input alpha: ");
        alpha = Double.parseDouble(scanner.nextLine());

        System.out.print("Input number of iterations: ");
        numberOfIterations = Integer.parseInt(scanner.nextLine());
        userPerceptron = new Perceptron(numberOfDimensions, alpha);

        Teacher teacher = new Teacher(userPerceptron, new File("perceptron.data"), new File("perceptron.test.data"));
        String one = "Iris-versicolor";
        try {
            teacher.teach(250, one);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Input a " + numberOfDimensions + "-dimensional vector to test (x,y,z...,n) or say \"quit\" to exit the program: ");


        String userInput = scanner.nextLine();
        while (!userInput.equals("quit")) {
            String[] input_split = userInput.split(",");

            if (input_split.length != numberOfDimensions) {
                System.out.print("Incorrect input. Please provide a " + numberOfDimensions + "-dimensional vector (x,y,z...,n) or say \"quit\" to exit the program: ");
                userInput = scanner.nextLine();
                continue;
            }
            int decision = perceptron.compute(getAttributes(input_split));
            System.out.println("Etiquette for inputted vector: " + (decision == 1 ?  : "Iris-virginica"));
            System.out.print("Input another vector or say \"quit\" to exit the program: ");
            userInput = scanner.nextLine();
        }*/
    }
    /*public static List<Double> getAttributes(String[] line_split) {
        List<Double> attributes = new ArrayList<>();

        for (String s : line_split) {
            attributes.add(Double.parseDouble(s));
        }

        return attributes;
    }*/
}
