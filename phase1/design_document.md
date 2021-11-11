# Design Document

## Updated Specification


## Major Design Decisions


## Clean Architecture

## SOLID Design Principles

* Single Responsibility
* Open/Closed
* Liskov's Substitution
* Interface Segregation
  * Rather than have one main input/output interface for both the `GameSelector` and each individual game, interfaces were separated allowing implementations to choose which interfaces to implement.
* Dependency Inversion
  * Each game (subclasses of `GameTemplate`) must depend on an interface between the game and the user running the code. An interface was used to specify the ways a user could interact with the game, and the subclasses of `GameTemplate` only depended on there being given some implementation of the interface, but did not directly depend on the implementation.

## Packaging Strategy

## Design Patterns

* Factory Method
  * `GameTemplate.gameFactory()` is used to create the various games. When games are added/removed from the pool of available games, `GameTemplate` must be changed, but not the menu implementation in `GameSelector`. `GameSelector` remains independent of the actual available games.


## Progress Report

### Open questions

### What has worked well

### What each group member has been working on

* Raymond: Added the `GameSelector` menu (and related console IO), added `UserManager` serialization flows
* Teddy:
* Brian:
* Bradley:
* Daniel:
* Azamat:
* Luke:
* Nitish:

### What each group member plans to work on next
* Raymond:
* Teddy:
* Brian:
* Bradley:
* Daniel:
* Azamat:
* Luke:
* Nitish:

