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
    // menu
    int boardHeight = 0, boardWidth = 0;
    int[][] board;
    int[][] placeholderBoard;
    boolean addcolonies = false;
    //define board size
    System.out.println("introdueix l'ample del taulell");
    boardWidth = readInt() + 2;
    System.out.println("introdueix l'altura del taulell");
    boardHeight = readInt() + 2;

    //init board
    board = new int[boardHeight][boardWidth];
    placeholderBoard = new int[boardHeight][boardWidth];

    // fill board
    for (int i = 1; i < (boardHeight - 1); i++) {
      for (int j = 1; j < (boardWidth - 1); j++) {
        board[i][j] = 1;
      }
    }

    //define the way you are going to add the colonies
    do {
      System.out.println(
        "de quina manera vols crear les colonies 1- de forma manual 2- forma automatica"
      );
      int option = readInt();
      switch (option) {
        case 1:
          int addcolonie = -1;
          do {
                    
            int posHeight = 0;
            int posWidth = 0;
            do {
              System.out.println(
                "introdueix la posicio altura del 1 al " + (boardHeight - 2)
              );
              posHeight = readInt();
            } while (posHeight < 1 || posHeight > (boardHeight - 2));
            do {
              System.out.println(
                "introdueix la posicio amplada del 1 al " + (boardWidth - 2)
              );
              posWidth = readInt();
            } while (posWidth < 1 || posWidth > (boardWidth - 2));

            createColony(board, posHeight, posWidth);
            System.out.println("si vols parar d'introduir colones introdueix un 0 ");
            addcolonie = readInt();
          } while (addcolonie != 0);
          break;
        case 2:
          //adding number of colonies automaticly
          System.out.println("cuantes colonies vols crear: ");
          int numberOfColonys = readInt();

          for (int i = 0; i < numberOfColonys; i++) {
            int posHeight = getRandomNumber(1, (boardHeight - 1));
            int posWidth = getRandomNumber(1, (boardWidth - 1));

            createColony(board, posHeight, posWidth);
          }

          break;
        default:
          break;
      }
    } while (addcolonies == true);

    //print original board
    printArray(board, boardHeight, boardWidth);
    System.out.println("---------------");

    // play game
    int cont = 0;
    while (cont < 15) {
      copyBoard(board, placeholderBoard, boardHeight, boardWidth);

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

    for (int i = (posHeight - 1); i < (posHeight + 2); i++) {
      for (int j = (posWidth - 1); j < (posWidth + 2); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }

    contNeighbours -= 1;

    if (contNeighbours < ALIVE_MIN) {
      placeholderBoard[posHeight][posWidth] = 1;
    }
    if (contNeighbours > ALIVE_MAX) {
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

    for (int i = (posHeight - 1); i < (posHeight + 2); i++) {
      for (int j = (posWidth - 1); j < (posWidth + 2); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }
    contNeighbours -= 1;

    if (contNeighbours == REVIVE) {
      placeholderBoard[posHeight][posWidth] = 2;
    }

    contNeighbours = 0;
  }
}
