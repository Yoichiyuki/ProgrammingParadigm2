import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameCredits extends JFrame {

    // ============================================
    // WINDOW SETTINGS
    // ============================================

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // ============================================
    // SCROLL SETTINGS
    // ============================================

    private int yOffset = HEIGHT;

    private Timer scrollTimer;

    // ============================================
    // CREDITS TEXT
    // ============================================

    private final String creditsText =
            "<html>" +
            "<body style='text-align: center; font-family: sans-serif; color: white; width: 550px;'>" +

            // ============================================
            // TITLE SECTION
            // ============================================

            "<h1 style='color: #FFCC00; font-size: 28px; margin-bottom: 5px;'>CREDITS</h1>" +

            "<h2 style='color: #FF3333; font-size: 20px; margin-top: 0px;'>" +
            "Senator Pew Pew Pew:<br>" +
            "Senate The Pandemonium" +
            "</h2>" +

            "<br><br>" +

            // ============================================
            // ABOUT SECTION
            // ============================================

            "<h3 style='color: #44CCFF; font-size: 16px;'>ABOUT THE GAME</h3>" +

            "<p style='font-size: 12px; line-height: 1.5; text-align: justify;'>" +

            "\"Senator Pew Pew Pew: Senate The Pandemonium\" is a fast-paced, " +
            "competitive 2D top-down shooter for two players, built completely " +
            "from scratch using Java Swing. Instead of going with generic sci-fi " +
            "soldiers or space marines, we wanted to create something completely " +
            "unique, local, and funny. We decided to use real-world political " +
            "figures as our playable characters and went through public broadcasts " +
            "to chop up real voice clips for the game's sound effects. Every time " +
            "you move, select a character, or fire a shot, you are hearing the " +
            "actual voices of the politicians, making the gameplay experience " +
            "incredibly loud and funny." +

            "</p>" +

            "<br>" +

            "<p style='font-size: 12px; line-height: 1.5; text-align: justify;'>" +

            "The subtitle \"Pandemonium\" was chosen very carefully for this " +
            "project. By definition, pandemonium means a state of wild uproar, " +
            "total chaos, or noisy confusion, which perfectly describes a crazy " +
            "situation where an environment completely loses control because of " +
            "excitement or anger. Our main goal was to take a satirical look at " +
            "what is actually going on in our current political climate, but " +
            "package it in a fun, arcade-style format that people can actually " +
            "laugh at and enjoy. It is chaotic, loud, and highly competitive, " +
            "matching the exact energy of a real senate floor in the middle of " +
            "a heated debate." +

            "</p>" +

            "<br><br>" +

            // ============================================
            // CONTROLS SECTION
            // ============================================

            "<h3 style='color: #44CCFF; font-size: 16px;'>HOW IT WORKS</h3>" +

            "<table style='width: 100%; color: white; font-size: 12px; " +
            "border-collapse: collapse; margin-bottom: 15px;'>" +

            "<tr style='background-color: #222; color: #FFCC00;'>" +
            "<th style='padding: 5px;'>Player</th>" +
            "<th style='padding: 5px;'>Movement</th>" +
            "<th style='padding: 5px;'>Fire</th>" +
            "</tr>" +

            "<tr>" +
            "<td style='padding: 5px;'><b>Player 1 (Left)</b></td>" +
            "<td style='padding: 5px;'>W, A, S, D</td>" +
            "<td style='padding: 5px;'>Spacebar</td>" +
            "</tr>" +

            "<tr>" +
            "<td style='padding: 5px;'><b>Player 2 (Right)</b></td>" +
            "<td style='padding: 5px;'>↑, ↓, ←, → Keys</td>" +
            "<td style='padding: 5px;'>Enter</td>" +
            "</tr>" +

            "</table>" +

            "<br>" +

            // ============================================
            // MOVEMENT FEATURES
            // ============================================

            "<h3 style='color: #44CCFF; font-size: 14px;'>MOVEMENT TRICKS</h3>" +

            "<p style='font-size: 12px; text-align: left; margin-left: 20px;'>" +

            "• <b>8-Directional Movement:</b> Hold two keys simultaneously " +
            "to run diagonally.<br>" +

            "• <b>Anti-Ghosting Design:</b> Isolated inputs prevent key jamming " +
            "between players.<br>" +

            "• <b>Multifunctional Button:</b> Press Escape to pause, Resume, " +
            "Restart, or Quit." +

            "</p>" +

            "<br><br>" +

            // ============================================
            // VOICES & MUSIC
            // ============================================

            "<h3 style='color: #44CCFF; font-size: 16px;'>" +
            "VOICES & MUSIC CREDITS" +
            "</h3>" +

            "<p style='font-size: 13px; margin-bottom: 2px;'>" +
            "<b>Featured Voices:</b>" +
            "</p>" +

            "<p style='font-size: 12px; margin-top: 0px;'>" +
            "Senator Ronald \"Bato\" Dela Rosa<br>" +
            "Former Senator Antonio \"Sonny\" Trillanes IV" +
            "</p>" +

            "<br>" +

            "<p style='font-size: 13px; margin-bottom: 2px;'>" +
            "<b>Audio & BGM:</b>" +
            "</p>" +

            "<p style='font-size: 12px; margin-top: 0px; line-height: 1.4;'>" +

            "Tap Sound 1 — wewinxd1<br>" +
            "Circus Theme Song — Solo Tu Radio Fm<br>" +
            "Ouch oof Hurt Sound Effect — Homemade_SFX" +

            "</p>" +

            "<br><br>" +

            // ============================================
            // SPECIAL THANKS
            // ============================================

            "<h3 style='color: #FFCC00; font-size: 15px;'>" +
            "SPECIAL THANKS" +
            "</h3>" +

            "<p style='font-size: 12px; font-style: italic; line-height: 1.4;'>" +

            "Public news broadcasts, senate speech archives, and classic " +
            "16-bit top-down arcade asset creators for providing our design inspiration." +

            "</p>" +

            "<br><br><br><br>" +

            "<h2 style='color: #FFCC00;'>" +
            "THANK YOU FOR PLAYING!" +
            "</h2>" +

            "</body></html>";

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public GameCredits() {

        // ============================================
        // FRAME SETUP
        // ============================================

        setTitle(
                "Senator Pew Pew Pew: Senate The Pandemonium - Credits"
        );

        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        // ============================================
        // CREDITS PANEL
        // ============================================

        CreditsPanel creditsPanel = new CreditsPanel();

        add(creditsPanel);

        // ============================================
        // SCROLL TIMER
        // ============================================

        scrollTimer = new Timer(
                25,
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        yOffset--;

                        // RESET SCROLL POSITION

                        if (yOffset < -1100) {
                            yOffset = HEIGHT;
                        }

                        creditsPanel.repaint();
                    }
                }
        );

        scrollTimer.start();
    }

    // ============================================
    // CREDITS PANEL CLASS
    // ============================================

    private class CreditsPanel extends JPanel {

        private final JLabel label;

        // ============================================
        // CONSTRUCTOR
        // ============================================

        public CreditsPanel() {

            setBackground(Color.BLACK);

            setLayout(null);

            label = new JLabel(creditsText);

            label.setSize(710, 1200);

            add(label);
        }

        // ============================================
        // DRAW PANEL
        // ============================================

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            int xOffset =
                    (getWidth() - label.getWidth()) / 2;

            label.setLocation(
                    xOffset,
                    yOffset
            );
        }
    }

    // ============================================
    // MAIN METHOD
    // ============================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new GameCredits().setVisible(true);
        });
    }
}

