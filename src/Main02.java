import java.util.Scanner;

public class Main02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input a .csv training file");
        String trainFileName = scanner.nextLine();

        Teacher teacher = new Teacher(trainFileName);
        DataLoader dataLoader = new DataLoader(trainFileName);
        Perceptron perceptron = new Perceptron(dataLoader.getDimensions());


        teacher.train(perceptron, 100);
    }
}
