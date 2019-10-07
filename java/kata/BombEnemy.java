package kata;

import java.util.*;

/**
 * Bomb Enermy
 * <p>https://leetcode.com/problems/bomb-enemy/
 * <p> Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty
 * '0' (the number zero), return the maximum enemies you can kill using one
 * bomb.
 * <p> The bomb kills all the enemies in the same row and column from the
 * planted point until it hits the wall since the wall is too strong to be
 * destroyed.
 * <p>Note: You can only put the bomb at an empty cell.
 */
public class BombEnemy {
  static Result bombEnemy(char[][] m) {
    Result result = new Result();

    if (m.length == 0 || m[0].length == 0) {
      return result;
    }

    int rows = m.length;
    int cols = m[0].length;

    int[][] leftcounts = new int[rows][cols];
    int[][] topcounts = new int[rows][cols];
    int[][] rightcounts = new int[rows][cols];
    int[][] bottomcounts = new int[rows][cols];

    // First pass, from top left to bottom right, count left and up directions
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (m[r][c] == 'X') {
          continue;
        }

        // count left direction
        if (c != 0) {
          leftcounts[r][c] = leftcounts[r][c - 1] + (m[r][c - 1] == 'e' ? 1 : 0);
        }
        // count top direction
        if (r != 0) {
          topcounts[r][c] = topcounts[r-1][c] + (m[r-1][c] == 'e' ? 1 : 0);
        }
      }
    }

    // Second pass, from bottom right to top left, count right and down directions
    for (int r = rows - 1; r >= 0; r--) {
      for (int c = cols - 1; c >= 0; c--) {
        if (m[r][c] == 'X') {
          continue;
        }

        // count right direction
        if (c != cols - 1) {
          rightcounts[r][c] = rightcounts[r][c + 1] + (m[r][c + 1] == 'e' ? 1 : 0);
        }
        // count bottom direction
        if (r != rows - 1) {
          bottomcounts[r][c] = bottomcounts[r+1][c] + (m[r+1][c] == 'e' ? 1 : 0);
        }
      }
    }

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (m[r][c] != ' ') {
          continue;
        }
        int n = leftcounts[r][c] + rightcounts[r][c] + topcounts[r][c] + bottomcounts[r][c];
        if (n > result.max) {
          result.max = n;
          result.row = r;
          result.col = c;
        }
      }
    }

    return result;
  }

  public static void main(String args[]) {
    char[][] m = new char[5][5];
    m[0][0] = 'e';
    m[1][0] = 'e';
    m[3][0] = 'X';
    m[2][2] = 'e';
    m[2][3] = 'X';
    m[2][4] = 'e';
    runSample(m);
  }

  static void runSample(char[][] m) {
    padMatrix(m);
    printMatrix(m);
    System.out.println(bombEnemy(m));
  }

  static void printMatrix(char[][] m) {
    if (m.length == 0 || m[0].length == 0) {
      return;
    }
    for (int r = 0; r < m.length; r++) {
        System.out.println(Arrays.toString(m[r]));
    }
  }

  static void printMatrix(int[][] m) {
    if (m.length == 0 || m[0].length == 0) {
      return;
    }
    for (int r = 0; r < m.length; r++) {
        System.out.println(Arrays.toString(m[r]));
    }
  }

  static void padMatrix(char[][] m) {
    if (m.length == 0 || m[0].length == 0) {
      return;
    }
    for (int r = 0; r < m.length; r++) {
      for (int c = 0; c < m[0].length; c++) {
        if (m[r][c] == '\0') {
          m[r][c] = ' ';
        }
      }
    }
  }

  static class Result {
    int max;
    int row;
    int col;

    public String toString() {
      return String.format("Result(max=%s, row=%s, col=%s)", max, row, col);
    }
  }
}
