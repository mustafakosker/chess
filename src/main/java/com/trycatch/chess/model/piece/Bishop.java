package com.trycatch.chess.model.piece;

/**
 * Bishop will occupy the board diagonally,
 * therefore it will override {@link #occupiesDiagonal()}
 */
public class Bishop extends Piece {
    public static final int ID = 2;

    /**
     * {@link Piece#occupiesDiagonal()}
     */
    @Override
    public boolean occupiesDiagonal() {
        return true;
    }

    /**
     * {@link Piece#getID()}
     */
    @Override
    public int getID() {
        return Bishop.ID;
    }

    @Override
    public String toString() {
        return "Bishop{" +
                "position=" + getPosition() +
                '}';
    }
}
