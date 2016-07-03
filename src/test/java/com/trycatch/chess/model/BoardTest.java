package com.trycatch.chess.model;

import org.junit.Test;

import static com.trycatch.chess.constants.CellStatus.OCCUPIED;
import static org.junit.Assert.*;

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

    @Test
    public void getNextAvailablePositionTestNegativePosition() throws Exception {
        final Position position = new Position(-1, -1);
        final Board board = new Board(2, 2);

        assertNull("Next available position to invalid position should be null",
                board.getNextAvailablePosition(position));
    }

    @Test
    public void getNextAvailablePositionTestEdgePosition() throws Exception {
        final Position position = new Position(3, 3);
        final Board board = new Board(3, 3);

        assertNull("Next available position to out of board position should be null",
                board.getNextAvailablePosition(position));
    }

    @Test
    public void getNextAvailablePositionTestEmptyBoard() throws Exception {
        final Position position = new Position(0, 0);
        final Board board = new Board(2, 2);

        assertEquals("Next available position to an empty position should be itself", position,
                board.getNextAvailablePosition(position));
    }

    @Test
    public void getNextAvailablePositionTestOccupiedCell() throws Exception {
        final Position position = new Position(1, 2);
        final Board board = new Board(3, 3);
        board.setCellStatus(position, OCCUPIED);

        assertEquals("Next available position to occupied cell should be adjecent cell", new Position(2, 2),
                board.getNextAvailablePosition(position));
    }

    @Test
    public void getNextAvailablePositionTestOccupiedCellHorizontalLastCell() throws Exception {
        final Position position = new Position(2, 1);
        final Board board = new Board(3, 3);
        board.setCellStatus(position, OCCUPIED);

        assertEquals("Next available position to occupied cell in the last horizontal cell should be the first cell in" +
                "the next row", new Position(0, 2), board.getNextAvailablePosition(position));
    }
}
