package com.trycatch.chess.game;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.*;
import com.trycatch.chess.util.PositionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by kosker on 07/07/16.
 */
public class ChessPuzzleSolver {
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter the number of pieces and piece type separated by comma.");
        System.out.println("Type K for king, N for Knight, B for bishop and Q for Queen");
        System.out.println("E.g: 2K,4B,3N,2Q");
        System.out.println("Press enter for default input, that is 2K,2B,2Q,1N");
        Scanner scanner = new Scanner(System.in);
        String pieceInput = scanner.nextLine();
        if (pieceInput == null || "".equals(pieceInput)) {
            pieceInput = "2K,2Q,2B,1N";
            System.out.println(pieceInput);
        }

        System.out.println("Please enter width of the board:");
        final int boardWidth = scanner.nextInt();

        System.out.println("Please enter height of the board:");
        final int boardHeight = scanner.nextInt();

        List<Piece> pieceList = readPieces(pieceInput);

        final int[] pieces = pieceList.stream().mapToInt(Piece::getID).toArray();
        Arrays.sort(pieces);

        long startTime = System.currentTimeMillis();

        final BoardController boardController = new BoardController(new Board(boardWidth, boardHeight));
        PositionUtil.init(boardWidth, boardHeight);
        BoardOccupyManager.createOccupiedPositionsMap(boardWidth, boardHeight, pieceList);

        do {
            pieceList = Arrays.stream(pieces)
                    .boxed()
                    .map(ChessPuzzleSolver::readPiece)
                    .collect(Collectors.toList());

            boardController.setPieceList(pieceList);
            boardController.findChessCombination(new Position(0, 0), 0);
            pieceList.clear();
        } while (boardController.nextPermutation(pieces));

        long endTime = System.currentTimeMillis();

        System.out.println(boardController.getSolutionCount());

        final double totalTimeInSec = (endTime - startTime) / 1000.0d;
        System.out.println("Total time: " + totalTimeInSec + " seconds");
    }

    public static List<Piece> readPieces(final String input) {
        final String[] splittedInput = input.split(",");
        final List<Piece> pieceList = new ArrayList<>();

        Arrays.stream(splittedInput).forEach(s -> {
            final int numberOfPiece = s.charAt(0) - '0';
            final char pieceKeyChar = s.charAt(1);

            for (int i = 0; i < numberOfPiece; i++) {
                pieceList.add(readPiece(pieceKeyChar));
            }
        });

        return pieceList;
    }

    private static Piece readPiece(final int pieceKeyChar) {
        switch (pieceKeyChar) {
            case 'K':
            case King.ID:
                return new King();
            case 'N':
            case Knight.ID:
                return new Knight();
            case 'Q':
            case Queen.ID:
                return new Queen();
            case 'B':
            case Bishop.ID:
                return new Bishop();
            case 'R':
            case Rook.ID:
                return new Rook();
            default:
                return null;
        }
    }
}
