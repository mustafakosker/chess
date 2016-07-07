package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.Arrays;
import java.util.List;

/**
 * Knight will have relative occupied positions list,
 * therefore it will override {@link #getRelativeOccupiedPositionsList()}
 */
public class Knight extends Piece {
    public static final int ID = 3;

    private static Position[] occupiedPositions = {
            new Position(-1, -2),
            new Position(1, -2),
            new Position(2, -1),
            new Position(2, 1),
            new Position(1, 2),
            new Position(-1, 2),
            new Position(-2, 1),
            new Position(-2, -1)
    };

    private static List<Position> occupiedPositionList = Arrays.asList(occupiedPositions);

    /**
     * {@link Piece#getRelativeOccupiedPositionsList()}
     */
    @Override
    public List<Position> getRelativeOccupiedPositionsList() {
        return occupiedPositionList;
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
        return "Knight{" + getPosition() +
                "}";
    }
}
