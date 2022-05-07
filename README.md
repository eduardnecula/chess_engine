Team project: Without PA we would be pa
team members: Mirescu Victor Marian, Necula Eduard Ionut, Radu Alexandru Florin, Simiocencu Andrei
team group: 322CA


Compilation instructions:
-Enter the src file, where all the classes are. For compilation we will use the command make, which
-We will run the make command every time we make code changes.
-To start the engine we will run (from the src file) the command:
xboard -fcp "make run" -debug
-make run starts our engine code so we can test it individually.

Step 1:

Details about the structure of the program:
A hierarchy of songs has been created that derives from the main JavaPiece class. They contain a name, an index
and a color. The index is made to give importance in the future to the piece to be attacked (that of
higher index).
For the board we used a matrix of Square classes, where Square represents a square that has a piece.
If the square piece is NULL, it means that the square is empty. The board also has cleaning functions
the square, that is, delete the piece after it and add a null. On lines 0 and 1 I put the black pieces, and on
lines 6 and 7 I put the white pieces.
The program is connected to the interface in the Main function, using a switch that interprets the commands
xboard data.
The role of the interpretation function is to transform an expression of the form "e2e4" into a movement on the board, using
coordinates such as: Source line, Source column, Destination line, Destination column
The movement algorithm has the role of trying for the first time to make aggressive movements with the first piece that can do
this, and if none can make a trivial move. By aggressive movement I mean the pawn to capture
a piece, and through the banal just to go in front


Details about the algorithmic approach:
ThinkAndMove () algorithm:
It goes through all the elements in the matrix and checks if it finds pawns of the color with which the snout plays.
If he finds it, try to make aggressive movements with them, and if it is not possible, make trivial movements until they
he is exhausted and resigns.
The priority that the pawn has in the moves is the following:
1) if she is on the last house, she turns into a queen
2) if he has a piece on the box to his left or right, he captures it (moves on it)
3) if he is on his first move (he is still on the starting line), he will take two steps forward (only the odd index ones, for
a better defense inspired by the Sicilian defense)
4) in other cases, advance a house in front if it is free
The moves attempted by each pawn are in order of priority.

Step 2:

At this stage, the program was developed to be able to move all the parts.
The reasoning used is simple, if a piece can attack, it will. In stage 3, the actual thinking will be developed.

Rocada:
The implemented algorithm ensures that if the king is moved, the move cannot be made. If any of the turns are moved, the cast will no longer run.
In order to perform the casting, the code is sure to remove all the pieces between the turn and the king. It was preferred to release the parts for the small cast.
Also, if the place where the king will be is in chess, the move will not be made.

By the way:
Implementation is simple. Depending on the color I play with, the program makes sure to move the pawn appropriately. If the last move is not the correct one, the en passant will not be executed

Chess condition:
The program ensures that the king can move around. Thinking details:
The king is looking for the next valid square where he can move around. If this square is occupied by the enemy, but does not enter chess, the king will attack the pawn.
To achieve the condition of chess, it is sought in all areas from which it can be attacked. If he is attacked, he summons a function that solves the chess, that is, moves the king. If he does not find a good move, the game offers a draw.

Normal chess moves:
If the king is not in chess, or if the program cannot attack anything, he makes a simple move to an empty square, with any piece available.

It was ensured for the king that he kept his distance from the enemy king.

More details:

1. After receiving a move, the engine updates the position in the matrix
2. It is checked if the king is in chess, if he solves it, otherwise he moves on
3. Move the pieces between the king and the turn (for the small ring)
4. Priority is given to moves that can attack an enemy piece.
5. After aggressive moves, try simple moves
6. The program ends when no more movements are available, or until the movements are repeated.

Sources of inspiration:
Chess strategies, like exp: Sicilian Defense
Series: Gambit Damei


Responsibility of each member Stage 1
Simiocencu Andrei: - representation of the pieces + improvements to some functii
Radu Alexandru: - representation of the game board + improvements to some functions
Necula Eduard: - connection of the game with the xboard interface, makefile, interpretation of the movement given by the xboard, new game
Mirescu Victor: - the thinking program of the game through which the movements are made (thinkAndMove), the transposition of the commands on the board
game and interpreting moves in format ("move a2a3"), changing the game mode white-> black

Responsibility of each member Stage 2
Simiocencu Andrei: - chess condition + testing
Radu Alexandru: - chess condition + testing + shift shift
Necula Eduard: implementation of moving in passing + small and large ring, depending on color + moving horse + moving king.
Mirescu Victor: - chess condition + testing + moving the queen