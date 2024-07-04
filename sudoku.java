import java.util.Scanner;

public class Sudoku {
    int[][] sudoku;
    Scanner scn = new Scanner(System.in);

    Sudoku() {
        this.sudoku = new int[9][9];
    }

    Sudoku(int size) {
        this.sudoku = new int[size][size];
    }

    Sudoku(int[][] sudoku) {
        this.sudoku = new int[sudoku.length][sudoku.length];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                this.sudoku[i][j] = sudoku[i][j];
            }
        }
    }

    void input() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                sudoku[i][j] = scn.nextInt();
            }
        }
    }

    void show() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
