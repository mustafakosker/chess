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

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return true;
    }

    @Override
    public boolean occupiesDiagonal() {
        return false;
    }

    @Override
    public List<Position> getRelativeOccupiedPositionsList() {
        return new ArrayList<>();
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
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Rook{" +
                "position=" + position +
                '}';
    }
}
