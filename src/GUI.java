import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;  
import java.util.HashMap; 
import java.util.Random;

public class GUI {
    private JFrame frame;
    private JLabel statusLabel;
    private File selectedFile;
    private String filePath = "";
    private boolean result = false;
    private JPanel boardPanel; 
    private PuzzleSolver solver; 

    public GUI() {
        frame = new JFrame("IQ Puzzler Pro Solver!");
        frame.setSize(700,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton loadButton = new JButton("Load File Puzzle (.txt)");
        JButton solveButton = new JButton("Solve");
        JButton savePNGButton = new JButton("Save as PNG");
        JButton saveTXTButton = new JButton("Save as TXT");
        statusLabel = new JLabel("Select a puzzle file.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(1, 1)); // Default -> blank
        boardPanel.setBorder(BorderFactory.createTitledBorder("Solved Puzzle"));

        // Select File Button
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                statusLabel.setText("Selected file: " + selectedFile.getName());
            }
        });

        // Solve Button
        solveButton.addActionListener(e -> {
            if (selectedFile == null) {
                statusLabel.setText("Please load a puzzle file first!");
                return;
            }

            statusLabel.setText("Solving...");
            
            try {
                long startTime = System.currentTimeMillis();
                solver = new PuzzleSolver(filePath); // use for save
                result = solver.solvePuzzle(); // solve the puzzle
                if (result) {
                    int iterationCount = solver.getIterationCount();
                    SwingUtilities.invokeLater(this::updateBoardDisplay); //show end board
                    long endTime = System.currentTimeMillis();
                    long duration = endTime-startTime;
                    statusLabel.setText("<html>Solved in " + iterationCount + " iterations!<br>Time: " + duration +"ms </html>");
                } else {
                    statusLabel.setText("No solution found.");
                }
            } catch (IOException ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Save as PNG Button
        savePNGButton.addActionListener(e -> {
            if (!result || solver == null) { 
                JOptionPane.showMessageDialog(frame, "No solution available to save!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save as PNG");

            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String savePath = fileToSave.getAbsolutePath();

                if (!savePath.toLowerCase().endsWith(".png")) {
                    savePath += ".png";
                }

                solver.saveToImage(savePath);
                statusLabel.setText("Image saved: " + new File(savePath).getName());
            }
        });

        // Save as TXT Button
        saveTXTButton.addActionListener(e -> {
            if (!result || solver == null) {
                JOptionPane.showMessageDialog(frame, "No solution available to save!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save as TXT");

            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String savePath = fileToSave.getAbsolutePath();

                if (!savePath.toLowerCase().endsWith(".txt")) {
                    savePath += ".txt";
                }

                solver.saveToTextFile(savePath);
                statusLabel.setText("File saved: " + new File(savePath).getName());
            }
        });

        buttonPanel.add(loadButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(savePNGButton);
        buttonPanel.add(saveTXTButton);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateBoardDisplay() {
        boardPanel.removeAll();
        char[][] board = solver.getBoard(); // get the board from solver code

        int rows = board.length;
        int cols = board[0].length;
        boardPanel.setLayout(new GridLayout(rows, cols));

        Map<Character, Color> colorMap = new HashMap<>();
        Random random = new Random();

        // variant color
        for (char[] row : board) {
            for (char c : row) {
                if (c != '.' && !colorMap.containsKey(c)) {
                    colorMap.put(c, new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))); // 3 -> RGB
                }
            }
        }

        // Make the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = board[i][j];
                JLabel cellLabel = new JLabel(String.valueOf(c), SwingConstants.CENTER);
                cellLabel.setOpaque(true);
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (c=='.') {
                    cellLabel.setBackground(Color.WHITE);
                } else {
                    cellLabel.setBackground(colorMap.get(c));
                }
                boardPanel.add(cellLabel);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
