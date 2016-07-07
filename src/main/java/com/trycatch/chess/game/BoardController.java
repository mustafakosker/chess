package com.trycatch.chess.game;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.Piece;

import java.io.IOException;
import java.util.List;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardController {
    private List<Piece> pieceList;
    private Board board;
    private long solutionCount;

    public BoardController(Board board) throws IOException {
        this.board = board;
        this.solutionCount = 0;

        BoardOccupyManager.createOccupiedPositionsMap(board.getWidth(), board.getHeight());
    }

    private String solutionToString(List<Piece> pieceList) {
        final StringBuilder solution = new StringBuilder("Solution " + solutionCount).append("\n");
        pieceList.stream().forEach(piece -> solution.append(piece.toString()).append("\n"));

        return solution.toString();
    }

    public void findChessCombination(Position position, int pieceIndex) throws IOException {
        if ((board.getNumberOfEmptyCells() <= 0 ||
                position == null ||
                board.isPositionAfterThanLastCell(position)) &&
                pieceIndex < pieceList.size()) {
            return;
        } else if (pieceIndex >= pieceList.size()) {
            solutionCount++;
        } else {
            final Position newPosition = board.getNextAvailablePosition(position);
            final Piece piece = pieceList.get(pieceIndex);

            if (board.isCellAvailableToPutPiece(piece, position)) {
                piece.setPosition(position);
                board.putPieceOnBoard(piece);

                findChessCombination(position, pieceIndex + 1);

                board.removePieceFromBoard(piece);
                piece.setPosition(null);
            }
            findChessCombination(newPosition, pieceIndex);
        }
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    public long getSolutionCount() {
        return solutionCount;
    }

    /**
     * This code is taken from: https://www.nayuki.io/page/next-lexicographical-permutation-algorithm
     * Calculates all permutations of the given array.
     *
     * @param array to be processed for permutation
     * @return next permutation of given rray
     */
    public boolean nextPermutation(int[] array) {
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
}
