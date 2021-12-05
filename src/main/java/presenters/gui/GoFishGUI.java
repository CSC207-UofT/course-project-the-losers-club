package presenters.gui;

import usecases.IOInterfaces.GoFishIO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the interface found in GoFishIO using a GUI built with Java's native swing library.
 */
public class GoFishGUI extends GUI implements GoFishIO {

    JLabel[] hand = new JLabel[0];

    /**
     * This creates a new GoFishGUI object. Calling this method will create a blank GUI window on the screen through
     * which the user will interact with. These interactions can be observed and used via the methods of the
     * interface.
     */
    public GoFishGUI() {
        super();
        this.frame.setTitle("Go Fish");
    }

    /**
     * This method should change the current user to <>username</> and then informer the user of this change.
     *
     * @param username The <>username</> of the next player.
     */
    @Override
    public void changePlayer(String username) {
        this.sendPopup("It is now " + username + "'s turn");
    }

    /**
     * This method should send a popup to the user containing a <>message</>.
     *
     * @param message a string that is to be sent to the user
     */
    @Override
    public void sendPopup(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    /**
     * This method should display the user's hand.
     *
     * @param hand a string representation of a hand of cards.
     */
    @Override
    public void showHand(String hand) {
        for (JLabel card : this.hand) {
            this.panel.remove(card);
        }

        String[] cardStrings = hand.split(" ");

        this.hand = new JLabel[cardStrings.length];

        for (int i = 0; i < cardStrings.length; i++) {
            ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(cardStrings[i]));
            this.hand[i] = new JLabel(icon);
            this.panel.add(this.hand[i]);
        }

        this.update();
    }

    /**
     * This method should ask the user which rank of card they would like to request from the other players.
     * The string must be one of {"A", "2", "3", "4", "5", "6", "7", "8', "9", "10", "J", "Q", "K"}.
     *
     * @return a string representation of the chosen rank
     */
    @Override
    public String getRank() {
        String[] options = {
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "Jack",
                "Queen",
                "King",
                "Ace"};
        int n = JOptionPane.showOptionDialog(frame,
                "Please Select A Rank",
                "Rank Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        if (options[n].equals("10")) {
            return "10";
        } else {
            return options[n].substring(0, 1);
        }
    }

    /**
     * This method should ask the user to choose a player to request the chosen rank from.
     *
     * @param currPlayer a string representation of the current player's username and is not in the list of usernames
     *                   displayed to the user
     * @param usernames  a list of all the player usernames in the game
     * @return a string representation of the username selected by the user
     */
    @Override
    public String getPlayerUsername(String currPlayer, List<String> usernames) {
        List<String> userList = new ArrayList<>(usernames);
        userList.remove(currPlayer);

        String[] userArray = new String[userList.size()];

        for (int i = 0; i < userArray.length; i++) {
            userArray[i] = userList.get(i);
        }

        int n = JOptionPane.showOptionDialog(frame,
                "Please Select A Player",
                "Player Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                userArray,
                0);

        return userArray[n];
    }
}
