import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChooseChar extends JFrame implements ActionListener {

    // ============================================
    // FRAME
    // ============================================
    final int WIDTH = 800;
    final int HEIGHT = 600;

    // ============================================
    // MAIN GAME
    // ============================================
    MainGame mainGame;

    // ============================================
    // COMPONENTS
    // ============================================
    JPanel mainPanel;

    JLabel titleLabel;

    JLabel player1Label;
    JLabel player2Label;

    JToggleButton batoButton1;
    JToggleButton trillButton1;

    JToggleButton batoButton2;
    JToggleButton trillButton2;

    JButton startButton;

    // ============================================
    // CHARACTER SPRITES
    // ============================================
    CharacterSprites bato;
    CharacterSprites trill;

    // ============================================
    // PLAYER CHOICES
    // ============================================
    CharacterSprites player1Choice;
    CharacterSprites player2Choice;

    // ============================================
    // CONSTRUCTOR
    // ============================================
    public ChooseChar(MainGame mainGame) {

        this.mainGame = mainGame;

        loadCharacters();

        setTitle("Choose Character");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 51, 51));

        // ============================================
        // TITLE
        // ============================================
        titleLabel = new JLabel();
        titleLabel.setIcon(new ImageIcon("assets/chooseursenator.gif"));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setBounds(0, 20, 800, 50);

        titleLabel.setFont(
                new Font(
                        "Yu Gothic UI",
                        Font.BOLD,
                        32
                )
        );

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // ============================================
        // PLAYER LABELS
        // ============================================
        player1Label = new JLabel();
        player1Label.setIcon(new ImageIcon("assets/player1.gif"));

        player1Label.setBounds(74, 52, 250, 150);

        player1Label.setFont(
                new Font(
                        "Yu Gothic UI",
                        Font.BOLD,
                        28
                )
        );

        player1Label.setForeground(Color.WHITE);

        player1Label.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        player2Label = new JLabel();
        player2Label.setIcon(new ImageIcon("assets/player2.gif"));  

        player2Label.setBounds(480, 52, 250, 150);

        player2Label.setFont(
                new Font(
                        "Yu Gothic UI",
                        Font.BOLD,
                        28
                )
        );

        player2Label.setForeground(Color.WHITE);

        player2Label.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // ============================================
        // PLAYER 1
        // ============================================
        batoButton1 = new JToggleButton();

        batoButton1.setBounds(40, 150, 150, 260);

       // batoButton1.setIcon(new ImageIcon("src/Bato.jpg"));

        batoButton1.addActionListener(this);

        trillButton1 = new JToggleButton();

        trillButton1.setBounds(210, 150, 150, 260);

        //trillButton1.setIcon(new ImageIcon("src/Trill.jpg"));

        trillButton1.addActionListener(this);

        // ============================================
        // PLAYER 2
        // ============================================
        batoButton2 = new JToggleButton();

        batoButton2.setBounds(440, 150, 150, 260);

       // batoButton2.setIcon(  new ImageIcon("src/Bato.jpg"));

        batoButton2.addActionListener(this);

        trillButton2 = new JToggleButton();

        trillButton2.setBounds(610, 150, 150, 260);

      //  trillButton2.setIcon(  new ImageIcon("src/Trill.jpg"));

        trillButton2.addActionListener(this);

        // ============================================
        // START BUTTON
        // ============================================
        startButton = new JButton("START MATCH");

        startButton.setBounds(250, 470, 300, 60);

        startButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        startButton.setFocusable(false);

        startButton.addActionListener(this);

        // ============================================
        // ADD COMPONENTS
        // ============================================
        mainPanel.add(titleLabel);

        mainPanel.add(player1Label);
        mainPanel.add(player2Label);

        mainPanel.add(batoButton1);
        mainPanel.add(trillButton1);

        mainPanel.add(batoButton2);
        mainPanel.add(trillButton2);

        mainPanel.add(startButton);

        add(mainPanel);

        setVisible(true);
    }

    // ============================================
    // ACTIONS
    // ============================================
    @Override
    public void actionPerformed(ActionEvent e) {

        // ============================================
        // PLAYER 1
        // ============================================
        if (e.getSource() == batoButton1) {

            player1Choice = bato;

            batoButton1.setSelected(true);
            trillButton1.setSelected(false);
        }

        if (e.getSource() == trillButton1) {

            player1Choice = trill;

            trillButton1.setSelected(true);
            batoButton1.setSelected(false);
        }

        // ============================================
        // PLAYER 2
        // ============================================
        if (e.getSource() == batoButton2) {

            player2Choice = bato;

            batoButton2.setSelected(true);
            trillButton2.setSelected(false);
        }

        if (e.getSource() == trillButton2) {

            player2Choice = trill;

            trillButton2.setSelected(true);
            batoButton2.setSelected(false);
        }

        // ============================================
        // START MATCH
        // ============================================
        if (e.getSource() == startButton) {

            if (
                    player1Choice == null ||
                    player2Choice == null
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Both players must choose a character!"
                );

                return;
            }

            dispose();

            mainGame.start(
                    player1Choice,
                    player2Choice
            );
        }
    }

    // ============================================
    // LOAD CHARACTERS
    // ============================================
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
                ImageIO.read(new File("assets/Bato/bato-shoot.png"))
            );

            trill = new CharacterSprites(
                ImageIO.read(new File("assets/PlaceHolder/idle.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Left.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Right.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Left_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Left_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Right_Down.png")),
                ImageIO.read(new File("assets/PlaceHolder/walk_Right_Up.png")),
                ImageIO.read(new File("assets/PlaceHolder/idle.png"))
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}