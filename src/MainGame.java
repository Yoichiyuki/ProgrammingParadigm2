import java.awt.*;
import javax.swing.*;

public class MainGame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    public MainGame() {
        setTitle("Senator Pew Pew Pew");
        setSize(800, 600); // Using the slightly larger size to fit the map menu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Setup CardLayout
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        add(mainContainer);

        // Load the very first screen: Map Selection
        mainContainer.add(new chooseMap(this), "MAP_SELECTION");

        setVisible(true);
    }

    // Called by chooseMap when a map is clicked
    public void goToCharacterSelection(String selectedMapPath) {
        ChooseChar charPanel = new ChooseChar(this, selectedMapPath);
        mainContainer.add(charPanel, "CHAR_SELECTION");
        cardLayout.show(mainContainer, "CHAR_SELECTION");
    }

    // Called by ChooseChar when "START MATCH" is clicked
    public void start(String mapPath, CharacterSprites p1, CharacterSprites p2) {
        GamePanel gamePanel = new GamePanel(mapPath, p1, p2);
        mainContainer.add(gamePanel, "GAME");
        cardLayout.show(mainContainer, "GAME");
        
        // CRITICAL: We must request focus so the KeyListener works for player movement!
        gamePanel.requestFocusInWindow(); 
        
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGame());
    }
}