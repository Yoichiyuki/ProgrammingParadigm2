import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // ============================================
    // GAME STATE
    // ============================================
    PauseMenu pauseMenu;
    boolean paused = false;

    boolean gameOver = false;
    String winnerText = "";

    // ============================================
    // SCREEN SETTINGS
    // ============================================
    Image backgroundImage;
    final int WIDTH = 800;
    final int HEIGHT = 600;

    // ============================================
    // GAME LOOP THREAD
    // ============================================
    Thread gameThread;

    // ============================================
    // MAP / COLLISION
    // ============================================
    final int HUD_TOP_MARGIN = 80;
    Rectangle divider;
    Rectangle topBarrier;

    // ============================================
    // PLAYERS
    // ============================================
    Player player1;
    Player player2;

    // ============================================
    // AUDIO
    // ============================================
    SoundManager soundManager = new SoundManager();

    // ============================================
    // BULLETS
    // ============================================
    ArrayList<Bullet> bullets = new ArrayList<>();

    // ============================================
    // CONSTRUCTOR
    // ============================================
    public GamePanel(
            String mapPath,
            CharacterSprites player1Sprites,
            CharacterSprites player2Sprites
    ) {

        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        backgroundImage = new ImageIcon(mapPath).getImage();

        // divider (middle wall)
        divider = new Rectangle(WIDTH / 2 - 5, 0, 10, HEIGHT);
        // top barrier (for maps with upper walls)
        topBarrier = new Rectangle(0, 0, WIDTH, 100);

        // players
        player1 = new Player(100, 300, Color.BLUE, 1, player1Sprites);
        player2 = new Player(600, 300, Color.RED, -1, player2Sprites);

        soundManager.loop("assets/sounds/circusMusic.wav");

        // pause menu overlay
        pauseMenu = new PauseMenu(this);
        pauseMenu.setBounds(0, 0, WIDTH, HEIGHT);
        pauseMenu.setVisible(false);
        add(pauseMenu);

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    // ============================================
    // START GAME LOOP
    // ============================================
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update(paused);
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ============================================
    // UPDATE GAME LOGIC
    // ============================================
    public void update(boolean paused) {

        if (gameOver || paused) return;

        player1.update(paused, divider, HUD_TOP_MARGIN);
        player2.update(paused, divider, HUD_TOP_MARGIN);

        // bullet system
        for (int i = 0; i < bullets.size(); i++) {

            Bullet b = bullets.get(i);
            b.update(paused);

            // collision: player1
            if (b.owner != player1 && b.getBounds().intersects(player1.getBounds())) {
                player1.hp -= 10;
                soundManager.play("assets/sounds/ouch.wav");
                b.active = false;
            }

            // collision: player2
            if (b.owner != player2 && b.getBounds().intersects(player2.getBounds())) {
                player2.hp -= 10;
                soundManager.play("assets/sounds/ouch.wav");
                b.active = false;
            }

            if (!b.active) {
                bullets.remove(i);
                i--;
            }
        }

        // win conditions
        if (player1.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 1 LOST!";
            soundManager.stop();
            soundManager.play("assets/sounds/lordnabahala2.wav");
            gameOver = true;
        }

        if (player2.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 2 LOST!";
            soundManager.stop();
            soundManager.play("assets/sounds/lordnabahala2.wav");
            gameOver = true;
        }
    }

    // ============================================
    // RENDERING
    // ============================================
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // background
        g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        // divider
        g2.setColor(Color.GRAY);
        g2.fillRect(WIDTH / 2 - 5, 0, 10, HEIGHT);

        // players
        player1.draw(g2);
        player2.draw(g2);

        // bullets
        for (Bullet b : bullets) {
            b.draw(g2);
        }

        // HUD
        drawHUD(g2, player1, 20, 20, false);
        drawHUD(g2, player2, WIDTH - 280, 20, true);

        // game over text
        if (gameOver) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString(winnerText, 230, 300);
        }
    }

    // ============================================
    // HUD (HEALTH, AMMO, COOLDOWN)
    // ============================================
    public void drawHUD(Graphics2D g2, Player p, int x, int y, boolean flipped) {

        int w = 260;
        int h = 20;

        // HP BAR
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, w, h);

        g2.setColor(Color.GREEN);
        g2.fillRect(x, y, (int)((double)p.hp / p.maxHP * w), h);

        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, w, h);

        // AMMO BAR
        int by = y + 30;
        int bs = 18;
        int sp = 8;

        for (int i = 0; i < p.maxBullets; i++) {

            int drawX = flipped
                    ? x + (p.maxBullets - 1 - i) * (bs + sp)
                    : x + i * (bs + sp);

            g2.setColor(i < p.bulletsLeft ? Color.YELLOW : Color.GRAY);
            g2.fillRect(drawX, by, bs, bs);

            g2.setColor(Color.WHITE);
            g2.drawRect(drawX, by, bs, bs);
        }

        // COOLDOWN BAR
        int cy = by + 35;

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, cy, w, 12);

        g2.setColor(Color.CYAN);
        g2.fillRect(x, cy, (int)(p.getCooldownPercent() * w), 12);

        g2.setColor(Color.WHITE);
        g2.drawRect(x, cy, w, 12);
    }

    // ============================================
    // INPUT HANDLING
    // ============================================
    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        // PLAYER 1 MOVEMENT
        if (c == KeyEvent.VK_W) player1.up = true;
        if (c == KeyEvent.VK_S) player1.down = true;
        if (c == KeyEvent.VK_A) player1.left = true;
        if (c == KeyEvent.VK_D) player1.right = true;

        // PLAYER 1 SHOOT
        if (c == KeyEvent.VK_SPACE) {
            Bullet b = player1.shoot(paused);
            if (!paused && b != null) bullets.add(b);
            if (b != null) soundManager.play("assets/sounds/gunshot.wav");
        }

        // PLAYER 2 MOVEMENT
        if (c == KeyEvent.VK_UP) player2.up = true;
        if (c == KeyEvent.VK_DOWN) player2.down = true;
        if (c == KeyEvent.VK_LEFT) player2.left = true;
        if (c == KeyEvent.VK_RIGHT) player2.right = true;

        // PLAYER 2 SHOOT
        if (c == KeyEvent.VK_ENTER || c == KeyEvent.VK_NUMPAD0) {
            Bullet b = player2.shoot(paused);
            if (!paused && b != null) bullets.add(b);
            if (b != null) soundManager.play("assets/sounds/gunshot.wav");
        }

        // PAUSE
        if (c == KeyEvent.VK_ESCAPE) togglePause();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int c = e.getKeyCode();

        // PLAYER 1 STOP
        if (c == KeyEvent.VK_W) player1.up = false;
        if (c == KeyEvent.VK_S) player1.down = false;
        if (c == KeyEvent.VK_A) player1.left = false;
        if (c == KeyEvent.VK_D) player1.right = false;

        // PLAYER 2 STOP
        if (c == KeyEvent.VK_UP) player2.up = false;
        if (c == KeyEvent.VK_DOWN) player2.down = false;
        if (c == KeyEvent.VK_LEFT) player2.left = false;
        if (c == KeyEvent.VK_RIGHT) player2.right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // ============================================
    // GAME CONTROL
    // ============================================
    public void restartGame() {
        player1.hp = player1.maxHP;
        player2.hp = player2.maxHP;

        player1.x = 100;
        player1.y = 300;

        player2.x = 600;
        player2.y = 300;

        player1.bulletsLeft = player1.maxBullets;
        player2.bulletsLeft = player2.maxBullets;

        player1.resetCooldown();
        player2.resetCooldown();

        bullets.clear();

        gameOver = false;
        paused = false;

        pauseMenu.setVisible(false);

        requestFocusInWindow();
    }

    public void togglePause() {
        paused = !paused;
        pauseMenu.setVisible(paused);
        requestFocusInWindow();

        if(paused) {
            soundManager.stop();
        } else {
            soundManager.loop("assets/sounds/circusMusic.wav");
        }

    }
}