package presenters.gui;

import controllers.MainMenuIO;
import helpers.UsernameCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements the MainMenuIO interface using Swing to create a GUI to display stats to the user.
 */
public class MainMenuGUI extends GUI implements MainMenuIO, ActionListener {

    private final JTextField input = new JTextField(20);
    private List<String> games;
    private volatile boolean ready = false;
    private JButton[] gameButtons;
    private int sel;
    private final JButton userStatButton;

    /**
     * This creates a new UserDisplayGUI object. This creates a new window with the user will interact with.
     */
    public MainMenuGUI() {
        super();

        this.frame.setTitle("Main Menu");

        this.userStatButton = new JButton();
        this.userStatButton.setText("USER STATISTICS");
        this.userStatButton.setActionCommand("userStat");

        this.update();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();

        int sel = this.games.indexOf(event);
        if (sel != -1) {
            this.ready = true;
            this.sel = sel;
        }

        if (event.equals("userStat")) {
            this.ready = true;
            this.sel = 9;
        }
    }

    /**
     * This method should close the GUI when called.
     */
    @Override
    public void close() {
        this.frame.dispose();
    }

    /**
     * Retrieve the user's selection.
     *
     * @param games array of games for the menu
     * @return an integer representing the selection
     */
    @Override
    public int getUserSelection(String[] games) {
        if (this.gameButtons == null) {
            this.games = Arrays.asList(games);

            this.gameButtons = new JButton[this.games.size()];
            for (int i = 0; i < games.length; i++) {
                JButton button = new JButton();
                button.setText(this.games.get(i));
                button.setActionCommand(this.games.get(i));
                button.addActionListener(this);

                gameButtons[i] = button;
                this.panel.add(button, BorderLayout.PAGE_START);
            }

            this.panel.add(this.userStatButton, BorderLayout.PAGE_END);
        }

        while (!this.ready) {
            Thread.onSpinWait();
        }
        this.ready = false;
        return this.sel;
    }

    /**
     * Retrieve a list of unique usernames that are to be playing the selected game.
     *
     * @param minNum
     * @param maxNum
     * @return a valid username representation. The length must be between
     * <code>minNum</code> (inclusive) and <code>maxNum</code> (inclusive)
     * @see UsernameCheck
     */
    @Override
    public List<String> getUsernames(int minNum, int maxNum) {
        return null;
    }

    /**
     * Send a popup to the user containing <code>message</code>.
     *
     * @param message the message to send to the user
     */
    @Override
    public void sendPopup(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
}
