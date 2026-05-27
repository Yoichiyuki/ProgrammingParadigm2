import java.awt.*;
import javax.swing.*;

public class PauseMenu extends JPanel {

    private boolean visibleState = false;

    public PauseMenu(GamePanel gamePanel) {

        setLayout(null);
        setBounds(300, 200, 200, 200);
        setBackground(new Color(0, 0, 0, 180));

        // Title
        JLabel label = new JLabel("PAUSED");
        label.setForeground(Color.WHITE);
        label.setBounds(60, 20, 100, 30);
        add(label);

        // Resume button
        JButton resumeBtn = new JButton("Resume");
        resumeBtn.setBounds(40, 70, 120, 30);
        resumeBtn.addActionListener(e -> gamePanel.togglePause());
        add(resumeBtn);

        // Quit button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(40, 110, 120, 30);
        quitBtn.addActionListener(e -> System.exit(0));
        add(quitBtn);

        setVisible(false);
    }

    public void setPaused(boolean state) {
        setVisible(state);
    }
}