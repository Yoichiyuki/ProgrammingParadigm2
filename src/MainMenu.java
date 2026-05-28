import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

    MainGame mainGame;
    Image background;
    JLabel titleLabel;
    JButton playButton, settingsButton, creditsButton, quitButton;

    public MainMenu(MainGame mainGame) {
        this.mainGame = mainGame;

        setLayout(null);
        background = new ImageIcon("assets/background.gif").getImage();


        int btnW = 220;
        int btnH = 60;
        int x = 50;

        playButton = new JButton();
        playButton.setIcon(new ImageIcon("assets/buttons/play.png"));
        playButton.setRolloverIcon(new ImageIcon("assets/buttons/playpress.png"));
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(false);
        playButton.setBounds(x, 180, btnW, btnH);
        playButton.addActionListener(this);

        creditsButton = new JButton();
        creditsButton.setIcon(new ImageIcon("assets/buttons/credits1.png"));
        creditsButton.setRolloverIcon(new ImageIcon("assets/buttons/hovecredits.png"));
        creditsButton.setBorderPainted(false);
        creditsButton.setContentAreaFilled(false);
        creditsButton.setFocusPainted(false);
        creditsButton.setOpaque(false);
        creditsButton.setBounds(x, 270, btnW, btnH);
        creditsButton.addActionListener(this);

        quitButton = new JButton();
        quitButton.setIcon(new ImageIcon("assets/buttons/quit.png"));
        quitButton.setRolloverIcon(new ImageIcon("assets/buttons/quitpress.png"));
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false);
        quitButton.setOpaque(false);
        quitButton.setBounds(x, 370, btnW, btnH);
        quitButton.addActionListener(this);

        playButton.setFocusable(false);
        creditsButton.setFocusable(false);
        quitButton.setFocusable(false);

        
        add(playButton);
        add(creditsButton);
        add(quitButton);

        setOpaque(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (background != null) {
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("Background is NULL!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == playButton) {
            mainGame.goToMapSelection(); // 🔥 FIXED
        }

        if (e.getSource() == settingsButton) {
            System.out.println("SETTINGS");
        }

        if (e.getSource() == creditsButton) {
            System.out.println("CREDITS");
            GameCredits creditsScreen = new GameCredits();
            creditsScreen.setVisible(true);
        }

        if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}