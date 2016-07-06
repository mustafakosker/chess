package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.Arrays;
import java.util.List;

/**
 * King will have relative occupied positions list,
 * therefore it will override {@link #getRelativeOccupiedPositionsList()}
 */
public class King extends Piece {
    public static final int ID = 1;

    private static Position[] occupiedPositions = {
            new Position(-1, -1),
            new Position(-1, 0),
            new Position(-1, 1),
            new Position(0, -1),
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 0),
            new Position(1, -1)
    };

    private static List<Position> occupiedPositionsList = Arrays.asList(occupiedPositions);

    /**
     * {@link Piece#getRelativeOccupiedPositionsList()}
     */
    @Override
    public List<Position> getRelativeOccupiedPositionsList() {
        return occupiedPositionsList;
    }

    /**
     * {@link Piece#getID()}
     */
    @Override
    public int getID() {
        return King.ID;
    }

    @Override
    public String toString() {
        return "King{" +
                "position=" + getPosition() +
                "}";
    }
}
