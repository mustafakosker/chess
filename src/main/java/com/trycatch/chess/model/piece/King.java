package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of King piece.
 */
public class King implements Piece {
    private Position position;

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

    public King() {
        this.position = null;
    }

    public List<Position> getOccupiedPositionsList() {
        return occupiedPositionsList;
    }

    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    public boolean occupiesDiagonal() {
        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "King{" +
                "position=" + position +
                '}';
    }
}
