package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosker on 05/07/16.
 */
public class Queen implements Piece {
    public static final int ID = 4;

    private Position position;

    @Override
    public boolean occupiesVerticalAndHorizontal() {
        return true;
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
        return "Queen{" +
                "position=" + position +
                '}';
    }
}
