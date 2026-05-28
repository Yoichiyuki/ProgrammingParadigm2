import java.awt.*;
import javax.swing.*;

public class PauseMenu extends JPanel {

    public PauseMenu(GamePanel gamePanel) {

        setLayout(null);
        setBackground(new Color(0, 0, 0, 180));

        JLabel label = new JLabel("PAUSED");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setBounds(330, 120, 200, 50);
        add(label);

        JButton resumeBtn = new JButton("Resume");
        resumeBtn.setBounds(320, 200, 140, 40);
        resumeBtn.addActionListener(e -> gamePanel.togglePause());
        add(resumeBtn);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setBounds(320, 260, 140, 40);
        restartBtn.addActionListener(e -> gamePanel.restartGame());
        add(restartBtn);

        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(320, 320, 140, 40);
        quitBtn.addActionListener(e -> System.exit(0));
        add(quitBtn);

        setVisible(false);
    }

    public void setPaused(boolean state) {
        setVisible(state);
    }
}