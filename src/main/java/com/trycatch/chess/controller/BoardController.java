package com.trycatch.chess.controller;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.King;
import com.trycatch.chess.model.piece.Piece;
import com.trycatch.chess.model.piece.Rook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosker on 03/07/16.
 */
public class BoardController {
    private List<Piece> pieceList;
    private Board board;
    private List<String> solutionBoard;

    public BoardController(Board board, List<Piece> pieceList) {
        this.board = board;
        this.pieceList = pieceList;
        this.solutionBoard = new ArrayList<>();
    }

    public List<String> getSolutionBoard() {
        return solutionBoard;
    }

    public void findChessCombination(Position position, int pieceIndex) {
        if ((board.getNumberOfEmptyCells() <= 0 ||
                position == null ||
                board.isPositionAfterThanLastCell(position)) &&
                pieceIndex < pieceList.size()) {
            return;
        } else if (pieceIndex >= pieceList.size()) {
            final StringBuilder solution = new StringBuilder("Solution " + solutionBoard.size()).append("\n");

            pieceList.stream().forEach(piece -> solution.append(piece.toString()).append("\n"));

            solutionBoard.add(solution.toString());
        } else {
            final Position newPosition = board.getNextAvailablePosition(position);
            if (board.isCellAvailableToPutPiece(pieceList.get(pieceIndex), position)) {
                final Piece piece = pieceList.get(pieceIndex);

                piece.setPosition(position);
                board.putPieceOnBoard(piece);

                findChessCombination(newPosition, pieceIndex+1);

                board.removePieceFromBoard(piece);
                piece.setPosition(null);
            }
            findChessCombination(newPosition, pieceIndex);
        }
    }

    public static void main(String[] args) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new King());
        pieces.add(new King());
        pieces.add(new Rook(3, 3));

        BoardController boardController = new BoardController(new Board(3,3), pieces);
        boardController.findChessCombination(new Position(0, 0), 0);

        boardController.getSolutionBoard().stream().forEach(System.out::println);
    }
}
