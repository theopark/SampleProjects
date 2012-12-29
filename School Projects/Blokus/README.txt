Blokus

Blokus is a 2-4 player board game where you lay tetris-like pieces on a 20x20 board. 
For my Practicum in Artificial Intelligence Project, I worked with two other students to create 
intelligent agents that can play the game. Each player starts by laying a piece in his/her given 
corner, then takes turns by playing pieces that are connected to your own existing pieces on a 
corner of a piece (they cannot be adjacent to another one of your own pieces).

The GUI for our game features a 20x20 game board that shows the game as it gets played, as well as 
a side board that shows a given player's remaining pieces and displays the winner of each game.

We have four game playing agents (MaxN, DeeperMaxN, MiniMax, and FastMiniMax). 
These are game tree search agents. The way game tree search agents operate is that 
they consider many moves and pick the one that leads to the best outcome.  For example, 
the starting player would have an empty board state and would expand every possible move for that 
board, then every possible move for each of those board states, and so on and so forth. 
Ideally, we would expand every move until we have reached a goal state.  However, in reality, 
doing so would take too long, so we must limit the depth to which we search and prune any branches 
deemed unfavorable.

Since this was created in an academic setting (and my repo is public), I have left out any source 
code to comply with any academic integrity poicies of Cornell, but I have included a JAR file that 
you can run. To choose how many/which players play, simply edit the "game.txt" file.  The jar runs a 
given game multiple times, rotating which player starts (since the starting player has an advantage, 
especially in a two player game) and averages the scores into an output results file. 

In our trials, we have found that going deeper yields no significant increase in expected score 
(unlike games like chess, where depth and performance are strongly correlated). We believe this is 
due to the fact that in Blokus, many different board configurations can lead to very similar scores, 
which makes different branches of a search tree similar as well.  Since our board evaluations are 
merely approximations of the "value" of a given board state, the "best" branch is even harder to 
determine.

To modify the "game.txt" file, we start with the number of players we want in the game, then list 
each player.  For MaxN and DeeperMaxN, we have 6 arguments.  The first tells the agent how heavily 
to weight the number of squares the player's pieces occupy.  The second argument weights the number 
of open corners that the player can lay another piece onto.  The third argument weights the spread 
of the player's pieces (the area of the smallest rectangle that can surround the player's pieces). 
The fourth weights the distance from the player's pieces to the center of the board.  The fifth 
indicates the top percentage of moves to consider, which helps us prune the search tree.  The final 
argument indicates the max depth to search to.  For MiniMax, there are three arguments: the max 
depth to search to, whether to use alpha-beta pruning, and which evaluation function ("tileAndCorner" 
or "tileAndPossMove") to use to score various board states.  FastMiniMax has 4 arguments - the first, 
second, and fourth are the three arguments from MiniMax, and the third argument is a number indicating 
what branching factor to use.

I have provided some sample games below:
4
MiniMax,1,true,tileAndPossMove
FastMiniMax,4,true,8,tileAndPossMove
MiniMax,1,true,tileAndPossMove
MiniMax,1,true,tileAndPossMove
4
MiniMax,1,true,tileAndPossMove
FastMiniMax,4,true,8,tileAndPossMove
MaxN,2,2,2,1,1,1
DeeperMaxN,2,2,2,1,1,1

Note: if you set games after one another, the JAR will play one game after another. If games are 
running slowly, you can reduce the branching factor/top percentage of moves to consider/max depth.