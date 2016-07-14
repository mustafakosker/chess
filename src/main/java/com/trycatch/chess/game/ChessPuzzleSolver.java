package com.trycatch.chess.game;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by kosker on 07/07/16.
 */
public class ChessPuzzleSolver {
    private final ChessPuzzle chessPuzzle;

    public ChessPuzzleSolver(ChessPuzzle chessPuzzle) {
        this.chessPuzzle = chessPuzzle;
    }

    private List<List<Piece>> generatePiecePermutationList() {
        final int[] pieces = chessPuzzle.getPieceList().stream().mapToInt(Piece::getID).toArray();
        Arrays.sort(pieces);

        final List<List<Piece>> piecePermutationList = new ArrayList<>();

        do {
            List<Piece> pieceList = Arrays.stream(pieces)
                    .boxed()
                    .map(this::readPiece)
                    .collect(Collectors.toList());

            piecePermutationList.add(pieceList);
        } while (nextPermutation(pieces));

        return piecePermutationList;
    }

    private Piece readPiece(int pieceId) {
        switch (pieceId) {
            case King.ID:
                return new King();
            case Knight.ID:
                return new Knight();
            case Queen.ID:
                return new Queen();
            case Bishop.ID:
                return new Bishop();
            case Rook.ID:
                return new Rook();
            default:
                return null;
        }
    }

    private List<Callable<Integer>> generateCallableList() {
        final List<List<Piece>> piecePermutationList = generatePiecePermutationList();

        return piecePermutationList.stream()
                .map((pList) -> ((Callable<Integer>) () -> {
                    System.out.println("Calculation started at thread: " + Thread.currentThread().getName());

                    final BoardController controller = new BoardController((new Board(chessPuzzle.getBoardWidth(), chessPuzzle.getBoardHeight())));
                    controller.setPieceList(pList);
                    controller.findChessCombination(new Position(0, 0), 0);

                    System.out.println("Calculation ended at thread: " + Thread.currentThread().getName());

                    return controller.getSolutionCount();
                }))
                .collect(Collectors.toList());
    }

    public int solvePuzzle() throws InterruptedException {
        final ExecutorService executorService = Executors.newWorkStealingPool();
        final List<Callable<Integer>> solutionCallableList = generateCallableList();

        final int totalSolutionCount = executorService.invokeAll(solutionCallableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .reduce(0, (a, b) -> a + b);

        executorService.shutdown();

        return totalSolutionCount;
    }

    /**
     * This code is taken from: https://www.nayuki.io/page/next-lexicographical-permutation-algorithm
     * Calculates all permutations of the given array.
     * E.g: All permutations of [1, 0, 1] will be [1, 1, 0], [1, 0, 1], [0, 1, 1]
     *
     * @param array to be processed for permutation
     * @return next permutation of given rray
     */
    private boolean nextPermutation(int[] array) {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
            i--;
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0)
            return false;

        // Let array[i - 1] be the pivot
        // Find rightmost element that exceeds the pivot
        int j = array.length - 1;
        while (array[j] <= array[i - 1])
            j--;
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }

        // Successfully computed the next permutation
        return true;
    }
}
