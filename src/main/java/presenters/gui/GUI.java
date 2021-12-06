package presenters.gui;

import javax.swing.*;
import java.util.Map;
import java.awt.*;
import java.util.HashMap;

public class GUI {

    protected final JFrame frame = new JFrame();

    protected final JPanel panel = new JPanel();

    protected final Map<String, String> stringToImage;

    /**
     * This creates a new instance of GUI. This will just make a blank window appear. For more detailed GUI's, see this
     * classes subclasses.
     */
    private GUI() {

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

        this.panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        this.frame.setTitle("GUI");
        update();
    }

    /**
     * This method updates the window and redraws everything on the screen. This should be called whenever you want
     * changes to be seen by the user.
     */
    protected void update() {
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.repaint();
        this.frame.setVisible(true);
    }

}
