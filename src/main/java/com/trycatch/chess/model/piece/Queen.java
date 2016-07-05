package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kosker on 05/07/16.
 */
public class Queen implements Piece {
    private Bishop bishop;
    private Rook rook;
    private Position position;

    public Queen(int boardWidth, int boardHeight) {
        bishop = new Bishop(boardWidth, boardHeight);
        rook = new Rook(boardWidth, boardHeight);
    }

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    @Override
    public boolean occupiesDiagonal() {
        return false;
    }

    @Override
    public List<Position> getOccupiedPositionsList(Position position) {
        return Stream.concat(bishop.getOccupiedPositionsList(position).stream(),
                rook.getOccupiedPositionsList(position).stream())
                .filter(p -> !p.equals(position))
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
        return "Queen{" +
                "position=" + position +
                '}';
    }
}
