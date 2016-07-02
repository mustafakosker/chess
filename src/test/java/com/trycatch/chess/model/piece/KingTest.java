package com.trycatch.chess.model.piece;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import org.junit.Test;

import static com.trycatch.chess.constants.CellStatus.FILLED;
import static com.trycatch.chess.constants.CellStatus.OCCUPIED;
import static org.junit.Assert.assertEquals;

/**
 * Created by kosker on 02/07/16.
 */
public class KingTest {

    @Test
    public void testOccupyBoardCurrentPosition() throws Exception {
        final Board board = new Board(2, 2);
        final King king = new King();
        final int x = 0, y = 0;

        king.occupyBoard(new Position(x, y), board);

        final int[][] boardData = board.getBoardData();

        assertEquals("Position of the piece should be in FILLED state", FILLED, boardData[x][y]);
    }

    @Test
    public void testOccupyBoardOccupiedPositions() throws Exception {
        final Board board = new Board(3, 3);
        final King king = new King();
        final int x = 1, y = 1;

        king.occupyBoard(new Position(x, y), board);

        final int[][] boardData = board.getBoardData();

        assertEquals("Position of the (x - 1, y) should be occupied", OCCUPIED, boardData[x - 1][y]);
        assertEquals("Position of the (x, y + 1) should be occupied", OCCUPIED, boardData[x][y + 1]);
        assertEquals("Position of the (x + 1, y + 1) should be occupied", OCCUPIED, boardData[x + 1][y + 1]);
        assertEquals("Position of the (x + 1, y) should be occupied", OCCUPIED, boardData[x + 1][y]);
        assertEquals("Position of the (x, y - 1) should be occupied", OCCUPIED, boardData[x][y - 1]);
        assertEquals("Position of the (x + 1, y - 1) should be occupied", OCCUPIED, boardData[x + 1][y - 1]);
        assertEquals("Position of the (x - 1, y - 1) should be occupied", OCCUPIED, boardData[x - 1][y - 1]);
        assertEquals("Position of the (x - 1, y + 1) should be occupied", OCCUPIED, boardData[x - 1][y + 1]);
    }

    @Test
    public void testOccupyBoardAtLastCell() throws Exception {
        final Board board = new Board(3, 3);
        final King king  = new King();
        final int x = 2, y = 2;

        king.occupyBoard(new Position(2, 2), board);

        final int[][] boardData = board.getBoardData();

        assertEquals("Last cell should be filled with King", FILLED, boardData[x][y]);
    }
}
