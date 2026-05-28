import javax.swing.*;

public class MainGame {

    public MainGame() {
        chooseChar();
    }

    public void start(
            CharacterSprites player1Sprites,
            CharacterSprites player2Sprites
    ) {

        GamePanel gamePanel =
                new GamePanel(player1Sprites, player2Sprites);

        JFrame window = new JFrame();

        window.setTitle("2 Player Arena Shooter");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

    public void chooseChar() {

        ChooseChar chooseChar = new ChooseChar(this);

        chooseChar.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGame();
    }
}
