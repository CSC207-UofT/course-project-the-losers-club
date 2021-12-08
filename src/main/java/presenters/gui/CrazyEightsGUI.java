package presenters.gui;

import usecases.IOInterfaces.CrazyEightsIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements the CrazyEightsIO interface using Swing to create a GUI to play the game.
 */
public class CrazyEightsGUI extends GUI implements CrazyEightsIO, ActionListener {

    private final JLabel topCard = new JLabel();
    private JButton[] buttons = new JButton[0];
    private volatile boolean card_selected;
    private String selected_card = "";

    /**
     * Creates a new instance of CrazyEights GUI. This will cause a GUI window to open. All implemented methods will do
     * user interaction through this window or popups.
     */
    public CrazyEightsGUI() {
        super();
        this.frame.setTitle("Crazy Eights");


        this.topCard.setText("Top Card");
        this.topCard.setFont(new Font("Serif", Font.PLAIN, 24));
        this.topCard.setVerticalTextPosition(SwingConstants.TOP);
        this.topCard.setHorizontalTextPosition(SwingConstants.CENTER);
        this.panel.add(this.topCard);


        JButton send_card = new JButton("Send Card");
        send_card.setActionCommand("final");
        send_card.addActionListener(this);
        this.panel.add(send_card);


        this.update();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("final")) {
            this.card_selected = true;
        } else {
            this.selected_card = e.getActionCommand();
        }
    }

    /**
     * This method should change the current user to <code>username</code> and then informers the user of this change.
     *
     * @param username The <code>username</code> of the next player.
     */
    @Override
    public void changePlayer(String username) {
        this.sendPopup("It is now " + username + "'s turn");
    }

    /**
     * This method should display the new top card to the user.
     *
     * @param card a string representation of the card to be displayed.
     */
    @Override
    public void showTopCard(String card) {
        card = card.toLowerCase();

        ImageIcon icon = new ImageIcon(this.stringToImage(card));
        this.topCard.setIcon(icon);
        this.update();
    }

    /**
     * This method should display the user's hand.
     *
     * @param hand a string representation of a hand of cards.
     */
    @Override
    public void showHand(String hand) {
        //First clean the old hand
        for (JButton button : this.buttons) {
            this.panel.remove(button);
        }

        hand = hand.toLowerCase();

        String[] cards_strings = hand.split(" ");

        this.buttons = new JButton[cards_strings.length];

        //Create and add new buttons
        for (int i = 0; i < cards_strings.length; i++) {
            ImageIcon icon = new ImageIcon(this.stringToImage(cards_strings[i]));
            this.buttons[i] = new JButton(icon);

            this.buttons[i].setActionCommand(cards_strings[i]);
            this.buttons[i].addActionListener(this);

            this.panel.add(this.buttons[i]);
        }

        //This code sets up the window itself and should be done last
        this.update();
    }

    /**
     * This method prompts the user to see if they want to draw a new card from the deck.
     *
     * @return true if the user wants to draw a card, false if the user does not want to draw a card.
     */
    @Override
    public boolean drawCard() {
        int selection = JOptionPane.showConfirmDialog(new JFrame(), "Would you like to draw a card?");

        return selection == 0;
    }

    /**
     * This method should return a string representation of the card that is selected by the user.
     *
     * @return a string representation of the card selected by the user.
     */
    @Override
    public String getCard() {
        while (!card_selected) {
            Thread.onSpinWait();
        }
        this.card_selected = false;
        return this.selected_card;
    }

    /**
     * This method should prompt the user to pick a suit
     *
     * @return a char representation of the chosen suit
     */
    @Override
    public char getSuit() {
        //Custom button text
        Object[] options = {"Spades",
                "Clubs",
                "Diamond",
                "Hearts"};
        int n = JOptionPane.showOptionDialog(frame,
                "Please Select A Suit",
                "Suit Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        char[] suits = {'s', 'c', 'd', 'h'};

        return suits[n];
    }
}
