package com.trycatch.chess.model.piece;

/**
 * Queen will move horizontally, vertically and diagonally,
 * therefore it will override {@link #occupiesVerticalAndHorizontal()} and {@link #occupiesDiagonal()}
 */
public class Queen extends Piece {
    public static final int ID = 4;

    /**
     * {@link Piece#occupiesVerticalAndHorizontal()}
     */
    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return true;
    }

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
        return ID;
    }

    @Override
    public String toString() {
        return "Queen{" +
                "position=" + getPosition() +
                "}";
    }
}
