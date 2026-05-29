import java.awt.*;
import javax.swing.*;

public class MainGame extends JFrame {

    // ============================================
    // CARD LAYOUT
    // ============================================
    public CardLayout cardLayout;
    public JPanel mainContainer;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public MainGame() {

        setTitle("Senator Pew Pew Pew");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ============================================
        // MAIN CONTAINER
        // ============================================

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        add(mainContainer);

        // ============================================
        // SCREENS
        // ============================================

        mainContainer.add(
                new MainMenu(this),
                "MAIN_MENU"
        );

        mainContainer.add(
                new chooseMap(this),
                "MAP_SELECTION"
        );

        // ============================================
        // START SCREEN
        // ============================================

        cardLayout.show(
                mainContainer,
                "MAIN_MENU"
        );

        setVisible(true);
    }

    // ============================================
    // GO TO MAP SELECTION
    // ============================================

    public void goToMapSelection() {

        cardLayout.show(
                mainContainer,
                "MAP_SELECTION"
        );
    }

    // ============================================
    // GO TO CHARACTER SELECTION
    // ============================================

    public void goToCharacterSelection(String selectedMapPath) {

        ChooseChar charPanel = new ChooseChar(
                this,
                selectedMapPath
        );

        mainContainer.add(
                charPanel,
                "CHAR_SELECTION"
        );

        cardLayout.show(
                mainContainer,
                "CHAR_SELECTION"
        );
    }

    // ============================================
    // START GAME
    // ============================================

    public void start(
            String mapPath,
            CharacterSprites p1,
            CharacterSprites p2,
            boolean p1FlipShoot,
            boolean p2FlipShoot
    ) {

        GamePanel gamePanel = new GamePanel(
                this,
                mapPath,
                p1,
                p2,
                p1FlipShoot,
                p2FlipShoot
        );

        mainContainer.add(gamePanel,"GAME");
        cardLayout.show(mainContainer,"GAME");
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }

    // ============================================
    // RETURN TO MENU
    // ============================================

    public void returnToMenu() {
        cardLayout.show(
                mainContainer,
                "MAIN_MENU"
        );
    }

    // ============================================
    // MAIN
    // ============================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGame::new);
    }
}