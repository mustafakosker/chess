package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kosker on 04/07/16.
 */
public class Knight implements Piece {
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

    private Position position;

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    @Override
    public boolean occupiesDiagonal() {
        return false;
    }

    @Override
    public List<Position> getRelativeOccupiedPositionsList() {
        return occupiedPositionList;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }



    @Override
    public String toString() {
        return "Knight{" +
                "position=" + position +
                '}';
    }
}
