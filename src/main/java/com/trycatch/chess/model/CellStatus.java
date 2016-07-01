package com.trycatch.chess.model;

/**
 * Enum class indicating the status of the cell.
 *
 * EMPTY means the cell is empty and any kind of piece
 *  can be placed in it.
 *
 * OCCUPIED means the cell is threatened by another piece
 *  and nothing can be placed in it.
 *
 * FILLED also means there is a piece in the cell.
 */
public enum CellStatus {
    EMPTY,
    OCCUPIED,
    FILLED
}
