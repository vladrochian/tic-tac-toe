package ro.vladrochian.projects.ttt.player;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.TableState;

import java.util.List;
import java.util.Scanner;

public class Human implements Player {
  private Scanner input;
  {
    input = new Scanner(System.in);
  }

  @Override
  public Position getMove(int tableState) {
    int player = TableState.getCurrentPlayer(tableState);
    System.out.println("Player " + player + "'s turn");
    List<Position> availableMoves = TableState.getAvailableMoves(tableState);
    System.out.println("Enter move:");
    Position move = readMove();
    while (!availableMoves.contains(move)) {
      System.out.println("Move not valid. Enter another move:");
      move = readMove();
    }
    return move;
  }

  private Position readMove() {
    int x = input.nextInt();
    int y = input.nextInt();
    return Position.from(x, y);
  }
}
