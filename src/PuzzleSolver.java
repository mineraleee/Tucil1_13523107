import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Arrays; 
import java.util.Map;  
import java.util.HashMap; 
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PuzzleSolver {
    private int N, M, P;
    private char[][] board;
    private List<PuzzlePiece> pieces;
    private int iterationCount = 0;
    private String caseType;
    private Scanner scanner;

    public PuzzleSolver(String filename) throws IOException {
        scanner = new Scanner(System.in);
        readInputFile(filename);
    }

    private void readInputFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        caseType = br.readLine().trim(); //DEFAULT
    
        board = new char[N][M];
        for (char[] row : board) Arrays.fill(row, '.');
    
        pieces = new ArrayList<>();
        List<String> shape = new ArrayList<>();
        
        String line = br.readLine();
        if (line== null) return;  // exit if the file blank
    
        char currentPiece = findFirstLetter(line); // take the first char in line
    
        while (line != null) {
            char firstLetter = findFirstLetter(line);
    
            if (firstLetter != currentPiece) { 
                // save the previous line
                if (!shape.isEmpty()) {
                    pieces.add(new PuzzlePiece(shape, currentPiece));
                }
    
                shape = new ArrayList<>();
                currentPiece = firstLetter; // update the new char
            }
    
            shape.add(line); // newline
            line = br.readLine();
        }
    
        // Last piece
        if (!shape.isEmpty()) {
            pieces.add(new PuzzlePiece(shape, currentPiece));
        }
        br.close();
        if (pieces.size() != P){
            showDifferentTotalPiecesWarning();
        }
    }
    
    private char findFirstLetter(String line) {
        for (char c : line.toCharArray()) {
            if (Character.isLetter(c)) {
                return c; // return the first letter
            }
        }
        return '.'; // Default 
    }

    private boolean solve(int index) {
        if (isBoardFullyFilled()) {
            // still there's other pieces but the board already fullfill
            if (index < pieces.size()) {
                //showExcessPiecesWarning(); // show the pop up cause there's extra pieces
                return false;
            }
            return true;
        }
    
        if (index == pieces.size()) return false;

        PuzzlePiece piece = pieces.get(index);
        List<PuzzlePiece> transformations = piece.getAllTransformations();

        for (PuzzlePiece transformed : transformations) {
            for (int i = 0;i < N;i++) {
                for (int j = 0; j < M; j++) {
                    if (canPlacePiece(transformed,i,j)) {
                        placePiece(transformed,i,j);
                        if (solve(index + 1))return true;
                        removePiece(transformed,i,j);
                        iterationCount++; //the combination all of pieces fail
                    }
                }
            }
        }
        return false;
    }

    private boolean isBoardFullyFilled() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true; //board is completely filled
    }

    private boolean canPlacePiece(PuzzlePiece piece, int row, int col) {
        for (int i = 0; i < piece.shape.length; i++) {
            for (int j = 0; j < piece.shape[i].length; j++) {
                if (piece.shape[i][j] != '.') {
                    int r = row + i, c = col + j;
                    if (r >= N || c >= M || board[r][c] != '.') return false;
                }
            }
        }
        return true;
    }

    private void placePiece(PuzzlePiece piece, int row, int col) {
        for (int i = 0; i < piece.shape.length; i++) {
            for (int j = 0; j < piece.shape[i].length; j++) {
                if (piece.shape[i][j] != '.') {
                    board[row + i][col + j] = piece.label;
                }
            }
        }
    }

    private void removePiece(PuzzlePiece piece, int row, int col) {
        for (int i = 0; i < piece.shape.length; i++) {
            for (int j = 0; j < piece.shape[i].length; j++) {
                if (piece.shape[i][j] != '.') {
                    board[row + i][col + j] = '.';
                }
            }
        }
    }

    private void showDifferentTotalPiecesWarning() {
        JOptionPane.showMessageDialog(null,"The number of pieces doesn't match!","Warning!!!",JOptionPane.WARNING_MESSAGE);
    }

    public boolean solvePuzzle() {
        if (solve(0)) {
            iterationCount++; // add the right combination
            //printBoard(); //uncomment if wanna run in terminal
            //System.out.println("Iterations: " + iterationCount);
            return true;
        } return false;
    }

    //uncomment if u wanna run in terminal
    // private void printBoard() {
    //     Map<Character, String> colorMap = new HashMap<>();
    //     Random random = new Random();

    //     for (char[] row : board) {
    //         for (char c : row) {
    //             if (c != '.' && !colorMap.containsKey(c)) {
    //                 int colorCode = 16 + random.nextInt(240); 
    //                 colorMap.put(c, "\u001B[38;5;" + colorCode + "m");
    //             }
    //         }
    //     }
    
    //     for (char[] row : board) {
    //         for (char c : row) {
    //             if (c == '.') {
    //                 System.out.print("\u001B[37m" + c + " "); 
    //             } else {
    //                 System.out.print(colorMap.get(c) + c + " ");
    //             }
    //         }
    //         System.out.println("\u001B[0m"); 
    //     }
    
    //     System.out.print("Do you want to save the solution as an image? (yes/no): ");
    //     String response = scanner.nextLine().trim().toLowerCase();
    
    //     if (response.equals("yes") || response.equals("y")) {
    //         System.out.print("Input the filename: ");
    //         String filename = scanner.nextLine().trim().toLowerCase();
    //         String name = "../test/result/"+ filename + ".png";
    //         saveToImage(name);
    //     } else if (response.equals("no") || response.equals("n")) {
    //         System.out.println("Solution not saved.");
    //     } else {
    //         System.out.println("Invalid input. Please enter yes or no.");
    //     }
    // }

    public int getIterationCount(){
        return iterationCount;
    }

    public char[][] getBoard() {
        return board;
    }

    //the form will be rounded/circle
    public void saveToImage(String filePath) { 
        int cellSize = 70;
        int width = M * cellSize;
        int height = N * cellSize;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
    
        //background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    
        // mapping unique color
        Map<Character, Color> colorMap = new HashMap<>();
        Random random = new Random();
    
        for (char[] row : board) {
            for (char c : row) {
                if (c != '.' && !colorMap.containsKey(c)) {
                    colorMap.put(c, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                }
            }
        }
    
        // grid
        int padding =5;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char c = board[i][j];
                if (c != '.') {
                    g.setColor(colorMap.get(c));
                    g.fillOval(j * cellSize + padding / 2, i * cellSize + padding / 2, cellSize - padding, cellSize - padding);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
        g.dispose();
    
        // save as png
        try {
            File outputFile = new File(filePath);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to: " +filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving image: " +e.getMessage());
        }
    }

    //save as txt
    public void saveToTextFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (char[] row:board) {
                writer.write(new String(row)); //convert char[] to String
                writer.newLine(); //newline
            }
            System.out.println("Board saved to: " +filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving board: " +e.getMessage());
        }
    }
}
