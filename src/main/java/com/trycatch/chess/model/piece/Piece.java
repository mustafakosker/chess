package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;

/**
 * Interface for all chess pieces.
 */
public interface Piece {

    /**
     * Marks the board data to indicate
     * occupied(threatening) and filled
     * cells.
     *
     * @param position position of the piece to be set
     * @param board
     */
    void occupyBoard(Position position, Board board);
}
