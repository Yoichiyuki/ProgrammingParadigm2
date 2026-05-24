import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    // ============================================
    // SCREEN SETTINGS
    // ============================================
    Image backgroundImage;
    final int WIDTH = 800;
    final int HEIGHT = 600;
    Thread gameThread;

    // ============================================
    // CREATE PLAYERS
    // ============================================
    Player player1 = new Player(
        100,
        HEIGHT / 2 - 20,
        Color.BLUE,
        1
    );
    
        Player player2 = new Player(
        660,
        HEIGHT / 2 - 20,
        Color.RED,
        -1
    );

    // ============================================
    // BULLET STORAGE
    // ============================================
    ArrayList<Bullet> bullets = new ArrayList<>();

    // ============================================
    // GAME STATE
    // ============================================
    boolean gameOver = false;
    String winnerText = "";

    // ============================================
    // CONSTRUCTOR
    // ============================================
    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        backgroundImage = new ImageIcon("assets/backGround.png").getImage();
    }

    // ============================================
    // START THREAD
    // ============================================
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // ============================================
    // GAME LOOP
    // ============================================
    @Override
    public void run() {
        while (gameThread != null) {

            update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ============================================
    // UPDATE GAME
    // ============================================
    public void update() {
        // Stop updates if game over
        if (gameOver) {
            return;
        }

        // ============================================
        // UPDATE PLAYERS
        // ============================================

        player1.update();
        player2.update();

        // ============================================
        // UPDATE BULLETS
        // ============================================
        for (int i = 0; i < bullets.size(); i++) {

            Bullet bullet = bullets.get(i);
            bullet.update();
            // ============================================
            // COLLISION WITH PLAYER 1
            // ============================================
            if (bullet.owner != player1 &&
                    bullet.getBounds().intersects(player1.getBounds())) {

                player1.hp -= 10;
                bullet.active = false;
            }

            // ============================================
            // COLLISION WITH PLAYER 2
            // ============================================
            if (bullet.owner != player2 &&
                    bullet.getBounds().intersects(player2.getBounds())) {

                player2.hp -= 10;
                bullet.active = false;
            }

            // ============================================
            // REMOVE BULLET
            // ============================================
            if (!bullet.active) {

                bullets.remove(i);
                i--;
            }
        }

        // ============================================
        // CHECK WINNER
        // ============================================
        if (player1.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 2 WINS!";
        }

        if (player2.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 1 WINS!";
        }
    }

// ============================================
// DRAW EVERYTHING
// ============================================

@Override
protected void paintComponent(Graphics g) {

    

    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    // ============================================
    // DRAW BACKGROUND
    // ============================================

    g2.drawImage(
            backgroundImage,
            0,
            0,
            WIDTH,
            HEIGHT,
            null
    );
    // ============================================
    // DRAW PLAYERS
    // ============================================

    // ============================================
    // MIDDLE DIVIDER
    // ============================================

    g2.setColor(Color.GRAY);

    g2.fillRect(WIDTH / 2 - 5, 0, 10, HEIGHT);

    player1.draw(g2);
    player2.draw(g2);

    // ============================================
    // DRAW BULLETS
    // ============================================

    for (Bullet bullet : bullets) {
        bullet.draw(g2);
    }

    // ============================================
    // PLAYER 1 HUD
    // ============================================

    drawHUD(g2, player1, 20, 20);

    // ============================================
    // PLAYER 2 HUD
    // ============================================

    drawHUD(g2, player2, 580, 20);

    // ============================================
    // GAME OVER TEXT
    // ============================================

    if (gameOver) {

        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Arial", Font.BOLD, 40));

        g2.drawString(winnerText, 230, 300);
    }
    g2.dispose();

}

    // ============================================
    // DRAW PLAYER HUD
    // ============================================
    public void drawHUD(Graphics2D g2, Player player, int startX, int startY) {

        // ============================================
        // HP BAR
        // ============================================

        int hpBarWidth = 180;
        int hpBarHeight = 18;

        // Background
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(startX, startY, hpBarWidth, hpBarHeight);
        // Current HP
        g2.setColor(Color.GREEN);
        int currentHPWidth =(int)((double) player.hp / player.maxHP * hpBarWidth);
        g2.fillRect(startX, startY, currentHPWidth, hpBarHeight);

        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(startX, startY, hpBarWidth, hpBarHeight);

        // ============================================
        // BULLET BAR
        // ============================================
        int bulletX = startX;
        int bulletY = startY + 30;
        int bulletSize = 18;
        int spacing = 8;
        for (int i = 0; i < player.maxBullets; i++) {

            // Filled bullet = available ammo
            if (i < player.bulletsLeft) {

                g2.setColor(Color.YELLOW);
            }
            // Empty bullet
            else {
                g2.setColor(Color.GRAY);
            }
            g2.fillRect(
                    bulletX + (i * (bulletSize + spacing)),
                    bulletY,
                    bulletSize,
                    bulletSize
            );
            // Border
            g2.setColor(Color.WHITE);
            g2.drawRect(
                    bulletX + (i * (bulletSize + spacing)),
                    bulletY,
                    bulletSize,
                    bulletSize
            );
        }

        // ============================================
        // COOLDOWN BAR
        // ============================================
        int cooldownY = bulletY + 35;
        int cooldownWidth = 180;
        int cooldownHeight = 12;
        // Background
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(startX, cooldownY, cooldownWidth, cooldownHeight);
        // Current cooldown progress
        g2.setColor(Color.CYAN);
        int currentCooldownWidth =
                (int)(player.getCooldownPercent() * cooldownWidth);

        g2.fillRect(
                startX,
                cooldownY,
                currentCooldownWidth,
                cooldownHeight
        );
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(
                startX,
                cooldownY,
                cooldownWidth,
                cooldownHeight
        );
    }

    // ============================================
    // KEY PRESSED
    // ============================================
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        // ============================================
        // PLAYER 1 MOVEMENT
        // ============================================
        if (code == KeyEvent.VK_W) {
            player1.up = true;
        }

        if (code == KeyEvent.VK_S) {
            player1.down = true;
        }

        if (code == KeyEvent.VK_A) {
            player1.left = true;
        }

        if (code == KeyEvent.VK_D) {
            player1.right = true;
        }

        // ============================================
        // PLAYER 1 SHOOT (SPACE)
        // ============================================
        if (code == KeyEvent.VK_SPACE) {

            Bullet bullet = player1.shoot();
            if (bullet != null) {

                bullets.add(bullet);
            }
        }

        // ============================================
        // PLAYER 2 MOVEMENT (ARROW KEYS)
        // ============================================
        if (code == KeyEvent.VK_UP) {
            player2.up = true;
        }

        if (code == KeyEvent.VK_DOWN) {
            player2.down = true;
        }

        if (code == KeyEvent.VK_LEFT) {
            player2.left = true;
        }

        if (code == KeyEvent.VK_RIGHT) {
            player2.right = true;
        }

        // ============================================
        // PLAYER 2 SHOOT (NUMPAD 0)
        // ============================================

        if (code == KeyEvent.VK_NUMPAD0) {
            Bullet bullet = player2.shoot();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }else if (code == KeyEvent.VK_ENTER) {
            Bullet bullet = player2.shoot();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }
    }

    // ============================================
    // KEY RELEASED
    // ============================================
    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        // PLAYER 1

        if (code == KeyEvent.VK_W) {
            player1.up = false;
        }

        if (code == KeyEvent.VK_S) {
            player1.down = false;
        }

        if (code == KeyEvent.VK_A) {
            player1.left = false;
        }

        if (code == KeyEvent.VK_D) {
            player1.right = false;
        }

        // PLAYER 2

        if (code == KeyEvent.VK_UP) {
            player2.up = false;
        }

        if (code == KeyEvent.VK_DOWN) {
            player2.down = false;
        }

        if (code == KeyEvent.VK_LEFT) {
            player2.left = false;
        }

        if (code == KeyEvent.VK_RIGHT) {
            player2.right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}