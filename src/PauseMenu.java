import java.awt.*;
import javax.swing.*;

public class PauseMenu extends JPanel {

    public PauseMenu(GamePanel gamePanel) {

        setLayout(null);
        setBackground(new Color(0, 0, 0, 180));

        JLabel label = new JLabel("PAUSED");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setBounds(310, 120, 200, 50);
        add(label);

        JButton resumeBtn = new JButton("Resume");
        resumeBtn.setBounds(320, 200, 150, 40);
        resumeBtn.setIcon(new ImageIcon("assets/buttons/pauseRe1.png"));
        resumeBtn.setRolloverIcon(new ImageIcon("assets/buttons/pauseRe.png"));
        resumeBtn.setFocusable(false);
        resumeBtn.setBorderPainted(false);
        resumeBtn.setContentAreaFilled(false);
        resumeBtn.setFocusPainted(false);
        resumeBtn.addActionListener(e -> gamePanel.togglePause());
        add(resumeBtn);

        JButton restartBtn = new JButton("Restart");
        restartBtn.setBounds(320, 260, 150, 40);
        restartBtn.setIcon(new ImageIcon("assets/buttons/pauseR1.png"));
        restartBtn.setRolloverIcon(new ImageIcon("assets/buttons/pauseR.png"));
        restartBtn.setFocusable(false);
        restartBtn.setBorderPainted(false);
        restartBtn.setContentAreaFilled(false);
        restartBtn.setFocusPainted(false);
        restartBtn.addActionListener(e -> gamePanel.restartGame());
        add(restartBtn);

        JButton quitBtn = new JButton("Quit");
        quitBtn.setBounds(320, 320, 150, 40);
        quitBtn.setIcon(new ImageIcon("assets/buttons/pauseQ1.png"));
        quitBtn.setRolloverIcon(new ImageIcon("assets/buttons/pauseQ.png"));
        quitBtn.setFocusable(false);
        quitBtn.setBorderPainted(false);
        quitBtn.setContentAreaFilled(false);
        quitBtn.setFocusPainted(false);
        quitBtn.addActionListener(e -> System.exit(0));
        add(quitBtn);

        setVisible(false);
    }

    public void setPaused(boolean state) {
        setVisible(state);
    }
}