# Specification

* Main idea: collection of various card games in a *pass-and-play* type format
* When running: 
  * A GUI will be used to interact with the program allowing players to click on menu items, select cards, etc.
  * The main menu should allow users to select which game they wish to play, and possibly edit settings for each game
  * During gameplay, users should be able to 
    * See their own cards
    * See the state of the table (e.g. the user should see which cards are on the stack in Crazy Eights)
    * Play cards from their hand (not necessarily valid ones)
    * Get feedback if an invalid card has been played (e.g. in Crazy Eights, the played card must match the top card on the stack's rank or suit)
  * At the end of a game, an appropriate end-game screen should be shown, with options allowing the user to play the same game again or return to the main menu
* Supported games (rules in section below)

  * Crazy Eights
  * War






## Game Rules

(Sourced from [Bicycle Cards](https://bicyclecards.com/rules/) with minor modifications)



### Crazy Eights

* 2+ players, 52 cards
* Start:
  * Deal 5 cards to each player
  * Rest of cards placed face down in middle (deck)
  * Top most card on deck is flipped to form a starter pile
* Play:
  * Each player take turns placing cards from their hand into the starter pile
    * Cards must match suit or number (exception: 8s)
      * 8s can be played on at any time in turn, on play, player must specify suit (but not a rank)
      * The next player must follow the specified suit (or play an 8)
    * If no valid cards to play: draw one card from deck 
    * If the deck is empty, the current player must pass their turn
    * Even if there's a valid move, player can still draw from deck
* Win condition:
  * Player with no cards left wins



### War

* 2 players, 52 cards
* Start: 
  * Deal half deck to each player
* Play:
  * Each player puts down top card of their stack (players cannot choose the card to put down)
  * Compare cards, player with higher card takes both cards into bottom of their stack
  * If they're the same, 
    * Take top two cards from own stack (one face up, one face down)
    * Compare face up card, player with higher one takes all cards in play
    * If tie, take another card, place face down
    * Flip over original face down and compare
    * Continue until one person has a higher card
* Win condition: 
  * Player who has all the cards