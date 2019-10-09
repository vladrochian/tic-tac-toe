package ro.vladrochian.projects.ttt.player;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.List;
import java.util.Scanner;

public class Human extends Player {
  private Table table;
  private Scanner input;

  public Human(String name, Table table) {
    super(name);
    this.table = table;
    input = new Scanner(System.in);
  }

  @Override
  public Position getMove(long tableState) {
    System.out.println(name + "'s turn");
    List<Position> availableMoves = table.getAvailableMoves(tableState);
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
