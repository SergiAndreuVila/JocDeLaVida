import java.util.Scanner;

public class JocDeLaVida {

  Scanner e = new Scanner(System.in);

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
    boardWidth = readInt() + 2;
    System.out.println("introdueix l'altura del taulell");
    boardHeight = readInt() + 2;

    //init board
    int board[][] = new int[boardHeight][boardWidth];
    int placeholderBoard[][] = new int[boardHeight][boardWidth];

    // fill board
    for (int i = 1; i < (boardHeight - 1); i++) {
      for (int j = 1; j < (boardWidth - 1); j++) {
        board[i][j] = 1;
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

          int cont = 0;
          while (cont < 5) {
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

            board[posHeight][posWidth] = 2;

            printArray(board, boardHeight, boardWidth);
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
          aliveMax =readInt();
          System.out.println("introdueix el numero per reviure:");
          revive = readInt();
          break;
        default:
          addcolonies = false;
          break;
      }
    } while (addcolonies == false);

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
            checkAlive(board, placeholderBoard, i, j, aliveMin, aliveMax);
          } else {
            checkDead(board, placeholderBoard, i, j, revive);
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

      } else if (board[x][y] == 2) {
        board[x][y] = 2;
        
      } else if (board[x][y] == 0) {
        board[x][y] = 0;
      }
    }
  }

  public void checkAlive(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth, int aliveMin , int aliveMax
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

    if (contNeighbours < aliveMin) {
      placeholderBoard[posHeight][posWidth] = 1;
    }
    if (contNeighbours > aliveMax) {
      placeholderBoard[posHeight][posWidth] = 1;
    }

    contNeighbours = 0;
  }

  public void checkDead(
    int[][] board,
    int[][] placeholderBoard,
    int posHeight,
    int posWidth, int revive
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

    if (contNeighbours == revive) {
      placeholderBoard[posHeight][posWidth] = 2;
    }

    contNeighbours = 0;
  }
}
