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
        background = new ImageIcon("assets/batoMenu.gif").getImage();

        System.out.println(background);
        titleLabel = new JLabel("SENATORS PEW PEW PEW");
        titleLabel.setBounds(200, 40, 400, 60);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        int btnW = 220;
        int btnH = 60;
        int x = 50;

        playButton = new JButton("PLAY");
        playButton.setBounds(x, 150, btnW, btnH);
        playButton.addActionListener(this);

        settingsButton = new JButton("SETTINGS");
        settingsButton.setBounds(x, 240, btnW, btnH);
        settingsButton.addActionListener(this);

        creditsButton = new JButton("CREDITS");
        creditsButton.setBounds(x, 330, btnW, btnH);
        creditsButton.addActionListener(this);

        quitButton = new JButton("QUIT");
        quitButton.setBounds(x, 420, btnW, btnH);
        quitButton.addActionListener(this);

        playButton.setFocusable(false);
        settingsButton.setFocusable(false);
        creditsButton.setFocusable(false);
        quitButton.setFocusable(false);

        
        add(titleLabel);
        add(playButton);
        add(settingsButton);
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
        }

        if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}