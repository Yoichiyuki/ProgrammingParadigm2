import java.awt.*;
import javax.swing.ImageIcon;

public class Player {

    int x;
    int y;
    int width = 40;
    int height = 40;
    int speed = 5;
    Color color;
    int maxHP = 100;
    int hp = 100;
    int maxBullets = 8;
    int bulletsLeft = 8;
    // 2 seconds cooldown
    long cooldown = 2000;

    // Stores last reload time
    long lastReloadTime = System.currentTimeMillis();

    // ============================================
    // PLAYER SPRITES
    // ============================================

    Image idleSprite;
    Image walkSprite;
    Image shootSprite;

    // Current sprite being used
    Image currentSprite;

    // ============================================
    // MOVEMENT FLAGS
    // ============================================
    boolean up;
    boolean down;
    boolean left;
    boolean right;

    // ============================================
    // PLAYER DIRECTION
    // ============================================
    // LEFT PLAYER shoots RIGHT
    // RIGHT PLAYER shoots LEFT
    int bulletDirection;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Player(int x, int y, Color color, int bulletDirection,
        String idlePath, String walkPath, String shootPath) {

        this.x = x;
        this.y = y;
        this.color = color;
        this.bulletDirection = bulletDirection;


        // ============================================
        // LOAD SPRITES
        // ============================================

        idleSprite = new ImageIcon(idlePath).getImage();
        walkSprite = new ImageIcon(walkPath).getImage();
        shootSprite = new ImageIcon(shootPath).getImage();
        // Default sprite
        currentSprite = idleSprite;

    }

    // ============================================
    // UPDATE PLAYER
    // ============================================

    public void update() {

        // ============================
        // MOVEMENT
        // ============================
        if (up) {
            y -= speed;
        }
        if (down) {
            y += speed;
        }
        if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }

        // ============================================
        // SCREEN BOUNDARIES
        // Prevent players from leaving screen
        // ============================================

        if (x < 0) {
            x = 0;
        }
        if (x + width > 800) {
            x = 800 - width;
        }
        if (y < 0) {
            y = 0;
        }
        if (y + height > 600) {
            y = 600 - height;
        }

        // ============================================
        // BULLET RELOAD SYSTEM
        // Every 2 seconds, restore 1 bullet
        // ============================================
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastReloadTime >= cooldown) {
            if (bulletsLeft < maxBullets) {
                bulletsLeft++;
                lastReloadTime = currentTime;
            }
        }

        // ============================================
        // ANIMATION STATE
        // ============================================

        // Walking
        if (up || down || left || right) {

            currentSprite = walkSprite;
        }

        // Idle
        else {

            currentSprite = idleSprite;
        }

    }

    // ============================================
    // DRAW PLAYER
    // ============================================

    public void draw(Graphics2D g2) {

        g2.drawImage(
                currentSprite,
                x,
                y,
                width,
                height,
                null
        );
    }

    // ============================================
    // CREATE BULLET
    // ============================================

    public Bullet shoot() {
        
        currentSprite = shootSprite;
        // Cannot shoot if no bullets
        if (bulletsLeft <= 0) {
            return null;
        }

        // Reduce ammo
        bulletsLeft--;

        // Bullet spawn position
        int bulletX;
        int bulletY = y + height / 2;
        // LEFT PLAYER
        if (bulletDirection == 1) {

            bulletX = x + width;
        }

        // RIGHT PLAYER
        else {
            bulletX = x - 10;
        }
        return new Bullet(bulletX, bulletY, bulletDirection, this);
    }

    // ============================================
    // PLAYER HITBOX
    // ============================================
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // ============================================
    // GET COOLDOWN PERCENT
    // Used for cooldown bar
    // ============================================

    public double getCooldownPercent() {

        long currentTime = System.currentTimeMillis();

        long elapsed = currentTime - lastReloadTime;

        // Clamp value so it doesn't exceed cooldown
        if (elapsed > cooldown) {
            elapsed = cooldown;
        }

        // Convert to percentage
        return (double) elapsed / cooldown;
    }
}