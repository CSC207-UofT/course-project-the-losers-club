package presenters.gui;

import usecases.IOInterfaces.UserDisplayIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This class implements the UserDisplayIO interface using Swing to create a GUI to display stats to the user.
 */
public class UserDisplayGUI extends GUI implements UserDisplayIO, ActionListener {

    private final JTextField input = new JTextField(20);
    private volatile boolean ready = false;

    /**
     * This creates a new UserDisplayGUI object. This creates a new window with the user will interact with.
     */
    public UserDisplayGUI(){
        super();

        this.frame.setTitle("User Stats");

        this.panel.add(input, BorderLayout.PAGE_START);

        JButton submit = new JButton();
        submit.setText("Submit");
        submit.setActionCommand("submit");
        submit.addActionListener(this);

        this.panel.add(submit, BorderLayout.CENTER);

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
        if(event.equals("submit")){
            this.ready = true;
        }
    }

    /**
     * Prompt the user for a username to get the statistics for.
     *
     * @return a valid username to retrieve various gameplay statistics for
     */
    @Override
    public String getUsername() {
        while(!this.ready){
            Thread.onSpinWait();
        }
        this.ready = false;
        return this.input.getText();
    }

    /**
     * Show a popup telling the user that they have entered an invalid username.
     *
     * @param username the invalid username
     */
    @Override
    public void invalidUsername(String username) {
        String message = "Username " + username + " doesn't exist";

        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    /**
     * Display the specified statistics.
     * <p>
     * Statistics to support (keys in the Map):
     * <ul>
     *     <li>"Games Played"</li>
     *     <li>"Games Won"</li>
     *     <li>"Games Tied"</li>
     *     <li>"Win Percentage"</li>
     * </ul>
     *
     * @param statistics mapping of statistic names to their values. See description for supported statistics.
     */
    @Override
    public void showStats(Map<String, String> statistics) {
        StringBuilder message = new StringBuilder();

        for(String key: statistics.keySet()){
            message.append(key).append(": ").append(statistics.get(key)).append("\n");
        }

        JOptionPane.showMessageDialog(new JFrame(), message.toString());
    }

}
