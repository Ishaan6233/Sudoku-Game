import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GUI extends solver {

    private JFrame frame;
    private JButton b1, b2, b3, checkButton;
    private JTextField[] fields;
    private int gridSize;
    private int[][] initialSudoku;

    public GUI() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(800, 600));
        showDifficultySelectionScreen();
    }

    public void showDifficultySelectionScreen() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("Select Difficulty Level:");
        panel.add(label);

        JButton easyButton = new JButton("Easy (4x4)");
        easyButton.addActionListener(e -> setupGame(4, 4));
        panel.add(easyButton);

        JButton mediumButton = new JButton("Medium (6x6)");
        mediumButton.addActionListener(e -> setupGame(6, 8));
        panel.add(mediumButton);

        JButton hardButton = new JButton("Hard (9x9)");
        hardButton.addActionListener(e -> setupGame(9, 16));
        panel.add(hardButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sudoku Solver");
        frame.pack();
        frame.setVisible(true);
    }

    private void setupGame(int size, int filledCells) {
        this.gridSize = size;
        initialSudoku = generateRandomSudoku(size, filledCells);
        fields = new JTextField[size * size];

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(size + 1, size));

        for (int i = 0; i < size * size; i++) {
            fields[i] = new JTextField();
            JTextField f = fields[i];
            f.setHorizontalAlignment(JTextField.CENTER);
            f.setBackground(Color.LIGHT_GRAY);
            f.setEditable(true);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            f.setBorder(border);

            f.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char pressedKey = ke.getKeyChar();
                    if (pressedKey == 8) {
                        f.setText("");
                        f.setBackground(Color.LIGHT_GRAY);
                    } else if ((pressedKey >= '1' && pressedKey <= '9')) {
                        f.setText("" + pressedKey);
                        f.setEditable(false);
                        f.setBackground(Color.GRAY);
                    } else {
                        f.setEditable(false);
                    }
                }
            });
            f.setEditable(true);
            panel.add(f);
        }

        fillInitialSudoku();

        b1 = new JButton("Solve");
        b1.addActionListener(e -> solveSudoku());
        panel.add(b1);

        checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());
        panel.add(checkButton);

        b2 = new JButton("Clear grid");
        b2.addActionListener(e -> clearGrid());
        panel.add(b2);

        b3 = new JButton("Exit");
        b3.addActionListener(e -> {
            frame.dispose();
            System.out.println("Exited");
        });
        panel.add(b3);

        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void fillInitialSudoku() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (initialSudoku[i][j] != 0) {
                    int index = gridSize * i + j;
                    fields[index].setText(String.valueOf(initialSudoku[i][j]));
                    fields[index].setEditable(false);
                    fields[index].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private int[][] generateRandomSudoku(int size, int filledCells) {
        int[][] sudoku = new int[size][size];
        Random random = new Random();
        for (int i = 0; i < filledCells; i++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            int value = random.nextInt(size) + 1;
            if (sudoku[row][col] == 0) {
                sudoku[row][col] = value;
            }
        }
        return sudoku;
    }

    private void clearGrid() {
        for (int i = 0; i < gridSize * gridSize; i++) {
            if (fields[i].isEditable()) {
                fields[i].setText("");
                fields[i].setBackground(Color.LIGHT_GRAY);
                fields[i].setForeground(Color.BLACK);
            }
        }
    }

    private void checkSolution() {
        int[][] userSolution = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                String val = fields[gridSize * i + j].getText();
                userSolution[i][j] = val.equals("") ? 0 : Integer.parseInt(val);
            }
        }
        Sudoku sudoku = new Sudoku(userSolution);
        if (isValid(sudoku)) {
            JOptionPane.showMessageDialog(frame, "Correct Solution!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect Solution!", "Result", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solveSudoku() {
        Sudoku inputPuzzle = new Sudoku(initialSudoku);
        int[][] solution = new int[gridSize][gridSize];
        solveAndGetFirstSolution(inputPuzzle, 0, 0, solution);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                fields[gridSize * i + j].setText(String.valueOf(solution[i][j]));
                fields[gridSize * i + j].setBackground(Color.GREEN);
            }
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
