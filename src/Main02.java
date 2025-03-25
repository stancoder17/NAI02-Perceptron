import java.util.Scanner;

public class Main02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input a .csv training file");
        String trainFileName = scanner.nextLine();

        Teacher teacher = new Teacher(trainFileName);
        Perceptron perceptron = new Perceptron(teacher.getDimensions());

        teacher.train(perceptron, 100);
    }
}
