# IQ Puzzler Pro Solver

IQ Puzzler Pro is a logic-based puzzle game where players must arrange pieces on a grid to complete a given challenge. The goal is to fit all the pieces correctly within the constraints provided.

This program is designed to find solutions for IQ Puzzler Pro using a 'brute-force' approach. It systematically explores all possible configurations to determine valid solutions for the puzzle.

## Installation

This program is written in Java and all required libraries are available in the Java standard library.
Ensure that Java is installed on your system. You can verify this by running:
```python
java -version
```
## Structure Program
```
TUCIL1_13523107/
├── bin/
├── doc/
│   ├── Tucil1_K2_13523107_Heleni Gratia.pdf
├── src/
│   ├── GUI.java
│   ├── Main.java   
│   ├── PuzzlePiece.java 
│   ├── PuzzleSolver.java 
├── test/
│   ├── result/
│   │     ├── result1.png
│   │     ├── result1.txt
│   │     ├── result2.png
│   │     ├── result2.txt
│   ├── test1.txt
│   ├── test2.txt
│   ├── test3.txt
│   ├── test4.txt
│   ├── test5.txt
│   ├── test6.txt
│   ├── test7.txt
│   ├── test8.txt
└── README.md         
```
## How to Run the Program
1. **Clone repository**
```python
git clone https://github.com/mineraleee/Tucil1_13523107
```
2. **Navigate to the src directory**
```python
cd src
```
3. **Compile the Java files using the following command:**
```python
javac Main.java PuzzleSolver.java PuzzlePiece.java GUI.java
```
4. **Run the program with:**

To run the GUI version:
```python
java GUI
```
If you want to run it in CLI mode, use:
```python
java Main
```
5. **Description of Buttons**

Once the GUI is displayed, the following buttons are available:

**-Load File Puzzle:** Load a puzzle from a .txt file (test case input).

**-Solve:** Compute and display the solution.

**-Save as PNG:** Save the solution as an image.

**-Save as TXT:** Save the solution as a text file.

## Contributor

| NIM      | Name  | Class |
| :---:    | :---: | :---: |
| 13523107| Heleni Gratia Meitrina Tampubolon|02|