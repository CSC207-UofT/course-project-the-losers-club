package presenters.gui;

import usecases.IOInterfaces.GameIO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that instantiates a basic GUI. Game specific GUIs extend this class and gain its functionality. This class
 * creates menus and windows which the users will view and interact with
 */
public class GUI implements GameIO {

    protected final JFrame frame = new JFrame();

    protected final JPanel panel = new JPanel();

    private final Map<String, String> stringToImage;

    /**
     * This creates a new instance of GUI. This will just make a blank window appear. For more detailed GUI's, see this
     * classes subclasses.
     */
    GUI() {

        final String[] CARDS_STRINGS = {"as", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "10s", "js", "qs", "ks",
                "ac", "2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "10c", "jc", "qc", "kc",
                "ad", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "10d", "jd", "qd", "kd",
                "ah", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "jh", "qh", "kh"};
        final String[] CARD_IMAGES = {
                "1_spade.png", "2_spade.png", "3_spade.png", "4_spade.png", "5_spade.png", "6_spade.png", "7_spade.png", "8_spade.png", "9_spade.png", "10_spade.png", "jack_spade.png", "queen_spade.png", "king_spade.png",
                "1_club.png", "2_club.png", "3_club.png", "4_club.png", "5_club.png", "6_club.png", "7_club.png", "8_club.png", "9_club.png", "10_club.png", "jack_club.png", "queen_club.png", "king_club.png",
                "1_diamond.png", "2_diamond.png", "3_diamond.png", "4_diamond.png", "5_diamond.png", "6_diamond.png", "7_diamond.png", "8_diamond.png", "9_diamond.png", "10_diamond.png", "jack_diamond.png", "queen_diamond.png", "king_diamond.png",
                "1_heart.png", "2_heart.png", "3_heart.png", "4_heart.png", "5_heart.png", "6_heart.png", "7_heart.png", "8_heart.png", "9_heart.png", "10_heart.png", "jack_heart.png", "queen_heart.png", "king_heart.png"
        };

        //Creates the map from cards as strings to their images file names
        this.stringToImage = new HashMap<>();
        for (int i = 0; i < CARD_IMAGES.length; i++) {
            this.stringToImage.put(CARDS_STRINGS[i], CARD_IMAGES[i]);
        }

        this.panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        this.frame.setTitle("GUI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setBounds(0, 0, (int) (screenSize.width * 0.9), (int) (screenSize.height * 0.9));
        update();
    }

    /**
     * This method updates the window and redraws everything on the screen. This should be called whenever you want
     * changes to be seen by the user.
     */
    protected void update() {
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    /**
     * Takes a string representation of a card and returns the address of the image for that card.
     *
     * @param cardString the String representation of the card you want an image of
     * @return the address of a the image of that card.
     */
    protected String stringToImage(String cardString) {
        if (cardString.equals("blank")) {
            return "src/main/resources/cards/" + "back-blue.png";
        } else {
            return "src/main/resources/cards/" + this.stringToImage.get(cardString);
        }
    }


    /**
     * This method should send a popup to the user containing a <code>message</code>.
     *
     * @param message a string that is to be sent to the user
     */
    public void sendPopup(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    /**
     * This method should display a <code>message</code> to the user and then close the GUI.
     *
     * @param message a string that is to be sent to the user.
     */
    public void closeMessage(String message) {
        this.sendPopup(message);
        this.close();
    }

    /**
     * This method should close the GUI when called.
     */
    public void close() {
        this.frame.dispose();
    }
}
