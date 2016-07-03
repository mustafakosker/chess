package com.trycatch.chess.model;

import static com.trycatch.chess.constants.CellStatus.EMPTY;

/**
 * Contains boardData and utility methods when traversing
 * the board.
 */
public class Board {
    private int[][] boardData;
    private int width;
    private int height;

    /**
     * @param width width of the board
     * @param height height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        this.boardData = new int[width][height];
    }

    /**
     * Checks if the board is available for the given position
     * to set.
     *
     * @param position position to check
     * @return boolean whether the given position is available to place
     *  a piece or not
     */
    public boolean isCellAvailable(Position position) {
        final int x = position.getX();
        final int y = position.getY();

        if (x >= width || y >= height || x < 0 || y < 0 ||
                boardData[x][y] != EMPTY) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the board data of the given position with given status.
     *
     * @param position position to be set
     * @param cellStatus status of the cell. see {@link com.trycatch.chess.constants.CellStatus}
     */
    public void setCellStatus(Position position, int cellStatus) {
        boardData[position.getX()][position.getY()] = cellStatus;
    }
}
