package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kosker on 04/07/16.
 */
public class Rook implements Piece {
    public static final int ID = 5;

    private Position position;
    private static List<Position> verticalOccupiedPositions;
    private static List<Position> horizontalOccupiedPositions;

    public Rook(int boardWidth, int boardHeight) {
        if (verticalOccupiedPositions == null) {
            verticalOccupiedPositions = calculateVerticalOccupiedPositions(boardHeight);
        }
        if (horizontalOccupiedPositions == null) {
            horizontalOccupiedPositions = calculateHorizontalOccupiedPositions(boardWidth);
        }
    }

    private static List<Position> calculateVerticalOccupiedPositions(int boardHeight) {
        final List<Position> positionList = new ArrayList<>();
        for (int i = 0; i < boardHeight; i++) {
            positionList.add(new Position(0, i));
        }
        return positionList;
    }

    private static List<Position> calculateHorizontalOccupiedPositions(int boardWidth) {
        final List<Position> positionList = new ArrayList<>();
        for (int i = 0; i < boardWidth; i++) {
            positionList.add(new Position(i, 0));
        }
        return positionList;
    }


    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return true;
    }

    @Override
    public boolean occupiesDiagonal() {
        return false;
    }

    @Override
    public List<Position> getOccupiedPositionsList(Position position) {
        if (occupiesVerticalAndHorizontal()) {
            final List<Position> occupiedPositions =
                    verticalOccupiedPositions.stream()
                    .map(p -> p.addReferencePositionX(position.getX()))
                    .filter(p -> !p.equals(position))
                    .collect(Collectors.toList());
            occupiedPositions.addAll(
                    horizontalOccupiedPositions.stream()
                            .map(p -> p.addReferencePositionY(position.getY()))
                            .filter(p -> !p.equals(position))
                            .collect(Collectors.toList())
            );

            return occupiedPositions;
        }
        return null;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Rook{" +
                "position=" + position +
                '}';
    }
}
