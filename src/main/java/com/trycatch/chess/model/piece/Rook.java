package com.trycatch.chess.model.piece;

/**
 * Rook will move horizontally and vertically,
 * therefore it will override {@link #occupiesVerticalAndHorizontal()}
 */
public class Rook extends Piece {
    public static final int ID = 5;

    /**
     * {@link Piece#occupiesVerticalAndHorizontal()}
     */
    @Override
    public boolean occupiesVerticalAndHorizontal() {
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
        return "Rook{" + getPosition() +
                "}";
    }
}
