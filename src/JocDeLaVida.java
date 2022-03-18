import java.util.Scanner;

public class JocDeLaVida {

  Scanner e = new Scanner(System.in);
  public static final int ALIVE_MIN = 2;
  public static final int ALIVE_MAX = 3;
  public static final int REVIVE = 3;

  public static void main(String[] args) {
    JocDeLaVida a = new JocDeLaVida();
    a.principal();
  }

  public void principal() {
    //define board size
    System.out.println("introdueix l'ample del taulell");
    int boardWidth = readInt() + 2;
    System.out.println("introdueix l'altura del taulell");
    int boardHeight = readInt() + 2;

    int[][] board = new int[boardHeight][boardWidth];
    int[][] placeholderBoard = new int[boardHeight][boardWidth];

    for (int i = 1; i < (boardHeight - 1); i++) {
      for (int j = 1; j < (boardWidth - 1); j++) {
        board[i][j] = 1;
      }
    }

    System.out.println("cuantes colonies vols crear: ");
    int numberOfColonys = readInt();

    for (int i = 0; i < numberOfColonys; i++) {
      int posHeight = getRandomNumber(1, (boardHeight - 1));
      int posWidth = getRandomNumber(1, (boardWidth - 1));

      createColony(board, posHeight, posWidth);
      
    }
    printArray(board, boardHeight, boardWidth);
      System.out.println("---------------");
    int cont = 0;
    
    while (cont < 10) {
      copyBoard(board, placeholderBoard, boardHeight, boardWidth);

      //printArray(board, boardHeight, boardWidth);

      for (int i = 1; i < boardHeight - 1; i++) {
        for (int j = 1; j < boardWidth - 1; j++) {
          if (isAlive(board[i][j]) == true) {
            checkAlive(board, placeholderBoard, i, j);
          } else {
            checkDead(board, placeholderBoard, i, j);
          }
        }
      }
      copyBoard(placeholderBoard, board, boardHeight, boardWidth);
      printArray(board, boardHeight, boardWidth);
      cont++;
    }
  }

  public int readInt() {
    int num;
    num = e.nextInt();
    return num;
  }

  public void printArray(int[][] array, int height, int width) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        switch (array[i][j]) {
          case 0:
            System.out.print(' ');
            break;
          case 1:
            System.out.print('_');
            break;
          case 2:
            System.out.print('#');
            break;
        }
      }
      System.out.println(" ");
    }
  }

  public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public boolean isAlive(int cell) {
    boolean alive = true;

    if (cell == 1) {
      alive = false;
    }

    return alive;
  }

  public void copyBoard(
    int[][] board,
    int[][] placeholderBoard,
    int height,
    int width
  ) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        placeholderBoard[i][j] = board[i][j];
      }
    }
  }

  public void createColony(int[][] board, int posHeight, int posWidth) {
    board[posHeight][posWidth] = 2;

    int cont = 4;

    while (cont > 0) {
      int x = getRandomNumber((posHeight - 1), (posHeight + 2));
      int y = getRandomNumber((posWidth - 1), (posWidth + 2));

      if (board[x][y] == 1) {
        board[x][y] = 2;
        cont--;
      }
    }
  }

  public void checkAlive(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth
  ) {
    int contNeighbours = 0;

    for (int i = (posHeight - 1); i < (posHeight + 1); i++) {
      for (int j = (posWidth - 1); j < (posWidth + 1); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }
    contNeighbours -= 1;

    if (contNeighbours < 2 || contNeighbours > 3) {
      placeholderBoard[posHeight][posWidth] = 1;
    }

    contNeighbours = 0;
  }

  public void checkDead(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth
  ) {
    int contNeighbours = 0;

    for (int i = (posHeight - 1); i < (posHeight + 1); i++) {
      for (int j = (posWidth - 1); j < (posWidth + 1); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }
    contNeighbours -= 1;

    if (contNeighbours == 3) {
      placeholderBoard[posHeight][posWidth] = 2;
    }

    contNeighbours = 0;
  }
}
