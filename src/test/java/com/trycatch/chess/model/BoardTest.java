package com.trycatch.chess.model;

import org.junit.Test;

import static com.trycatch.chess.constants.CellStatus.OCCUPIED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardTest {
    @Test
    public void isCellAvailableTestInvalidPosition() throws Exception {
        final Position position = new Position(-1, 0);
        final Board board = new Board(2, 2);
        
        assertFalse("Invalid position should not be available", board.isCellAvailable(position));
    }

    @Test
    public void isCellAvailableTestOccupiedCell() throws Exception {
        final Position position = new Position(0, 0);
        final Board board = new Board(2, 2);
        board.setCellStatus(position, OCCUPIED);

        assertFalse("OCCUPIED cell should not be available", board.isCellAvailable(position));
    }

    @Test
    public void isCellAvailableTestEmptyCell() throws Exception {
        final Position position = new Position(2, 2);
        final Board board = new Board(3, 3);

        assertTrue("EMPTY cell should be available", board.isCellAvailable(position));
    }
}
