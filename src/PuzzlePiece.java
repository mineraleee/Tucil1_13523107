import java.util.*;

public class PuzzlePiece {
    char[][] shape;
    char label;

    //save the pieces
    public PuzzlePiece(List<String> shapeLines, char label) {
        this.label = label;
    
        // max width
        int maxChars = 0;
        for (String line : shapeLines) {
            maxChars = Math.max(maxChars, line.length());
        }
    
        this.shape = new char[shapeLines.size()][maxChars];
    
        for (int i = 0;i<shapeLines.size();i++) {
            String line = shapeLines.get(i);
            for (int j = 0; j < maxChars; j++) {
                if (j < line.length()) {
                    char currentChar = line.charAt(j);
                    this.shape[i][j] = (currentChar == ' ')?'.' : currentChar;
                } else {
                    this.shape[i][j] = '.'; //will fill remaining space with dots --> matriks (2D)
                }
            }
        }
    }

    //Generate all possible transformations (rotations and reflections)
    public List<PuzzlePiece> getAllTransformations() {
        Set<String> uniqueShapes = new HashSet<>();
        List<PuzzlePiece> transformations = new ArrayList<>();
        char[][] currentShape = this.shape;
    
        for (int i = 0;i < 4;i++) { // Rotate 4 times -> 90,180,270,360 
            String shapeStr = toString(currentShape);
            if (uniqueShapes.add(shapeStr)) {
                transformations.add(new PuzzlePiece(convertToList(currentShape), label));
            }
    
            char[][] reflected = reflect(currentShape);
            shapeStr = toString(reflected);
            if (uniqueShapes.add(shapeStr)) {
                transformations.add(new PuzzlePiece(convertToList(reflected), label));
            }
    
            currentShape = rotate(currentShape); //rotate for next iteration
        }
    
        return transformations;
    }
    
    //convert char[][] to String for uniqueness check
    private String toString(char[][] arr) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : arr) {
            sb.append(row).append("\n");
        }
        return sb.toString();
    }
    
    //rotate the shape 90° clockwise
    private char[][] rotate(char[][] firstform) {
        int rows = firstform.length;
        int cols = firstform[0].length;
        char[][] rotated = new char[cols][rows];

        for (int i = 0;i < rows;i++) {
            for (int j = 0;j < cols;j++) {
                rotated[j][rows - i - 1] = firstform[i][j];
            }
        }
        return rotated;
    }

    //reflect the shape horizontally
    private char[][] reflect(char[][] firstform) {
        int rows = firstform.length;
        int cols = firstform[0].length;
        char[][] flipped = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0;j < cols;j++) {
                flipped[i][cols - j - 1] = firstform[i][j];
            }
        }
        return flipped;
    }

    //cnvert char[][] to List<String> (storing transformations)
    private List<String> convertToList(char[][] shapeArray) {
        List<String> result = new ArrayList<>();
        for (char[] row : shapeArray) {
            result.add(new String(row));
        }
        return result;
    }

    //make sure there's no duplicate form
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null||getClass()!=obj.getClass()) return false;
        PuzzlePiece other = (PuzzlePiece) obj;
        return Arrays.deepEquals(this.shape, other.shape);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(shape);
    }
}