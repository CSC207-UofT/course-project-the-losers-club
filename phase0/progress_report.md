## Progress Report

### Summary

#### Specification

The main idea of our project is to implement a collection of card games in Java while applying the design principles we have bene learning in lecture. While not implemented yet, our goal is to have a GUI for users to interact with our program. Currently, we've implemented Crazy Eights, but our design allows for expansion to other games like War and other games.

#### CRC Cards

Our implementation currently consists of 3 entity classes (`Card`, `Deck`, `Hand`), 3 use cases (`Game`, `CrazyEightsGame`, `Player`), and 1 controller/driver class (`ConsoleInOut` which implements the `InOut` interface). In the future, we may decide to abstract a few of these classes and implement specific versions for specific games as required.

#### Walkthrough

Upon running our program, it should launch into a 2 player game of Crazy Eights. The game will loop through each player's turn, allowing players to play cards from their hand to the playing field. The game finishes when one player has exhausted all their cards. 

#### Skeleton Program

The skeleton program is an implementation of our walkthrough. The entry point of our program is `Main.java`, where various classes are instantiated to run a Crazy Eights game. Then, the game is run and players are able to interact with the game through the console. Once a winning state for one player is reached, the program exits.



### Questions

1. Should we refactor the entity classes such that the Deck class does not implement Card objects?
2. Should we make the Hand Class Abstract to implement different games?
3. Should we implement playing field as its own class?



### What has worked well

Our project group has altogether been organized and communicating clearly. The design of the project is intuitive 
and our implementation displays the design's clarity. The design of the project also allows different group members 
to work on different sections of the project at the same time, which allows for better efficiency. 



### Responsibilities so far

* Raymond: Worked on console input/output, the Controller class, the progress report, and the specification
* Brian: Worked on Use Case classes and the specification
* Luke: Worked on console input/output and the Controller class 
* Bradley: Worked on Entity Classes
* Azmat: Worked on the Use Case classes
* Nitish: Worked on the Use Case classes
* Teddy: Worked on CRC cards and the Use Case classes
* Daniel: Worked on Entity Classes, the progress report, and the walkthrough



### Future Responsibilities

* Raymond: Console/IO transition layer and potential 2nd game implementation
* Brian: Console/IO transition layer and potential 2nd game implementation
* Luke: GUI
* Bradley: GUI
* Azmat: Potential 2nd game implementation
* Nitish: Potential 2nd game implementation and implementing Crazy 8 draw rule
* Teddy: Potential 2nd game implementation
* Daniel: GUI