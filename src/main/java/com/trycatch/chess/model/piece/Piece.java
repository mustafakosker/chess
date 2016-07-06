package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all chess pieces.
 */
public abstract class Piece {
    /**
     * Position of the piece on the board.
     */
    private Position position;

    /**
     * @return position of the piece.
     * Returns null if no position is set before.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @param position position to be set of the piece.
     *                 null value will be populated to unset the position of the piece.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * This method will indicate that piece occupies
     * the all the cells on the same row and column.
     * E.g. Rook
     *
     * Override this method to change default value.
     *
     * @return false for default value.
     */
    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    /**
     * This method will indicate that piece occupies
     * the all diagonal cells on the board.
     * E.g. Bishop
     *
     * Override this method to change default value.
     *
     * @return false for default value.
     */
    public boolean occupiesDiagonal() {
        return false;
    }

    /**
     * Return list of relative occupied positions list
     * for the piece.
     *
     * Override this method to return relative occupied positions
     * of a piece type.
     *
     * @return empty positions list as default value.
     */
    public List<Position> getRelativeOccupiedPositionsList() {
        return new ArrayList<>();
    }

    /**
     * @return integer ID value of the piece type. Each piece type
     * will have unique ID.
     */
    public abstract int getID();
}
