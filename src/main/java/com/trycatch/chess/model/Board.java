package com.trycatch.chess.model;

import com.trycatch.chess.game.BoardOccupyManager;
import com.trycatch.chess.model.piece.Piece;

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
        if (!isPositionValid(position) ||
                boardData[position.getX()][position.getY()] != FILLED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the given position is suitable to place a piece
     * on it or not. It will calculate the transformed thread positions
     * and check whether there is a piece on these positions or not.
     *
     * @param piece piece to place on the board
     * @param position position to place the piece
     * @return boolean value whether if it is appropriate to place a piece
     * on the given position or not.
     */
    public boolean isCellAvailableToPutPiece(Piece piece, Position position) {
        if (isPositionValid(position) &&
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
     * value of the board.
     *
     * @param position position to be set
     * @param cellStatus status of the cell. see {@link com.trycatch.chess.constants.CellStatus}
     */
    public boolean setCellStatus(Position position, int cellStatus) {
        if (isPositionValid(position)) {
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

    public void putPieceOnBoard(Piece piece) {
        // TODO reuse transformed list

        BoardOccupyManager.getOccupiedPositionsList(piece.getID(), piece.getPosition())
                .stream()
                .filter(position -> !position.equals(piece.getPosition()))
                .forEach(p -> setCellStatus(p, OCCUPIED));
        setCellStatus(piece.getPosition(), FILLED);
    }

    public void removePieceFromBoard(Piece piece) {
        // TODO reuse transformed list

        BoardOccupyManager.getOccupiedPositionsList(piece.getID(), piece.getPosition())
                .stream()
                .forEach(p -> unsetCellStatus(p, OCCUPIED));
        unsetCellStatus(piece.getPosition(), FILLED);
    }

    private void unsetCellStatus(Position position, int cellStatus) {
        if (!isPositionValid(position)) {
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
        Position nextPosition = getNextPosition(position);

        for (int i = getRawPosition(position); i < getTotalNumberOfCells(); i++) {
            if (!isPositionValid(nextPosition)) {
                return null;
            }
            if (getCellStatus(nextPosition) == EMPTY) {
                return nextPosition;
            }
            nextPosition = getNextPosition(nextPosition);
        }


        return null;
    }

    private int getTotalNumberOfCells() {
        return width * height;
    }

    private int getRawPosition(Position position) {
        return position.getY() * width + position.getX();
    }

    private int getTransformedY(int rawPosition) {
        return rawPosition / width;
    }

    private int getTransformedX(int rawPosition) {
        return rawPosition % width;
    }

    /**
     * Calculates the next cell without looking to borders,
     * or the cell is empty or not.
     *
     * @param position reference position
     * @return next position in the board. It might return invalid position as well.
     * Might be checked with {{@link #isPositionValid(Position)}}
     */
    public Position getNextPosition(Position position) {
        int rawPosition = getRawPosition(position);
        rawPosition++;

        return new Position(getTransformedX(rawPosition), getTransformedY(rawPosition));
    }

    /**
     * Getter method for tracking number of empty cells on the board.
     *
     * @return numberOfEmptyCells in the board
     */
    public int getNumberOfEmptyCells() {
        return numberOfEmptyCells;
    }

    /**
     * Checks whether the given position inside the borders
     * of the board
     *
     * @param position position to be checked
     * @return true if the position is valid, false otherwise
     */
    private boolean isPositionValid(Position position) {
        final int x = position.getX();
        final int y = position.getY();

        return (x < width) && (y < height) && (x >= 0) && (y >= 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPositionAfterThanLastCell(Position position) {
        return ((position.getY() == (height - 1)) && (position.getX() >= width))
                || (position.getY() >= height);
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
