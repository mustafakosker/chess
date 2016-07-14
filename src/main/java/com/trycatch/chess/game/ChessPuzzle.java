package com.trycatch.chess.game;

import com.trycatch.chess.model.piece.*;
import com.trycatch.chess.util.PositionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kosker on 15/07/16.
 */
public class ChessPuzzle {
    private int boardWidth;
    private int boardHeight;
    private List<Piece> pieceList;

    public ChessPuzzle(int boardHeight, int boardWidth, List<Piece> pieceList) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.pieceList = pieceList;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public static final class ChessPuzzleBuilder {
        private int boardHeight;
        private int boardWidth;
        private List<Piece> pieceList;

        private ChessPuzzleBuilder() {
        }

        public static ChessPuzzleBuilder aChessPuzzle() {
            return new ChessPuzzleBuilder();
        }

        public ChessPuzzleBuilder withBoardHeight(int boardHeight) {
            this.boardHeight = boardHeight;
            return this;
        }

        public ChessPuzzleBuilder withBoardWidth(int boardWidth) {
            this.boardWidth = boardWidth;
            return this;
        }

        public ChessPuzzleBuilder withPieceList(String pieceList) {
            this.pieceList = readPieces(pieceList);
            return this;
        }

        private List<Piece> readPieces(final String input) {
            final String[] splittedInput = input.split(",");
            final List<Piece> pieceList = new ArrayList<>();

            Arrays.stream(splittedInput).forEach(s -> {
                s = s.trim();

                final int numberOfPiece = s.charAt(0) - '0';
                final char pieceKeyChar = s.charAt(1);

                for (int i = 0; i < numberOfPiece; i++) {
                    pieceList.add(readPiece(pieceKeyChar));
                }
            });

            return pieceList;
        }

        private Piece readPiece(final int pieceKeyChar) {
            switch (pieceKeyChar) {
                case 'K':
                    return new King();
                case 'N':
                    return new Knight();
                case 'Q':
                    return new Queen();
                case 'B':
                    return new Bishop();
                case 'R':
                    return new Rook();
                default:
                    return null;
            }
        }

        public ChessPuzzle build() {
            final ChessPuzzle chessPuzzle = new ChessPuzzle(boardHeight, boardWidth, pieceList);
            initBoardUtils();

            return chessPuzzle;
        }

        private void initBoardUtils() {
            PositionUtil.init(boardWidth, boardHeight);
            BoardOccupyManager.createOccupiedPositionsMap(boardWidth, boardHeight, pieceList);
        }
    }
}
