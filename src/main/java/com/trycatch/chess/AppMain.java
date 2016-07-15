package com.trycatch.chess;

import com.trycatch.chess.game.ChessPuzzle;
import com.trycatch.chess.game.ChessPuzzleSolver;

import java.util.Scanner;

/**
 * Created by koskerm on 15/07/2016.
 */
public class AppMain {
    public static void main(String[] args) throws InterruptedException {
        final String DEFAULT_PIECE_INPUT = "2K,2Q,2B,1N";

        System.out.println("Please enter the number of pieces and piece type separated by comma.");
        System.out.println("Type K for king, N for Knight, B for bishop and Q for Queen");
        System.out.println("E.g: 2K,4B,3N,2Q");
        System.out.println("Press enter for default input(2K,2B,2Q,1N)");
        Scanner scanner = new Scanner(System.in);
        String pieceInput = scanner.nextLine();
        if (pieceInput == null || "".equals(pieceInput)) {
            pieceInput = DEFAULT_PIECE_INPUT;
            System.out.println(pieceInput);
        }

        System.out.println("Please enter width of the board:");
        final int boardWidth = Integer.valueOf(scanner.nextLine());

        System.out.println("Please enter height of the board:");
        final int boardHeight = Integer.valueOf(scanner.nextLine());

        System.out.println("Do you want to see the solutions on the console(yes/no)?");
        final String solutionOnConsole = scanner.nextLine();
        final boolean writeSolutionOnConsole = "yes".equalsIgnoreCase(solutionOnConsole) || "y".equalsIgnoreCase(solutionOnConsole);

        System.out.println("Calculation started...");

        long startTime = System.currentTimeMillis();

        final ChessPuzzle chessPuzzle = ChessPuzzle.ChessPuzzleBuilder
                .aChessPuzzle()
                .withBoardWidth(boardWidth)
                .withBoardHeight(boardHeight)
                .withPieceList(pieceInput)
                .build();
        final ChessPuzzleSolver chessPuzzleSolver = new ChessPuzzleSolver(chessPuzzle, writeSolutionOnConsole);

        final int totalSolutionCount = chessPuzzleSolver.solvePuzzle();

        long endTime = System.currentTimeMillis();

        System.out.println("Calculation finished!");
        System.out.println();
        System.out.println("Piece list: " + pieceInput);
        System.out.println("Board Width: " + boardWidth);
        System.out.println("Board Height: " + boardHeight);
        System.out.println();
        System.out.println("Total solution count: " + totalSolutionCount);

        final double totalTimeInSec = (endTime - startTime) / 1000.0d;
        System.out.println("Total time: " + totalTimeInSec + " seconds");
    }

}
