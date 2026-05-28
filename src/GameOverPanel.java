import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameOverPanel extends JPanel {
    private BufferedImage backgroundImage;
    private List<Point> bulletHoles;
    private Random random;
    private Font customFont;
    private MainGame mainGame; // Added reference to the master window
    
    // Animation states
    private int flashAlpha = 0;
    private int offsetX = 0;
    private int offsetY = 0;

    public GameOverPanel(MainGame mainGame) {
        this.mainGame = mainGame;
        setLayout(null); 
        bulletHoles = new ArrayList<>();
        random = new Random();
        
        try {
        backgroundImage = ImageIO.read(new File("assets/gameOver.png"));
    } catch (Exception e) {
        System.out.println("Failed to load background image");
        backgroundImage = null;
    }

        try {
           customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/PressStart2P-Regular.ttf")).deriveFont(30f);
        } catch (Exception e) {
            System.out.println("Could not load custom font. Using fallback.");
            customFont = new Font("Monospaced", Font.BOLD, 30);
        }

        RetroButton retryBtn = new RetroButton("pew pew pew?", customFont);
        retryBtn.setBounds(80, 250, 600, 50);
        
        RetroButton returnMenuBtn = new RetroButton("no no no?", customFont);
        returnMenuBtn.setBounds(80, 320, 600, 50);

        retryBtn.addActionListener(e -> {
            fireGunshot();
            Timer hideTimer = new Timer(300, evt -> {
                bulletHoles.clear();
                repaint();
                mainGame.returnToMenu(); // Flips back to the Map Selection screen!
            });
            hideTimer.setRepeats(false);
            hideTimer.start();
        });

        returnMenuBtn.addActionListener(e -> {
            bulletHoles.clear();
            repaint();
            System.exit(0); // Quits the game if they choose "no no no?"
        });

        add(retryBtn);
        add(returnMenuBtn);
    }

    public void triggerGameOver(String loser) {
        bulletHoles.clear();
        spawnBulletHoles(50, 100);
    }

    private void spawnBulletHoles(int min, int max) {
        int targetHoles = random.nextInt(max - min + 1) + min;
        Timer spawnTimer = new Timer(30, null);
        spawnTimer.addActionListener(e -> {
            if (bulletHoles.size() >= targetHoles) {
                spawnTimer.stop();
                return;
            }
            int x = random.nextInt(750) + 20;
            int y = random.nextInt(550) + 20;
            bulletHoles.add(new Point(x, y));
            repaint();
        });
        spawnTimer.start();
    }

    private void fireGunshot() {
        Timer shakeTimer = new Timer(40, null);
        long startTime = System.currentTimeMillis();
        shakeTimer.addActionListener(e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed > 200) {
                offsetX = 0;
                offsetY = 0;
                shakeTimer.stop();
                repaint();
            } else {
                offsetX = random.nextInt(30) - 15;
                offsetY = random.nextInt(30) - 15;
                repaint();
            }
        });
        shakeTimer.start();

        flashAlpha = 220; 
        Timer flashTimer = new Timer(20, null);
        flashTimer.addActionListener(e -> {
            flashAlpha -= 25;
            if (flashAlpha <= 0) {
                flashAlpha = 0;
                flashTimer.stop();
            }
            repaint();
        });
        flashTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(offsetX, offsetY);

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, 800, 600, null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 800, 600);
        }

        for (Point p : bulletHoles) {
            g2d.setColor(new Color(0, 0, 0, 200)); 
            g2d.fillOval(p.x, p.y, 14, 14);
            g2d.setColor(Color.BLACK); 
            g2d.fillOval(p.x + 2, p.y + 2, 10, 10);
            g2d.setColor(new Color(255, 255, 255, 100)); 
            g2d.drawArc(p.x, p.y, 14, 14, 225, 90);
        }

        g2d.setFont(customFont.deriveFont(70f));
        String title = "GAME OVER";
        int titleX = 80;
        int titleY = 150;

        g2d.setColor(new Color(43, 34, 0)); 
        g2d.drawString(title, titleX + 8, titleY + 8);
        
        g2d.setColor(new Color(138, 109, 0)); 
        g2d.drawString(title, titleX + 4, titleY + 4);
        
        g2d.setColor(new Color(255, 215, 64)); 
        g2d.drawString(title, titleX, titleY);

        if (flashAlpha > 0) {
            g2d.translate(-offsetX, -offsetY); 
            if (flashAlpha < 150) {
                g2d.setColor(new Color(255, 215, 64, flashAlpha)); 
            } else {
                g2d.setColor(new Color(255, 255, 255, flashAlpha));
            }
            g2d.fillRect(0, 0, 800, 600);
        }
    }
}

// Custom RetroButton remains exactly the same!
class RetroButton extends JButton {
    private boolean isHovered = false;
    private boolean isPressed = false;
    private Color normalColor = new Color(255, 215, 64);
    private Color hoverColor = Color.WHITE;
    private Color shadow1 = new Color(138, 109, 0);
    private Color shadow2 = new Color(43, 34, 0);
    private Color hoverShadow1 = new Color(204, 0, 0);
    private Color hoverShadow2 = new Color(74, 0, 0);

    public RetroButton(String text, Font font) {
        super(text);
        setFont(font);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.LEFT);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            @Override
            public void mousePressed(MouseEvent e) { isPressed = true; repaint(); }
            @Override
            public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (isPressed) g2d.scale(0.98, 0.98);
        else if (isHovered) g2d.scale(1.02, 1.02);

        int textY = getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 4;
        Color currentShadow1 = isHovered ? hoverShadow1 : shadow1;
        Color currentShadow2 = isHovered ? hoverShadow2 : shadow2;
        
        g2d.setColor(currentShadow2);
        g2d.drawString(getText(), 6, textY + 6);
        g2d.setColor(currentShadow1);
        g2d.drawString(getText(), 3, textY + 3);
        g2d.setColor(isHovered ? hoverColor : normalColor);
        g2d.drawString(getText(), 0, textY);
    }
}