package com.trycatch.chess.util;

import com.trycatch.chess.model.Position;

/**
 * Created by kosker on 07/07/16.
 */
public final class PositionUtil {
    private static int boardWidth;
    private static int boardHeight;

    private PositionUtil() {
    }

    public static void init(int width, int height) {
        boardWidth = width;
        boardHeight = height;
    }

    /**
     * Checks whether the given position inside the borders
     * of the board
     *
     * @param position position to be checked
     * @return true if the position is valid, false otherwise
     */
    public static boolean isPositionValid(Position position) {
        final int x = position.getX();
        final int y = position.getY();

        return (x < boardWidth) && (y < boardHeight) && (x >= 0) && (y >= 0);
    }

    public static boolean isPositionAfterThanLastCell(Position position) {
        return ((position.getY() == (boardHeight - 1)) && (position.getX() >= boardWidth))
                || (position.getY() >= boardHeight);
    }

    public static int getRawPosition(Position position) {
        return position.getY() * boardWidth + position.getX();
    }

    /**
     * Calculates the next cell without looking to borders,
     * or the cell is empty or not.
     *
     * @param position reference position
     * @return next position in the board. It might return invalid position as well.
     * Might be checked with {{@link com.trycatch.chess.util.PositionUtil#isPositionValid(Position)}}
     */
    public static Position getNextPosition(Position position) {
        int rawPosition = getRawPosition(position);
        rawPosition++;

        return new Position(rawPosition % boardWidth, rawPosition / boardWidth);
    }


}
