package presenters.gui;

import usecases.IOInterfaces.BuraIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements the BuraIO interface using Swing to create a GUI to play the game.
 */
public class BuraGUI extends GUI implements BuraIO, ActionListener {

    private final JLabel cardToBeat = new JLabel();
    private final JLabel trumpSuit = new JLabel();

    private JButton[] buttons = new JButton[0];
    private volatile boolean card_selected;
    private String selected_card = "";

    public BuraGUI(){
        this.frame.setTitle("Bura");

        this.panel.add(cardToBeat);
        this.panel.add(trumpSuit);


        this.trumpSuit.setText("Trump Suit");
        this.trumpSuit.setHorizontalTextPosition(SwingConstants.CENTER);
        this.trumpSuit.setVerticalTextPosition(SwingConstants.TOP);
        this.trumpSuit.setFont(new Font("Serif", Font.PLAIN, 24));


        this.cardToBeat.setText("Card To Beat");
        this.cardToBeat.setHorizontalTextPosition(SwingConstants.CENTER);
        this.cardToBeat.setVerticalTextPosition(SwingConstants.TOP);
        this.cardToBeat.setFont(new Font("Serif", Font.PLAIN, 24));
        ImageIcon icon = new ImageIcon("src/main/resources/cards/back-blue.png");
        this.cardToBeat.setIcon(icon);


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
     * This method should change the current user to <>username</> and then informers the user of this change.
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
     * This method should display the top card to the user and indicate that this is the card to beat.
     *
     * @param card a string representation of the card to beat.
     */
    @Override
    public void showCardToBeat(String card) {
        ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(card));
        this.cardToBeat.setIcon(icon);
        this.update();
    }

    /**
     * This method should display to the user which suit is the trump suit.
     *
     * @param trump a char representation of a suit
     */
    @Override
    public void showTrumpSuit(char trump) {
        String trumpString = "a" + String.valueOf(trump).toLowerCase();
        ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(trumpString));
        this.trumpSuit.setIcon(icon);
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
            ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(cards_strings[i]));
            this.buttons[i] = new JButton(icon);

            this.buttons[i].setActionCommand(cards_strings[i]);
            this.buttons[i].addActionListener(this);

            this.panel.add(this.buttons[i]);
        }

        //This code sets up the window itself and should be done last
        this.update();
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
     * This method should close the GUI when called.
     */
    @Override
    public void close() {
        this.frame.dispose();
    }

    /**
     * This method should display a <>message</> to the user and then close the GUI.
     *
     * @param message a string that is to be sent to the user.
     */
    @Override
    public void closeMessage(String message) {
        this.sendPopup(message);
        this.close();
    }
}
