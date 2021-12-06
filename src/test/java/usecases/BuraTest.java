package usecases;

import entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.IOInterfaces.BuraIO;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BuraTest {
    Bura game;
    List<String> usernames;
    UserManager usermanager;

    @BeforeEach
    void setUp() throws UserManager.UserAlreadyExistsException {
        usernames = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            usernames.add("Test User-" + i);
        }
        usermanager = new UserManager();
        for (String username : usernames) {
            usermanager.addUser(username);
        }
        game = new Bura(usernames, usermanager, new FakeBuraGUI(), new Random(12345));
    }

    @Test
    void TestStartGame() {
        game.startGame();
        int maxScore = 0;
        int sumScore = 0;
        for (Player player : game.players) {
            int score = game.SCORE_TRACKER.get(player);
            maxScore = Math.max(maxScore, score);
            sumScore += score;
            for (Card card : player.getHand().getCards()) {
                sumScore += game.ranks.get(card.getRank());
            }
        }
        while (!game.deck.isEmpty()) {
            sumScore += game.ranks.get(game.deck.drawCard().getRank());
        }
        assertTrue(maxScore >= 31 || game.currPlayer.isHandEmpty());
        assertEquals(sumScore, 120);
    }

    @Test
    void TestToString() {
        assertEquals("Bura", game.toString());
    }

    @Test
    void TestGetMaxPlayers() {
        assertEquals(6, Bura.getMaxPlayers());
    }

    @Test
    void TestGetMinPlayers() {
        assertEquals(2, Bura.getMinPlayers());
    }

    @Test
    void TestBeatCards() {
        Card card1 = new Card("10", 'S');
        Card card2 = new Card("A", 'H');
        assertTrue(game.beatsCard(card1, card2));
    }

    @Test
    void Test2BeatCards() {
        Card card1 = new Card("6", 'D');
        Card card2 = new Card("J", 'D');
        assertFalse(game.beatsCard(card1, card2));
    }

    static class FakeBuraGUI implements BuraIO {

        final String[] getCardSequence = {"6H", "7C", "KH", "9D", "8D", "8H", "AC", "QH", "9C", "9H", "10H", "10S", "JC", "7S", "9S", "AH", "AS", "8C"};
        int currCardIndex = 0;

        @Override
        public void changePlayer(String username) {
            System.out.println("CHANGE PLAYER username = " + username);
        }

        @Override
        public void sendPopup(String message) {
            System.out.println("POPUP message = " + message);
        }

        @Override
        public void showCardToBeat(String card) {
            System.out.println("CARD TO BEAT card = " + card);
        }

        @Override
        public void showTrumpSuit(char trump) {
            System.out.println("TRUMP SUIT trump = " + trump);
        }

        @Override
        public void showHand(String hand) {
            System.out.println("hand = " + hand);
        }

        @Override
        public String getCard() {
            String chosenRank = getCardSequence[currCardIndex];
            currCardIndex += 1;
            return chosenRank;
        }

        @Override
        public void close() {

        }

        @Override
        public void closeMessage(String message) {

        }
    }
}
