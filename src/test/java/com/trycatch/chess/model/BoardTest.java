package com.trycatch.chess.model;

import com.trycatch.chess.model.piece.King;
import com.trycatch.chess.util.PositionUtil;
import org.junit.Test;

import static com.trycatch.chess.constants.CellStatus.EMPTY;
import static com.trycatch.chess.constants.CellStatus.FILLED;
import static com.trycatch.chess.constants.CellStatus.OCCUPIED;
import static org.junit.Assert.*;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardTest {
    @Test
    public void isCellAvailableToThreatTestInvalidPosition() throws Exception {
        final Position position = new Position(-1, 0);
        final Board board = new Board(2, 2);
        
        assertTrue("Invalid position should be available", board.isCellAvailableToThreat(position));
    }

    @Test
    public void isCellAvailableToThreatTestOccupiedCell() throws Exception {
        final Position position = new Position(0, 0);
        final Board board = new Board(2, 2);
        board.setCellStatus(position, OCCUPIED);

        assertTrue("OCCUPIED cell should be available", board.isCellAvailableToThreat(position));
    }

    @Test
    public void isCellAvailableToThreatTestEmptyCell() throws Exception {
        final Position position = new Position(2, 2);
        final Board board = new Board(3, 3);

        assertTrue("EMPTY cell should be available", board.isCellAvailableToThreat(position));
    }

    @Test
    public void isCellAvailableToPutPieceOccupiedCell() throws Exception {
        final Position position = new Position(1, 1);
        final Board board = new Board(2, 2);
        board.setCellStatus(position, OCCUPIED);

        assertFalse("Occupied cell will be unavailable to place a piece on it",
                board.isCellAvailableToPutPiece(new King(), position));
    }

}
