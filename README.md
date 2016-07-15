Chess puzzle solver for given piece types and board size.
To execute the app, please run following command first:

    mvn clean install

Now it's ready to run it. Run the command below after build:

    java -jar target/chess-puzzle-solver-1.0-SNAPSHOT.jar


```
Please enter the number of pieces and piece type separated by comma.
Type K for king, N for Knight, B for bishop and Q for Queen
E.g: 2K,4B,3N,2Q
Press enter for default input, that is 2K,2B,2Q,1N
> 3B,2K,2Q,1N
```

After specifying piece types along with count, you should provide board size:
```
Please enter width of the board:
> 7
Please enter height of the board:
> 7
```

Now you will see solution of the puzzle. It will return following result for
default configuration:
```
Total solution count: 3063828
Total time: 23.643 seconds
```

Main class is `com.trycatch.chess.game.ChessPuzzleSolver`. This class will
iterate through all permutations of given piece type and count list.
It will configure utility classes with given configuration to get started to find solutions.

You should check `com.trycatch.chess.game.BoardController` to see recursive method
for traversing the board for all the positions.

`com.trycatch.chess.game.BoardOccupyManager` class should be initialized after
board configuration is specified. It will calculate and cache all the threatened(occupied) positions for the all the given piece types.

You can see the implemented piece types in `com.trycatch.chess.model.piece` package. You will see that implemented piece types are derived from `com.trycatch.chess.model.piece.Piece`

`com.trycatch.chess.model.Board` class responsible for holding the current status
of the board. It also has some methods to put a piece on the board and remove a piece from the board.

Please note that total time taken could be reduced by 4 times by calculating
symmetric solutions.



