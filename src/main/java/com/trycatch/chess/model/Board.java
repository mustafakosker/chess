package com.trycatch.chess.model;

import com.trycatch.chess.game.BoardOccupyManager;
import com.trycatch.chess.model.piece.Piece;
import com.trycatch.chess.util.PositionUtil;

import static com.trycatch.chess.constants.CellStatus.*;

/**
 * Contains boardData and utility methods when traversing
 * the board.
 */
public class Board {
    private int[][] boardData;
    private int width;
    private int height;
    private int numberOfEmptyCells;

    /**
     * @param width width of the board
     * @param height height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.numberOfEmptyCells = width * height;

        this.boardData = new int[width][height];
    }

    /**
     * Checks if the board is available for the given position
     * to threat. Available to threat means there is no piece
     * in the given position.
     *
     * @param position position to check
     * @return boolean whether the given position is available to threat
     * or not.
     */
    public boolean isCellAvailableToThreat(Position position) {
        if (!PositionUtil.isPositionValid(position) ||
                boardData[position.getX()][position.getY()] != FILLED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the given position is suitable to place a piece
     * on it or not. It will get occupy positions from BoardOccupyManager
     * and check whether there is a piece on these positions or not.
     *
     * @param piece piece to place on the board
     * @param position position to place the piece
     * @return boolean value whether if it is appropriate to place a piece
     * on the given position or not.
     */
    public boolean isCellAvailableToPutPiece(Piece piece, Position position) {
        if (PositionUtil.isPositionValid(position) &&
                getCellStatus(position) == EMPTY) {
            return !pieceThreatsAnotherPiece(piece, position);
        } else {
            return false;
        }
    }

    /**
     * Checks whtether the piece placed on the position threats any other
     * piece on the board.
     *
     * @param piece piece to place on the board
     * @param position position of the piece
     * @return true if the piece threats another piece
     */
    private boolean pieceThreatsAnotherPiece(Piece piece, Position position) {
        return BoardOccupyManager
                    .getOccupiedPositionsList(piece.getID(), position)
                    .stream()
                    .filter(p -> !isCellAvailableToThreat(p))
                    .count() > 0;
    }

    /**
     * Sets the board data of the given position with given status.
     * It'll increase the value of OCCUPIED cells in order to unset
     * value of the board. For example a cell value 4 means given position
     * is being threatened by two pieces(2*OCCUPIED)
     * It will also decrease numberOfEmpty cells if a cell state changes from
     * EMPTY to FILLED or OCCUPIED
     *
     * @param position position to be set
     * @param cellStatus status of the cell. see {@link com.trycatch.chess.constants.CellStatus}
     */
    public boolean setCellStatus(Position position, int cellStatus) {
        if (PositionUtil.isPositionValid(position)) {
            final int x = position.getX();
            final int y = position.getY();

            if (cellStatus == OCCUPIED &&
                    (boardData[x][y] > 0)) {
                boardData[x][y] += OCCUPIED;
            } else {
                boardData[x][y] = cellStatus;
                this.numberOfEmptyCells--;
            }
            return true;
        }
        return false;
    }

    /**
     * Places the piece on the board by marking the
     * board data with OCCUPIED and FILLED.
     * Position of the piece will be marked as FILLED.
     * Positions occupied(threatened) by the piece
     * will be marked as OCCUPIED.
     *
     * @param piece piece to put on the board.
     *              {@link Piece#position should be set as well}
     */
    public void putPieceOnBoard(Piece piece) {
        BoardOccupyManager.getOccupiedPositionsList(piece.getID(), piece.getPosition())
                .stream()
                .filter(position -> !position.equals(piece.getPosition()))
                .forEach(p -> setCellStatus(p, OCCUPIED));
        setCellStatus(piece.getPosition(), FILLED);
    }

    /**
     * This method removes a piece from the board by
     * undoing the {@link #putPieceOnBoard(Piece)}
     * It will iterate through all the occupied positions and current position
     * of the piece and will call {@link #unsetCellStatus(Position, int)}
     *
     * @param piece piece to remove from the board
     */
    public void removePieceFromBoard(Piece piece) {
        BoardOccupyManager.getOccupiedPositionsList(piece.getID(), piece.getPosition())
                .stream()
                .forEach(p -> unsetCellStatus(p, OCCUPIED));
        unsetCellStatus(piece.getPosition(), FILLED);
    }

    /**
     * Unsets status of the cell. It will set the status
     * to EMPTY if one piece occupies the cell(that is cell value {@link com.trycatch.chess.constants.CellStatus#OCCUPIED})
     * or cell status is {@link com.trycatch.chess.constants.CellStatus#FILLED} and it will increase numberOfEmptyCells
     * by one to indicate there is one more space available to put piece on the board.
     *
     * If the position given being thretened(occupied) by two piece(that is cell value is 2 * OCCUPIED)
     * than it will just decrease the value of the cell.
     *
     * @param position
     * @param cellStatus
     */
    private void unsetCellStatus(Position position, int cellStatus) {
        if (!PositionUtil.isPositionValid(position)) {
            return;
        }
        final int x = position.getX();
        final int y = position.getY();

        if (cellStatus == OCCUPIED) {
            boardData[x][y] -= OCCUPIED;
        } else {
            boardData[x][y] = EMPTY;
        }

        if (boardData[x][y] == EMPTY) {
            numberOfEmptyCells++;
        }
    }

    /**
     *
     * @param position to get status from board
     * @return status of the cell. One of the values of {@link com.trycatch.chess.constants.CellStatus}
     */
    public int getCellStatus(Position position) {
        return boardData[position.getX()][position.getY()];
    }

    /**
     * Calculates and returns the closest empty cell on the board.
     *
     * @param position to start from
     * @return the empty position closest to given position.
     * It will return null if there is no available position in the board.
     */
    public Position getNextAvailablePosition(Position position) {
        Position nextPosition = PositionUtil.getNextPosition(position);

        for (int i = PositionUtil.getRawPosition(position); i < width * height; i++) {
            if (!PositionUtil.isPositionValid(nextPosition)) {
                return null;
            }
            if (getCellStatus(nextPosition) == EMPTY) {
                return nextPosition;
            }
            nextPosition = PositionUtil.getNextPosition(nextPosition);
        }


        return null;
    }

    /**
     * Getter method for tracking number of empty cells on the board.
     *
     * @return numberOfEmptyCells in the board
     */
    public int getNumberOfEmptyCells() {
        return numberOfEmptyCells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        String separator = ",";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < boardData.length; ++i)
        {
            result.append("[");
            for (int j = 0; j < boardData[i].length; ++j)
                if (j > 0)
                    result.append(boardData[j][i]).append(separator);
                else
                    result.append(boardData[j][i]).append(separator);
            result.append("]\n");
        }

        return result.toString();
    }
}
