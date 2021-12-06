package usecases;

import entities.Card;
import controllers.MainMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.console.Input;
import presenters.console.Output;
import usecases.usermanagement.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BuraTest {
    Bura game;
    List<String> usernames;
    UserManager usermanager;

    private static class TestInput extends Input implements MainMenu.Input, GameTemplate.Input {
        final String[] getCardSequence = {"H6", "C7", "HK", "D9", "D8", "H8", "CA", "HQ", "C9", "H9", "H10", "S10", "CJ",
                "S7", "S9", "HA", "SA", "C8"};
        int currCardIndex = 0;

        @Override
        public String getCard() {
            String chosenRank = getCardSequence[currCardIndex];
            currCardIndex += 1;
            return chosenRank;
        }
    }

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
        game = new Bura(usernames, usermanager, new BuraTest.TestInput(), new Output(), new Random(12345), "BURA");
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
}
