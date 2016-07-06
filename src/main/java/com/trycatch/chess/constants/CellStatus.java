package com.trycatch.chess.constants;

/**
 * Indicates the status of an each cell in the board.
 */
public final class CellStatus {

    /** EMPTY means the cell is empty and any kind of piece
     *  can be placed in it. */
    public static final int EMPTY = 0;

    /**
     * FILLED means there is a piece in the cell.
     */
    public static final int FILLED = 1;

    /**
     * OCCUPIED means the cell is threatened by another piece
     * and nothing can be placed in it.
     */
    public static final int OCCUPIED = 2;
}
