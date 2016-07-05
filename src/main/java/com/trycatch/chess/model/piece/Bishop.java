package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kosker on 04/07/16.
 */
public class Bishop implements Piece {
    public static final int ID = 2;

    private Position position;
    private static Position[] occupiedDirections = {
        new Position(-1, -1),
        new Position(-1, 1),
        new Position(1, 1),
        new Position(1, -1)
    };

    private static List<Position> occupiedDirectionPositionsList = Arrays.asList(occupiedDirections);
    private static List<Position> occupiedPositionsList;

    public Bishop(final int boardWidth, final int boardHeight) {
        final int maxBoardDimension = Math.max(boardWidth, boardHeight);

        if (occupiedPositionsList == null) {
            occupiedPositionsList = calculateOccupiedPositionsList(maxBoardDimension);
        }
    }

    private List<Position> calculateOccupiedPositionsList(final int maxBoardDimension) {
        final List<Position> occupiedPositionsList = new ArrayList<>();

        for (int i = 1; i < maxBoardDimension; i++) {
            final int multiplier = i;
            occupiedDirectionPositionsList
                    .forEach(p ->
                            occupiedPositionsList.add(new Position(p.getX() * multiplier, p.getY() * multiplier)));
        }

        return occupiedPositionsList;
    }

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    @Override
    public boolean occupiesDiagonal() {
        return true;
    }

    @Override
    public List<Position> getOccupiedPositionsList(Position position) {
        return occupiedPositionsList
                .stream()
                .map(p -> p.addPosition(position))
                .collect(Collectors.toList());
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
        return "Bishop{" +
                "position=" + position +
                '}';
    }
}
