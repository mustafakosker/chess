package com.trycatch.chess.model;

/**
 * Interface for all chess pieces.
 */
public interface Piece {

    /**
     * Marks the board data to indicate
     * occupied(threatening) and filled
     * cells.
     *
     * @param boardData
     */
    void occupyBoard(int[][] boardData);
}
