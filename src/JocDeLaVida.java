import java.util.Scanner;

public class JocDeLaVida {

  Scanner e = new Scanner(System.in);
  public static final int BOARD_MARGIN = 2;
  public static final int ALIVE_CELL = 2;
  public static final int DEAD_CELL = 1;
  public static final int NO_CELL = 0;

  public static void main(String[] args) {
    JocDeLaVida a = new JocDeLaVida();
    a.principal();
  }

  public void principal() {
    int aliveMin = 2;
    int aliveMax = 3;
    int revive = 3;

    int boardHeight = 0, boardWidth = 0;
    int posHeight = 0, posWidth = 0;

    boolean addcolonies = false;

    //define board size
    System.out.println("introdueix l'ample del taulell");
    boardWidth = readInt() + BOARD_MARGIN;
    System.out.println("introdueix l'altura del taulell");
    boardHeight = readInt() + BOARD_MARGIN;

    //init board
    int board[][] = new int[boardHeight][boardWidth];
    int placeholderBoard[][] = new int[boardHeight][boardWidth];

    // fill board
    for (int i = 1; i < (boardHeight - 1); i++) {
      for (int j = 1; j < (boardWidth - 1); j++) {
        board[i][j] = DEAD_CELL;
      }
    }

    //define the way you are going to add the colonies
    do {
      System.out.println(
        "que vols fer: \n 1- de forma manual\n 2- forma automatica\n 3-canvia les normes del joc"
      );
      int option = readInt();

      switch (option) {
        case 1:
          posHeight = 0;
          posWidth = 0;
          int insertedCells = 5;

          int cont = 0;
          while (cont < insertedCells) {
            do {
              System.out.println(
                "introdueix la posicio altura del 1 al " + (boardHeight - BOARD_MARGIN)
              );
              posHeight = readInt();
            } while (posHeight < 1 || posHeight > (boardHeight - BOARD_MARGIN));
            do {
              System.out.println(
                "introdueix la posicio amplada del 1 al " + (boardWidth - BOARD_MARGIN)
              );
              posWidth = readInt();
            } while (posWidth < 1 || posWidth > (boardWidth - BOARD_MARGIN));

            board[posHeight][posWidth] = ALIVE_CELL;

            printBoard(board, boardHeight, boardWidth);
            cont++;
          }
          addcolonies = true;

          break;
        case 2:
          //adding number of colonies automaticly
          System.out.println("cuantes colonies vols crear: ");
          int numberOfColonys = readInt();

          for (int i = 0; i < numberOfColonys; i++) {
            posHeight = getRandomNumber(1, (boardHeight - 1));
            posWidth = getRandomNumber(1, (boardWidth - 1));

            createColony(board, posHeight, posWidth);
          }
          addcolonies = true;

          break;
        case 3:
          //change rules of the game
          System.out.println("introdueix el minim per estar viu:");
          aliveMin = readInt();
          System.out.println("introdueix el maxim per estar viu:");
          aliveMax = readInt();
          System.out.println("introdueix el numero per reviure:");
          revive = readInt();
          break;
      }
    } while (addcolonies == false);

    //print original board
    printBoard(board, boardHeight, boardWidth);
    System.out.println("---------------");
//TODO: add delay
    // play game
    int cont = 0;
    while (cont < 15) {
      copyBoard(board, placeholderBoard, boardHeight, boardWidth);

      for (int i = 1; i < boardHeight - 1; i++) {
        for (int j = 1; j < boardWidth - 1; j++) {
          if (isAlive(board[i][j]) == true) {
            applyAliveRules(board, placeholderBoard, i, j, aliveMin, aliveMax);
          } else {
            applyDeadRules(board, placeholderBoard, i, j, revive);
          }
        }
      }
      copyBoard(placeholderBoard, board, boardHeight, boardWidth);
      printBoard(board, boardHeight, boardWidth);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      cont++;
    }
  }

  public int readInt() {
    int num;
    num = e.nextInt();
    return num;
  }

  public void printBoard(int[][] array, int height, int width) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        switch (array[i][j]) {
          case NO_CELL:
            System.out.print(' ');
            break;
          case DEAD_CELL:
            System.out.print('_');
            break;
          case ALIVE_CELL:
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
    board[posHeight][posWidth] = ALIVE_CELL;

    int colonySize = 4;
    //int maxTry = 15;

    while (colonySize > 0 ) {
      int x = getRandomNumber((posHeight - 1), (posHeight + 2));
      int y = getRandomNumber((posWidth - 1), (posWidth + 2));

      if (board[x][y] == DEAD_CELL) {
        board[x][y] = ALIVE_CELL;
        colonySize--;
      } else if (board[x][y] == ALIVE_CELL) {
        board[x][y] = ALIVE_CELL;
      } else if (board[x][y] == NO_CELL) {
        board[x][y] = NO_CELL;
      }
      //maxTry--;
    }
  }

  public void applyAliveRules(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth,
    int aliveMin,
    int aliveMax
  ) {
    int contNeighbours = 0;

    for (int i = (posHeight - 1); i < (posHeight + BOARD_MARGIN); i++) {
      for (int j = (posWidth - 1); j < (posWidth + BOARD_MARGIN); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }

    contNeighbours -= 1;

    if (contNeighbours < aliveMin) {
      placeholderBoard[posHeight][posWidth] = 1;
    }
    if (contNeighbours > aliveMax) {
      placeholderBoard[posHeight][posWidth] = 1;
    }

    contNeighbours = 0;
  }

  public void applyDeadRules(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth,
    int revive
  ) {
    int contNeighbours = 0;

    for (int i = (posHeight - 1); i < (posHeight + BOARD_MARGIN); i++) {
      for (int j = (posWidth - 1); j < (posWidth + BOARD_MARGIN); j++) {
        if (board[i][j] == 2) {
          contNeighbours++;
        }
      }
    }
    contNeighbours -= 1;

    if (contNeighbours == revive) {
      placeholderBoard[posHeight][posWidth] = 2;
    }

    contNeighbours = 0;
  }
}
