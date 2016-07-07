Chess puzzle solver for given piece types and board size.
To execute the app, please run following command first:

    `mvn clean install`

Now it's ready to run it. Run the command below after build:

    `java -jar chess-puzzle-solver-1.0-SNAPSHOT.jar`


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
Total time: 100.629 seconds
```

Please note that total time taken could be reduced by 4 times by calculating
symmetric solutions.



