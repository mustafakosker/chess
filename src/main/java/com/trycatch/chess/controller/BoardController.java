package com.trycatch.chess.controller;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardController {
    private List<Piece> pieceList;
    private Board board;
    private List<String> solutionBoard;

    public BoardController(Board board) {
        this.board = board;
        this.solutionBoard = new ArrayList<>();
    }

    public List<String> getSolutionBoard() {
        return solutionBoard;
    }

    public void findChessCombination(Position position, int pieceIndex) {
        if ((board.getNumberOfEmptyCells() <= 0 ||
                position == null ||
                board.isPositionAfterThanLastCell(position)) &&
                pieceIndex < pieceList.size()) {
            return;
        } else if (pieceIndex >= pieceList.size()) {
            final StringBuilder solution = new StringBuilder("Solution " + solutionBoard.size()).append("\n");

            pieceList.stream().forEach(piece -> solution.append(piece.toString()).append("\n"));
            solution.append(board);

            solutionBoard.add(solution.toString());
        } else {
            final Position newPosition = board.getNextAvailablePosition(position);
            final Piece piece = pieceList.get(pieceIndex);

            if (board.isCellAvailableToPutPiece(piece, position)) {
                piece.setPosition(position);
                board.putPieceOnBoard(piece);

                findChessCombination(position, pieceIndex+1);

                board.removePieceFromBoard(piece);
                piece.setPosition(null);
            }
            findChessCombination(newPosition, pieceIndex);
        }
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    private static boolean nextPermutation(int[] array) {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
            i--;
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0)
            return false;

        // Let array[i - 1] be the pivot
        // Find rightmost element that exceeds the pivot
        int j = array.length - 1;
        while (array[j] <= array[i - 1])
            j--;
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }

        // Successfully computed the next permutation
        return true;
    }

    public static void main(String[] args) {
        int[] pieces = {King.ID, King.ID, Bishop.ID, Bishop.ID, Knight.ID, Queen.ID, Queen.ID};
        BoardController boardController = new BoardController(new Board(7, 7));

        int k = 0;
        do {
            List<Piece> pieceList = Arrays.stream(pieces).boxed()
                    .map(i -> {
                        if (i == Rook.ID) {
                            return new Rook(7, 7);
                        } else if (i == Knight.ID) {
                            return new Knight();
                        } else if(i == Bishop.ID) {
                            return new Bishop(7, 7);
                        } else if(i == Queen.ID) {
                            return new Queen(7,7);
                        } else {
                            return new King();
                        }
                    }).collect(Collectors.toList());

            System.out.println(k++);
            boardController.setPieceList(pieceList);
            boardController.findChessCombination(new Position(0, 0), 0);

            pieceList.clear();
        } while (nextPermutation(pieces));

        boardController.getSolutionBoard().stream().forEach(System.out::println);
    }
}
