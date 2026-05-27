import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {


    PauseMenu pauseMenu;
    boolean paused = false;

    Image backgroundImage;
    final int WIDTH = 800;
    final int HEIGHT = 600;

    Thread gameThread;

    CharacterSprites bato;

    Player player1;
    Player player2;

    ArrayList<Bullet> bullets = new ArrayList<>();

    boolean gameOver = false;
    String winnerText = "";

    public GamePanel() {
        pauseMenu = new PauseMenu(this);
        this.setLayout(null);
        this.add(pauseMenu);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        backgroundImage = new ImageIcon("assets/backGround.png").getImage();

        try {

            bato = new CharacterSprites(
                ImageIO.read(new File("assets/bato-idle.png")),
                ImageIO.read(new File("assets/bato-walk_Down.png")),
                ImageIO.read(new File("assets/bato-walk_Up.png")),
                ImageIO.read(new File("assets/bato-walk_Left.png")),
                ImageIO.read(new File("assets/bato-walk_Right.png")),
                ImageIO.read(new File("assets/bato-walk_Left_Down.png")),
                ImageIO.read(new File("assets/bato-walk_Left_Up.png")),
                ImageIO.read(new File("assets/bato-walk_Right_Down.png")),
                ImageIO.read(new File("assets/bato-walk_Right_Up.png")),
                ImageIO.read(new File("assets/bato-shoot.png"))
            );

            player1 = new Player(100, 300, Color.BLUE, 1, bato);
            player2 = new Player(700, 300, Color.RED, -1, bato);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void update(boolean paused) {
        if (gameOver || paused) return;

        player1.update(paused);
        player2.update(paused);

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update(paused);

            if (b.owner != player1 && b.getBounds().intersects(player1.getBounds())) {
                player1.hp -= 10;
                b.active = false;
            }

            if (b.owner != player2 && b.getBounds().intersects(player2.getBounds())) {
                player2.hp -= 10;
                b.active = false;
            }

            if (!b.active) {
                bullets.remove(i);
                i--;
            }
        }

        if (player1.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 2 WINS!";
        }

        if (player2.hp <= 0) {
            gameOver = true;
            winnerText = "PLAYER 1 WINS!";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        g2.setColor(Color.GRAY);
        g2.fillRect(WIDTH / 2 - 5, 0, 10, HEIGHT);

        player1.draw(g2);
        player2.draw(g2);
    
        for (Bullet b : bullets) {
            b.draw(g2);
        }
        
        
         drawHUD(g2, player1, 20, 20);
         drawHUD(g2, player2, 580, 20);
        

        if (gameOver) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString(winnerText, 230, 300);
        }
    }

    public void drawHUD(Graphics2D g2, Player p, int x, int y) {

        int w = 180, h = 18;

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, w, h);

        g2.setColor(Color.GREEN);
        g2.fillRect(x, y, (int)((double)p.hp / p.maxHP * w), h);

        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, w, h);

        int by = y + 30, bs = 18, sp = 8;

        for (int i = 0; i < p.maxBullets; i++) {

            g2.setColor(i < p.bulletsLeft ? Color.YELLOW : Color.GRAY);

            g2.fillRect(x + i * (bs + sp), by, bs, bs);

            g2.setColor(Color.WHITE);

            g2.drawRect(x + i * (bs + sp), by, bs, bs);
        }

        int cy = by + 35;

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, cy, w, 12);

        g2.setColor(Color.CYAN);
        g2.fillRect(x, cy, (int)(p.getCooldownPercent() * w), 12);

        g2.setColor(Color.WHITE);
        g2.drawRect(x, cy, w, 12);
    }

    @Override public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_W) player1.up = true;
        if (c == KeyEvent.VK_S) player1.down = true;
        if (c == KeyEvent.VK_A) player1.left = true;
        if (c == KeyEvent.VK_D) player1.right = true;

        if (c == KeyEvent.VK_SPACE) {
            Bullet b = player1.shoot(paused);
            if(!paused){
                if (b != null) bullets.add(b);
            }
        }

        if (c == KeyEvent.VK_UP) player2.up = true;
        if (c == KeyEvent.VK_DOWN) player2.down = true;
        if (c == KeyEvent.VK_LEFT) player2.left = true;
        if (c == KeyEvent.VK_RIGHT) player2.right = true;

        if (c == KeyEvent.VK_NUMPAD0 || c == KeyEvent.VK_ENTER) {
            Bullet b = player2.shoot(paused);
            if(!paused){
            if (b != null) bullets.add(b);
            }
        }

        if (c == KeyEvent.VK_ESCAPE) {
            togglePause();
        }
    }

    @Override public void keyReleased(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_W) player1.up = false;
        if (c == KeyEvent.VK_S) player1.down = false;
        if (c == KeyEvent.VK_A) player1.left = false;
        if (c == KeyEvent.VK_D) player1.right = false;

        if (c == KeyEvent.VK_UP) player2.up = false;
        if (c == KeyEvent.VK_DOWN) player2.down = false;
        if (c == KeyEvent.VK_LEFT) player2.left = false;
        if (c == KeyEvent.VK_RIGHT) player2.right = false;
    }

    public void togglePause() {
        paused = !paused;
        pauseMenu.setPaused(paused);
    }
    @Override public void keyTyped(KeyEvent e) {}
}