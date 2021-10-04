* Main idea: collection of various card games with various users in a possibly online scenario

* When running, a GUI will be used to interact with the program/games

* Will support a few games of relatively similar type (start with 1) 

  * Crazy 8's
  * War

  

---



Game rules sourced from [Bicycle Cards](https://bicyclecards.com/rules/).

* Crazy 8's

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

  

* War

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