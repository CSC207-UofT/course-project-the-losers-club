package presenters.gui;

import usecases.IOInterfaces.WarIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class implements the WarIO interface using Swing to create a GUI to play the game.
 */
public class WarGUI extends GUI implements WarIO, ActionListener {

    private volatile boolean flip = false;
    private final JButton flipButton = new JButton();
    private HashMap<String, JLabel> piles = null;
    private final JLabel pileSize = new JLabel();

    /**
     * This creates a new GUI window through which the user will interact with to play War.
     */
    public WarGUI(){
        super();

        this.frame.setTitle("War");

        this.flipButton.addActionListener(this);
        this.flipButton.setText("Flip!");
        this.flipButton.setActionCommand("flip");


        this.update();
    }



    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("flip")){
            this.flip = true;
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
     * This method should display the top of both of the War piles.
     *
     * @param card1     a string representation of the card on the first War pile to be displayed.
     * @param card2     a string representation of the card on the second War pile to be displayed.
     * @param pileSize  an int representation of the size of the War piles.
     * @param username1 a string representation of the one of the player's username.
     * @param username2 a string representation of the other player's username.
     */
    @Override
    public void displayBoard(String card1, String card2, int pileSize, String username1, String username2) {
        card1 = card1.toLowerCase();
        card2 = card2.toLowerCase();

        if(this.piles == null){
            this.piles = new HashMap<>();
            Font font = new Font("Serif", Font.PLAIN, 24);


            ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(card1));
            JLabel first = new JLabel(icon);
            first.setText(username1);
            first.setFont(font);
            first.setHorizontalTextPosition(SwingConstants.CENTER);
            first.setVerticalTextPosition(SwingConstants.TOP);
            this.piles.put(username1, first);


            icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(card2));
            JLabel second = new JLabel(icon);
            second.setFont(font);
            second.setText(username2);
            second.setHorizontalTextPosition(SwingConstants.CENTER);
            second.setVerticalTextPosition(SwingConstants.TOP);
            this.piles.put(username2, second);


            this.pileSize.setFont(new Font("Serif", Font.PLAIN, 50));
            this.pileSize.setText(String.valueOf(pileSize));

            this.panel.add(this.pileSize);
            this.panel.add(this.piles.get(username1));
            this.panel.add(flipButton);
            this.panel.add(this.piles.get(username2));

        }
        else{
            ImageIcon icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(card1));
            this.piles.get(username1).setIcon(icon);

            icon = new ImageIcon("src/main/resources/cards/" + this.stringToImage.get(card2));
            this.piles.get(username2).setIcon(icon);

            this.pileSize.setText(String.valueOf(pileSize));

            if(card1.equals("") && card2.equals("")){
                icon = new ImageIcon("src/main/resources/cards/back-blue.png");
                this.piles.get(username1).setIcon(icon);

                this.piles.get(username2).setIcon(icon);

                this.pileSize.setText(String.valueOf(pileSize));
            }
        }


        this.update();
    }

    /**
     * This method takes a keyboard or mouse input from the user and continues the game once the input is received.
     * Acts to stall the game so that the players can look at the current state of the game.
     */
    @Override
    public void stall() {
        while (!this.flip){
            Thread.onSpinWait();
        }
        this.flip = false;
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
