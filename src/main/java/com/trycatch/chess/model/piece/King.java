package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import static com.trycatch.chess.constants.CellStatus.OCCUPIED;
import static com.trycatch.chess.constants.CellStatus.FILLED;

/**
 * Implementation of King piece.
 */
public class King implements Piece {
    private Position position;

    public King() {
        this.position = null;
    }

    public void occupyBoard(Position p, Board board) {
        // TODO: add assertion here.

        final int x = p.getX();
        final int y = p.getY();

        board.setCellStatus(p, FILLED);
        board.setCellStatus(x + 1, y, OCCUPIED);
        board.setCellStatus(x, y + 1, OCCUPIED);
        board.setCellStatus(x + 1, y + 1, OCCUPIED);
        board.setCellStatus(x - 1, y, OCCUPIED);
        board.setCellStatus(x, y - 1, OCCUPIED);
        board.setCellStatus(x - 1, y - 1, OCCUPIED);
        board.setCellStatus(x - 1, y + 1, OCCUPIED);
        board.setCellStatus(x + 1, y - 1, OCCUPIED);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
