## Walkthrough

We first intialize a "CrazyEightGame" instance, which then intializes a with "Deck" class and 2 "Player" classes, 
1 for each player. 

The "Deck" class is initialized with a list containing "Card" objects, simulating a real deck of 52 unique cards. 
The 2 "Player" instances are initialized with preset names "PS1" and "PS2", as well as "Hand" classes used to represent 
each player's hand.

After "CrazyEightGame" intializes both "Deck" and the 2 "Player" classes, it distributes cards appropriately using 
"Deck.shuffle()" and "Player.addToHand()". 

Once the starting game state has been intialized, "CrazyEightGame" loops through player after player's turn until one
player's hand is empty (the win condition). 

During this loop, "CrazyEightGame" will look at its "currPlayer" instance variable to determine which player will be 
making a move. "CrazyEightGame" will then use "calculateValidMoves()" to determine the valid moves based on the 
"currPlayer"'s "hand" instance variable (which is a "Hand" object). 

After determining the valid moves, if "currPlayer" can make a move, then the player can choose to play cards from their
"hand" through "CardPicker" and its "takeInput()" function. If the move is legal, then "CrazyEightGame" will make the 
move and change the "currPlayer"'s "hand" and it's "deck" instance variable. These changes can be done through functions 
such as "Deck" methods such as "addCard()" and "Player" methods such as "removeFromHand()". If the move is illegal, 
"CrazyEightGame" will prompt the player to pick a legal move.

If "currPlayer" does not have any possible moves, they will be forced to draw a card from the deck using "Deck" method 
"drawCard()" and "Player" method "addToHand()".

We then use "Player" method "checkWin()" to check if "currPlayer" has won the game or not. If the game has been won, 
then the game loop breaks and the current player wins.  

If the current player did not win, then the "currPlayer"'s turn is over and "currPlayer" now switches to the other
"Player" class, and runs through the same game logic for their turn.