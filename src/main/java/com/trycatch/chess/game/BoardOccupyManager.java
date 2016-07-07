package com.trycatch.chess.game;

import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This final class will be used as cache to hold
 * all the occupied positions of given piece
 * at specific position on the board. It will iterate
 * through all the cells on the board for all pieces.
 */
public final class BoardOccupyManager {
    private static final Map<Integer, Map<Position, List<Position>>> occupyBoardPositionsMap = new HashMap<>();

    private BoardOccupyManager() {
    }

    public static void createOccupiedPositionsMap(int boardWidth, int boardHeight, List<Piece> pieceList) {
        final List<Position> diagonalOccupiedPositionsList =
                calculateDiagonalOccupiedPositionsList(Math.max(boardWidth, boardHeight));

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                final Position currentPosition = new Position(j, i);

                pieceList.forEach(piece -> {
                    List<Position> occupiedPositionsList =
                            calculateAbsoluteOccupiedPositions(piece.getRelativeOccupiedPositionsList(), currentPosition);

                    if (piece.occupiesDiagonal()) {
                        occupiedPositionsList.addAll(
                                calculateAbsoluteOccupiedPositions(diagonalOccupiedPositionsList, currentPosition));
                    }
                    if (piece.occupiesVerticalAndHorizontal()) {
                        occupiedPositionsList
                                .addAll(calculateVerticalOccupiedPositionsList(boardHeight, currentPosition.getX()));
                        occupiedPositionsList
                                .addAll(calculateHorizontalOccupiedPositionList(boardWidth, currentPosition.getY()));
                        occupiedPositionsList = occupiedPositionsList.stream()
                                .filter(p -> !p.equals(currentPosition))
                                .collect(Collectors.toList());
                    }

                    Map<Position, List<Position>> pieceOccupiedPositionsMap = occupyBoardPositionsMap.get(piece.getID());

                    if (pieceOccupiedPositionsMap == null) {
                        pieceOccupiedPositionsMap = new HashMap<>();
                    }
                    pieceOccupiedPositionsMap.put(currentPosition, occupiedPositionsList);

                    occupyBoardPositionsMap.put(piece.getID(), pieceOccupiedPositionsMap);
                });
            }
        }
    }

    private static List<Position> calculateAbsoluteOccupiedPositions(final List<Position> relativeOccupiedPositions,
                                                                     Position currentPosition) {
        return relativeOccupiedPositions
                .stream()
                .filter(p -> !p.equals(new Position(0, 0)))
                .map(p -> p.addPosition(currentPosition))
                .collect(Collectors.toList());
    }

    private static List<Position> calculateVerticalOccupiedPositionsList(final int boardHeight, int x) {
        final List<Position> positionList = new ArrayList<>();
        for (int i = 0; i < boardHeight; i++) {
            positionList.add(new Position(x, i));
        }
        return positionList;
    }

    private static List<Position> calculateHorizontalOccupiedPositionList(final int boardWidth, int y) {
        final List<Position> positionList = new ArrayList<>();
        for (int i = 0; i < boardWidth; i++) {
            positionList.add(new Position(i, y));
        }
        return positionList;
    }

    private static List<Position> calculateDiagonalOccupiedPositionsList(final int maxBoardDimension) {
        final Position[] diagonalOccupiedDirections = {
                new Position(-1, -1),
                new Position(-1, 1),
                new Position(1, 1),
                new Position(1, -1)
        };
        final List<Position> diagonalOccupiedDirectionList = Arrays.asList(diagonalOccupiedDirections);
        final List<Position> occupiedPositionsList = new ArrayList<>();

        for (int i = 1; i < maxBoardDimension; i++) {
            final int multiplier = i;
            diagonalOccupiedDirectionList
                    .forEach(p ->
                            occupiedPositionsList.add(new Position(p.getX() * multiplier, p.getY() * multiplier)));
        }

        return occupiedPositionsList;
    }


    public static List<Position> getOccupiedPositionsList(int id, Position position) {
        return occupyBoardPositionsMap.get(id).get(position);
    }
}
