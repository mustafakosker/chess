package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Position;

import java.util.List;

/**
 * Interface for all chess pieces.
 */
public interface Piece {
    boolean occupiesVerticalAndHorizontal();

    boolean occupiesDiagonal();

    List<Position> getOccupiedPositionsList();
}
