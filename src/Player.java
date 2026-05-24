import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    int x;
    int y;
    int width = 96;
    int height = 128;
    final int FRAME_WIDTH = 48;
    final int FRAME_HEIGHT = 64;
    double speed = 3.75;
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
    int animationFrame = 0;
    int animationCounter = 0;

    int shootTimer = 0;
    boolean shooting = false;
    BufferedImage idle;
    BufferedImage walkDown;
    BufferedImage walkUp;
    BufferedImage walkLeft;
    BufferedImage walkLeftDown;
    BufferedImage walkLeftUp;
    BufferedImage walkRight;
    BufferedImage walkRightDown;
    BufferedImage walkRightUp;

    BufferedImage currentAnimation;
    BufferedImage currentFrame;

    

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

    public Player(int x, int y, Color color, int bulletDirection) {

        this.x = x;
        this.y = y;
        this.color = color;
        this.bulletDirection = bulletDirection;


        // ============================================
        // LOAD SPRITES
        // ============================================
        try {
            idle = ImageIO.read(new File("assets/walk.png"));
            walkDown = ImageIO.read(new File("assets/walk_Down.png"));
            walkUp = ImageIO.read(new File("assets/walk_Up.png"));

            walkLeft = ImageIO.read(new File("assets/walk_Left.png"));

            walkLeftDown = ImageIO.read(
                new File("assets/walk_Left_Down.png")
            );

            walkLeftUp = ImageIO.read(
                new File("assets/walk_Left_Up.png")
            );

            walkRight = ImageIO.read(new File("assets/walk_Right.png"));

            walkRightDown = ImageIO.read(
                new File("assets/walk_Right_Down.png")
            );

            walkRightUp = ImageIO.read(
                new File("assets/walk_Right_Up.png")
            );



        } catch (IOException e) {
            e.printStackTrace();
        }
        currentAnimation = idle;
        currentFrame = currentAnimation.getSubimage(
        0,
        0,
        FRAME_WIDTH,
        FRAME_HEIGHT
    );
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
        // SELECT CURRENT ANIMATION
        // ============================================

        // Idle
        currentAnimation = idle;

        // Down
        if (down && !left && !right) {
            currentAnimation = walkDown;
        }

        // Up
        if (up && !left && !right) {
            currentAnimation = walkUp;
        }

        // Left ONLY
        if (left && !up && !down) {
            currentAnimation = walkLeft;
        }

        // Right ONLY
        if (right && !up && !down) {
            currentAnimation = walkRight;
        }

        // Left Down
        if (left && down) {
            currentAnimation = walkLeftDown;
        }

        // Left Up
        if (left && up) {
            currentAnimation = walkLeftUp;
        }

        // Right Down
        if (right && down) {
            currentAnimation = walkRightDown;
        }

        // Right Up
        if (right && up) {
            currentAnimation = walkRightUp;
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
        // ANIMATION LOOP
        // ============================================

        animationCounter++;

        if (animationCounter >= 10) {

            animationFrame++;

            if (animationFrame >= 8) {
                animationFrame = 0;
            }

            animationCounter = 0;
        }

        currentFrame = currentAnimation.getSubimage(
            animationFrame * FRAME_WIDTH,
            0,
            FRAME_WIDTH,
            FRAME_HEIGHT
        );

    }

    // ============================================
    // DRAW PLAYER
    // ============================================

    public void draw(Graphics2D g2) {

        g2.drawImage(
                currentFrame,
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
        

        // Cannot shoot if no bullets
        if (bulletsLeft <= 0) {
            return null;
        }

        shooting = true;
        shootTimer = 15;

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
         return new Rectangle(
        x + 34,
        y + 38,
        30,
        48
    );
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