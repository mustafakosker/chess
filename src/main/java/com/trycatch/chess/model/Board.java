package com.trycatch.chess.model;

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
     * @return two dimenson array containing board data
     */
    public int[][] getBoardData() {
        return boardData;
    }

    /**
     * Overloaded method of {@link #setCellStatus(Position, int)}
     *
     * @param x
     * @param y
     * @param cellStatus
     * @return
     */
    public boolean setCellStatus(int x, int y, int cellStatus) {
        return setCellStatus(new Position(x, y), cellStatus);
    }

    /**
     * This method will try to set the specified position cell
     * to given cell status.
     *
     * @param position position of the cell to be set.
     * @param cellStatus status of the cell to be set.
     *                   See {@link com.trycatch.chess.constants.CellStatus}
     * @return false if the position is outside of the board, otherwise true.
     */
    public boolean setCellStatus(Position position, int cellStatus) {
        final int x = position.getX();
        final int y = position.getY();

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        } else {
          boardData[x][y] = cellStatus;
          return true;
        }
    }
}
