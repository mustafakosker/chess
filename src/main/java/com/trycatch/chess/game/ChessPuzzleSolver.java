package com.trycatch.chess.game;

import com.trycatch.chess.model.Board;
import com.trycatch.chess.model.Position;
import com.trycatch.chess.model.piece.*;
import com.trycatch.chess.util.PositionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by kosker on 07/07/16.
 */
public class ChessPuzzleSolver {
    private final int boardWidth;
    private final int boardHeight;
    private final List<Piece> pieceList;

    public ChessPuzzleSolver(int boardWidth, int boardHeight, List<Piece> pieceList) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.pieceList = pieceList;

        initBoardUtils();
    }

    private void initBoardUtils() {
        PositionUtil.init(boardWidth, boardHeight);
        BoardOccupyManager.createOccupiedPositionsMap(boardWidth, boardHeight, pieceList);
    }

    private List<List<Piece>> generatePiecePermutationList() {
        final int[] pieces = pieceList.stream().mapToInt(Piece::getID).toArray();
        Arrays.sort(pieces);

        final List<List<Piece>> piecePermutationList = new ArrayList<>();

        do {
            List<Piece> pieceList = Arrays.stream(pieces)
                    .boxed()
                    .map(ChessPuzzleSolver::readPiece)
                    .collect(Collectors.toList());

            piecePermutationList.add(pieceList);
        } while (nextPermutation(pieces));

        return piecePermutationList;
    }

    private List<Callable<Integer>> generateCallableList() {
        final List<List<Piece>> piecePermutationList = generatePiecePermutationList();

        return piecePermutationList.stream()
                .map((pList) -> ((Callable<Integer>) () -> {
                    final BoardController controller = new BoardController((new Board(boardWidth, boardHeight)));
                    controller.setPieceList(pList);
                    controller.findChessCombination(new Position(0, 0), 0);

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


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Please enter the number of pieces and piece type separated by comma.");
        System.out.println("Type K for king, N for Knight, B for bishop and Q for Queen");
        System.out.println("E.g: 2K,4B,3N,2Q");
        System.out.println("Press enter for default input, that is 2K,2B,2Q,1N");
        Scanner scanner = new Scanner(System.in);
        String pieceInput = scanner.nextLine();
        if (pieceInput == null || "".equals(pieceInput)) {
            pieceInput = "2K,2Q,2B,1N";
            System.out.println(pieceInput);
        }

        System.out.println("Please enter width of the board:");
        final int boardWidth = scanner.nextInt();

        System.out.println("Please enter height of the board:");
        final int boardHeight = scanner.nextInt();

        long startTime = System.currentTimeMillis();

        final List<Piece> pieceList = readPieces(pieceInput);

        final ChessPuzzleSolver chessPuzzleSolver = new ChessPuzzleSolver(boardWidth, boardHeight, pieceList);
        final int totalSolutionCount = chessPuzzleSolver.solvePuzzle();

        long endTime = System.currentTimeMillis();

        System.out.println("Total solution count: " + totalSolutionCount);

        final double totalTimeInSec = (endTime - startTime) / 1000.0d;
        System.out.println("Total time: " + totalTimeInSec + " seconds");
    }

    private static List<Piece> readPieces(final String input) {
        final String[] splittedInput = input.split(",");
        final List<Piece> pieceList = new ArrayList<>();

        Arrays.stream(splittedInput).forEach(s -> {
            final int numberOfPiece = s.charAt(0) - '0';
            final char pieceKeyChar = s.charAt(1);

            for (int i = 0; i < numberOfPiece; i++) {
                pieceList.add(readPiece(pieceKeyChar));
            }
        });

        return pieceList;
    }

    private static Piece readPiece(final int pieceKeyChar) {
        switch (pieceKeyChar) {
            case 'K':
            case King.ID:
                return new King();
            case 'N':
            case Knight.ID:
                return new Knight();
            case 'Q':
            case Queen.ID:
                return new Queen();
            case 'B':
            case Bishop.ID:
                return new Bishop();
            case 'R':
            case Rook.ID:
                return new Rook();
            default:
                return null;
        }
    }
}
