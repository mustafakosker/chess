package com.trycatch.chess.game;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.Piece;
import com.trycatch.chess.util.PositionUtil;

import java.util.List;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardController {
    private List<Piece> pieceList;
    private Board board;
    private int solutionCount;

    public BoardController(Board board) {
        this.board = board;
        this.solutionCount = 0;
    }

    private String solutionToString(List<Piece> pieceList) {
        final StringBuilder solution = new StringBuilder("Solution " + solutionCount).append("\n");
        pieceList.stream().forEach(piece -> solution.append(piece.toString()).append("\n"));

        return solution.toString();
    }

    /**
     * Recursive function for finding all the solutions. It will start from
     * the first position(0,0) of the board with the first element in the {@link #pieceList}
     * and it will try to place the rest of the pieces after first piece. If the position given
     * is being threatened by another piece or it is filled, than it will continue with the next
     * available position to try to place the piece.
     * If it finds a position to place on the board for the given piece by pieceIndex, it will continue
     * to place rest of the pieces to empty cells on the board starting from the last positioned piece.
     *
     *
     * @param position
     * @param pieceIndex
     */
    public void findChessCombination(Position position, int pieceIndex) {
        // Exit condition for the method. It means that if there is no empty cells on the board
        // or given position is null(meaning there is no available next position on the board)
        // and we have not placed all the pieces to the board, it means that it cannot find the
        // solution for this placement and it can continue with the next position of the piece
        if ((board.getNumberOfEmptyCells() <= 0 ||
                position == null ||
                PositionUtil.isPositionAfterThanLastCell(position)) &&
                pieceIndex < pieceList.size()) {
            return;
        } else if (pieceIndex >= pieceList.size()) { // This means that we have found a solution
            // To list all the positions of the found solution, uncomment next line
            // System.out.println(solutionToString(pieceList));
            solutionCount++;
        } else {
            // Next available position of the board relative to given position
            // It will start from upper left corner and will continue to right, then first cell on the next row
            final Position newPosition = board.getNextAvailablePosition(position);
            final Piece piece = pieceList.get(pieceIndex);

            // If we can place the current piece to current position
            // then we can continue placing other pieces
            if (board.isCellAvailableToPutPiece(piece, position)) {
                piece.setPosition(position);
                board.putPieceOnBoard(piece);

                findChessCombination(position, pieceIndex + 1);

                board.removePieceFromBoard(piece);
                piece.setPosition(null);
            }
            // Continue placing the current piece to next position
            findChessCombination(newPosition, pieceIndex);
        }
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    public int getSolutionCount() {
        return solutionCount;
    }
}
