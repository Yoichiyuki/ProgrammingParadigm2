import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

// 1. Changed to extends JPanel
public class ChooseChar extends JPanel implements ActionListener {

    MainGame mainGame;
    SoundManager soundManager = new SoundManager();
    String selectedMapPath; // Store the map choice!

    // COMPONENTS
    JLabel titleLabel, player1Label, player2Label;
    JToggleButton batoButton1, trillButton1, batoButton2, trillButton2;
    JButton startButton;

    // SPRITES & CHOICES
    CharacterSprites bato, trill;
    CharacterSprites player1Choice, player2Choice;

    // 2. Constructor receives the mapPath
    public ChooseChar(MainGame mainGame, String mapPath) {
        this.mainGame = mainGame;
        this.selectedMapPath = mapPath;

        loadCharacters();

        // Setup JPanel
        setLayout(null);
        setBackground(new Color(0, 51, 51));

        // TITLE
        titleLabel = new JLabel();
        titleLabel.setIcon(new ImageIcon("assets/chooseursenator.gif"));
        titleLabel.setBounds(0, 20, 800, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // PLAYER LABELSaa
        ImageIcon p1Icon = new ImageIcon("assets/selectChar/player1.gif");
        ImageIcon p2Icon = new ImageIcon("assets/selectChar/player2.gif");

       player1Label = new JLabel(p1Icon);
        player2Label = new JLabel(p2Icon);

        player1Label.setBounds(74, 52, 250, 150);
        player2Label.setBounds(480, 52, 250, 150); 

        // PLAYER 1 BUTTONS
        batoButton1 = new JToggleButton();
        batoButton1.setBounds(40, 150, 150, 260);
        batoButton1.setIcon(new ImageIcon("assets/selectChar/batoSelect.png"));
        batoButton1.addActionListener(this);

        trillButton1 = new JToggleButton();
        trillButton1.setBounds(210, 150, 150, 260);
        trillButton1.setIcon(new ImageIcon("assets/selectChar/trillSelect.png"));
        trillButton1.addActionListener(this);

        // PLAYER 2 BUTTONS
        batoButton2 = new JToggleButton();
        batoButton2.setBounds(440, 150, 150, 260);
        batoButton2.setIcon(new ImageIcon("assets/selectChar/batoSelect.png"));
        batoButton2.addActionListener(this);

        trillButton2 = new JToggleButton();
        trillButton2.setBounds(610, 150, 150, 260);
        trillButton2.setIcon(new ImageIcon("assets/selectChar/trillSelect.png"));
        trillButton2.addActionListener(this);

        // START BUTTON
        startButton = new JButton();
        startButton.setIcon(new ImageIcon("assets/buttons/startMatch.png"));
        startButton.setRolloverIcon(new ImageIcon("assets/buttons/startMatchHover.png"));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setBounds(250, 470, 300, 60);
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        // ADD COMPONENTS
        add(titleLabel);
        add(player1Label);
        add(player2Label);
        add(batoButton1);
        add(trillButton1);
        add(batoButton2);
        add(trillButton2);
        add(startButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // PLAYER 1
        if (e.getSource() == batoButton1) {
            soundManager.play("assets/sounds/runforyourlife2.wav");
            trillButton1.setIcon(new ImageIcon("assets/selectChar/trillSelect.png"));
            player1Choice = bato;
            batoButton1.setSelected(true);
            batoButton1.setIcon(new ImageIcon("assets/selectChar/batoSelected.png"));
            trillButton1.setSelected(false);
        }

        if (e.getSource() == trillButton1) {
            soundManager.play("assets/sounds/trillchoose2.wav");
            batoButton1.setIcon(new ImageIcon("assets/selectChar/batoSelect.png"));
            player1Choice = trill;
            trillButton1.setSelected(true);
            trillButton1.setIcon(new ImageIcon("assets/selectChar/trillSelected.png"));   
            batoButton1.setSelected(false);
        }

        // PLAYER 2
        if (e.getSource() == batoButton2) {
            soundManager.play("assets/sounds/runforyourlife2.wav");
            trillButton2.setIcon(new ImageIcon("assets/selectChar/trillSelect.png"));
            player2Choice = bato;
            batoButton2.setSelected(true);
            batoButton2.setIcon(new ImageIcon("assets/selectChar/batoSelected.png"));
            trillButton2.setSelected(false);
        }

        if (e.getSource() == trillButton2) {
            soundManager.play("assets/sounds/trillchoose2.wav");
            batoButton2.setIcon(new ImageIcon("assets/selectChar/batoSelect.png"));
            player2Choice = trill;
            trillButton2.setSelected(true);
            trillButton2.setIcon(new ImageIcon("assets/selectChar/trillSelected.png"));
            batoButton2.setSelected(false);
        }

        // START MATCH
        if (e.getSource() == startButton) {
            if (player1Choice == null || player2Choice == null) {
                JOptionPane.showMessageDialog(this, "Both players must choose a character!");
                return;
            }
            // 3. Call MainGame to launch the arena, passing the map and the characters!
            boolean p1FlipShoot = false;
boolean p2FlipShoot = false;

// If Player 1 uses Trillanes
if (player1Choice == trill) {
    p1FlipShoot = true;
}

// If Player 2 uses Bato
if (player2Choice == bato) {
    p2FlipShoot = true;
}

mainGame.start(
        selectedMapPath,
        player1Choice,
        player2Choice,
        p1FlipShoot,
        p2FlipShoot
);
        }
    }

    public void loadCharacters() {
        try {
            bato = new CharacterSprites(
                ImageIO.read(new File("assets/Bato/bato-idle2.png")),
                ImageIO.read(new File("assets/Bato/bato-walkdown2.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Up.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Left.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Right.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Left_Down.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Left_Up.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Right_Down.png")),
                ImageIO.read(new File("assets/Bato/bato-walk_Right_Up.png")),
                ImageIO.read(new File("assets/Bato/bato-shoot.png")), true
            );

            trill = new CharacterSprites(
                ImageIO.read(new File("assets/PlaceHolder/Idle (1).png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Left.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Right.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Left_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Left_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Right_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Right_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/Walk_Left.png")), false
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}