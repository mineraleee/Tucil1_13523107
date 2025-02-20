import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter test case file path: ");
        String filename = scanner.nextLine();
        long startTime = System.currentTimeMillis();
        String filePath = "../test/" + filename;

        try {
            PuzzleSolver solver = new PuzzleSolver(filePath);
            solver.solvePuzzle();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        scanner.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime-startTime) + " ms");
    }
}