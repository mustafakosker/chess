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
}
