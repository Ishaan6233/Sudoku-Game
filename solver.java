public class solver {
    public static boolean checkRow(Sudoku test, int i, int j) {
        for (int col = 0; col < test.sudoku.length; col++) {
            if (col == j) continue;
            if (test.sudoku[i][col] == test.sudoku[i][j] && test.sudoku[i][col] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkColumn(Sudoku test, int i, int j) {
        for (int row = 0; row < test.sudoku.length; row++) {
            if (row == i) continue;
            if (test.sudoku[row][j] == test.sudokua[i][j] && test.sudoku[row][j] != 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] getBox(Sudoku test, int i, int j) {
        int boxSize = (int) Math.sqrt(test.sudoku.length);
        return new int[]{(i / boxSize) * boxSize, (j / boxSize) * boxSize};
    }

    public static boolean checkBox(Sudoku test, int i, int j) {
        int[] startingPt = getBox(test, i, j);
        int boxSize = (int) Math.sqrt(test.sudoku.length);
        for (int row = startingPt[0]; row < startingPt[0] + boxSize; row++) {
            for (int col = startingPt[1]; col < startingPt[1] + boxSize; col++) {
                if (row == i && col == j) continue;
                if (test.sudoku[row][col] == test.sudoku[i][j] && test.sudoku[row][col] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(Sudoku test) {
        for (int i = 0; i < test.sudoku.length; i++) {
            for (int j = 0; j < test.sudoku[i].length; j++) {
                if (!checkRow(test, i, j) || !checkColumn(test, i, j) || !checkBox(test, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(int cell) {
        return cell == 0;
    }

    public static boolean solveAndGetFirstSolution(Sudoku test, int i, int j, int[][] solution) {
        if (i == test.sudoku.length - 1 && j == test.sudoku.length) {
            updateResult(test.sudoku, solution);
            return true;
        }

        if (j == test.sudoku.length) {
            i++;
            j = 0;
        }

        if (!isEmpty(test.sudoku[i][j])) {
            return solveAndGetFirstSolution(test, i, j + 1, solution);
        }

        for (int val = 1; val <= test.sudoku.length; val++) {
            test.sudoku[i][j] = val;
            if (checkRow(test, i, j) && checkColumn(test, i, j) && checkBox(test, i, j)) {
                if (solveAndGetFirstSolution(test, i, j + 1, solution)) {
                    return true;
                }
            }
            test.sudoku[i][j] = 0;
        }

        return false;
    }

    public static void updateResult(int[][] result, int[][] solution) {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                solution[i][j] = result[i][j];
            }
        }
    }

    public static void main(String[] args) {
        new GUI().showDifficultySelectionScreen();
    }
}
