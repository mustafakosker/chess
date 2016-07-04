package com.trycatch.chess.model;

import com.trycatch.chess.model.piece.King;
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
    public void isCellAvailableToThreatFilledCell() throws Exception {
        final Position position = new Position(1,1);
        final Board board = new Board(2,2);
        board.setCellStatus(position, FILLED);

        assertFalse("FILLED cell should not be available", board.isCellAvailableToThreat(position));
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

        assertEquals("Next available position to an empty position should be adjecent cell", board.getNextPosition(position),
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

    @Test
    public void getNextPositionTest() throws Exception {
        final Position position  = new Position(3, 3);
        final Board board = new Board(4, 4);

        assertEquals("Next position should be on the next row", new Position(0, 4), board.getNextPosition(position));
    }

    @Test
    public void setCellStatusTestSetOccupied() throws Exception {
        final Position position = new Position(2, 1);
        final int width = 3, height = 4;
        final Board board = new Board(width, height);
        board.setCellStatus(position, OCCUPIED);

        assertTrue("The status of the cell should be OCCUPIED", board.getCellStatus(position) == OCCUPIED);
        assertTrue("The number of available cells should be decreased",
                board.getNumberOfEmptyCells() == (width * height - 1));
    }

    @Test
    public void setCellStatusTestSetDoubleOccupied() throws Exception {
        final Position position = new Position(2, 1);
        final int width = 3, height = 3;
        final Board board = new Board(width, height);
        board.setCellStatus(position, OCCUPIED);
        board.setCellStatus(position, OCCUPIED);

        assertTrue("The status of the cell should be double OCCUPIED", board.getCellStatus(position) == OCCUPIED * 2);
        assertTrue("The number of available cells should be decreased by one",
                board.getNumberOfEmptyCells() == (width * height - 1));
    }

    @Test
    public void isCellAvailableToPutPieceTestEmptyCellNotThreats() throws Exception {
        final Position position = new Position(2, 2);
        final Board board = new Board(3, 3);

        assertTrue("Empty Cell will be available to place a piece on it",
                board.isCellAvailableToPutPiece(new King(), position));
    }

    @Test
    public void isCellAvailableToPutPieceOccupiedCell() throws Exception {
        final Position position = new Position(1, 1);
        final Board board = new Board(2, 2);
        board.setCellStatus(position, OCCUPIED);

        assertFalse("Occupied cell will be unavailable to place a piece on it",
                board.isCellAvailableToPutPiece(new King(), position));
    }

    @Test
    public void isCellAvailableToPutPieceFilledCell() throws Exception {
        final Position position = new Position(0, 0);
        final Board board = new Board(3, 3);
        board.setCellStatus(position, FILLED);

        assertFalse("Threating position to a filled cell should be unavailble to place a piece on it",
                board.isCellAvailableToPutPiece(new King(), new Position(1, 1)));
    }

    @Test
    public void putPieceOnBoardTestOccupiesBoard() throws Exception {
        final Position position = new Position(1, 1);
        final Board board = new Board(3, 3);
        final King king = new King();
        king.setPosition(position);

        board.putPieceOnBoard(king);

        assertTrue(board.getCellStatus(new Position(0, 0)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(1, 0)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(2, 0)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(0, 1)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(0, 2)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(2, 1)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(1, 2)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(2, 2)) == OCCUPIED);
    }

    @Test
    public void putPieceOnBoardTestFillsBoard() throws Exception {
        final Board board = new Board(3, 3);
        final King king = new King();
        king.setPosition(new Position(0, 0));

        board.putPieceOnBoard(king);

        assertTrue("Board data with given position should be in FILLED status",
                board.getCellStatus(new Position(0, 0)) == FILLED);
    }

    @Test
    public void removePieceFromBoardTestSinglePiece() throws Exception {
        final Board board = new Board(3, 3);
        final King king = new King();
        king.setPosition(new Position(1, 1));

        board.putPieceOnBoard(king);
        board.removePieceFromBoard(king);

        assertTrue(board.getCellStatus(new Position(0, 0)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(1, 0)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(2, 0)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(0, 1)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(0, 2)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(2, 1)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(1, 2)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(2, 2)) == EMPTY);
        assertTrue(board.getCellStatus(new Position(0, 0)) == EMPTY);
    }

    @Test
    public void removePieceFromBoardTestMultiplePieces() throws Exception {
        final Board board = new Board(3, 3);
        final King king = new King();
        king.setPosition(new Position(0, 0));

        final King king2 = new King();
        king2.setPosition(new Position(2, 0));

        board.putPieceOnBoard(king);
        board.putPieceOnBoard(king2);

        board.removePieceFromBoard(king);

        assertTrue(board.getCellStatus(new Position(1, 0)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(1, 1)) == OCCUPIED);
        assertTrue(board.getCellStatus(new Position(2, 1)) == OCCUPIED);
        assertTrue(board.getCellStatus(king2.getPosition()) == FILLED);
    }
}
