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

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return false;
    }

    @Override
    public boolean occupiesDiagonal() {
        return true;
    }

    @Override
    public List<Position> getRelativeOccupiedPositionsList() {
        return new ArrayList<>();
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
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Bishop{" +
                "position=" + position +
                '}';
    }
}
