import java.awt.*;
import java.awt.image.BufferedImage;

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

    // ============================================
    // RELOAD SYSTEM
    // ============================================
    long gameTime = 0;
    long cooldown = 2000;

    long lastReloadTime = 0;

    // ============================================
    // PLAYER SPRITES
    // ============================================

    int animationFrame = 0;
    int animationCounter = 0;

    int shootTimer = 0;

    CharacterSprites sprites;

    BufferedImage currentAnimation;
    BufferedImage previousAnimation;

    BufferedImage currentFrame;

    boolean shooting = false;

    // ============================================
    // MOVEMENT FLAGS
    // ============================================

    boolean up;
    boolean down;
    boolean left;
    boolean right;

    // ============================================
    // BULLET DIRECTION
    // ============================================

    int bulletDirection;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Player(
            int x,
            int y,
            Color color,
            int bulletDirection,
            CharacterSprites sprites
    ) {

        this.x = x;
        this.y = y;

        this.color = color;

        this.bulletDirection = bulletDirection;

        this.sprites = sprites;

        currentAnimation = sprites.idle;

        previousAnimation = currentAnimation;

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

    public void update(boolean paused) {

        // ============================================
        // MOVEMENT
        // ============================================
        if (paused) return;
        gameTime += 16; // approx 60 FPS

        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // ============================================
        // SELECT CURRENT ANIMATION
        // ============================================

        if (shooting) {

            currentAnimation = sprites.shootin;

            shootTimer--;

            if (shootTimer <= 0) {
                shooting = false;
            }
        }

        else if (down && !left && !right) {
            currentAnimation = sprites.walkDown;
        }

        else if (up && !left && !right) {
            currentAnimation = sprites.walkUp;
        }

        else if (left && !up && !down) {
            currentAnimation = sprites.walkLeft;
        }

        else if (right && !up && !down) {
            currentAnimation = sprites.walkRight;
        }

        else if (left && down) {
            currentAnimation = sprites.walkLeftDown;
        }

        else if (left && up) {
            currentAnimation = sprites.walkLeftUp;
        }

        else if (right && down) {
            currentAnimation = sprites.walkRightDown;
        }

        else if (right && up) {
            currentAnimation = sprites.walkRightUp;
        }

        else {
            currentAnimation = sprites.idle;
        }

        // ============================================
        // RESET ANIMATION WHEN CHANGING STATES
        // ============================================

        if (currentAnimation != previousAnimation) {

            animationFrame = 0;
            animationCounter = 0;

            previousAnimation = currentAnimation;
        }

        // ============================================
        // SCREEN BOUNDARIES
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
        // RELOAD SYSTEM
        // ============================================
        if (lastReloadTime == 0) {
            lastReloadTime = gameTime;
        }

        if (gameTime - lastReloadTime >= cooldown) {

            if (bulletsLeft < maxBullets) {
                bulletsLeft++;
                lastReloadTime = gameTime;
            }
        }
        // ============================================
        // ANIMATION LOOP
        // ============================================

        animationCounter++;

        if (animationCounter >= 10) {

            animationFrame++;

            int totalFrames =
                    currentAnimation.getWidth() / FRAME_WIDTH;

            if (animationFrame >= totalFrames) {
                animationFrame = 0;
            }

            animationCounter = 0;
        }

        // ============================================
        // GET CURRENT FRAME
        // ============================================

        int frameX = animationFrame * FRAME_WIDTH;

        if (frameX + FRAME_WIDTH > currentAnimation.getWidth()) {

            frameX = 0;
            animationFrame = 0;
        }

        currentFrame = currentAnimation.getSubimage(
                frameX,
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
    // SHOOT BULLET
    // ============================================

    public Bullet shoot(Boolean paused) {
        if (paused) return null;
        if (bulletsLeft <= 0) {
            return null;
        }

        shooting = true;

        shootTimer = 15;

        animationFrame = 0;
        animationCounter = 0;

        bulletsLeft--;

        int bulletX;

        int bulletY = y + height / 2;

        if (bulletDirection == 1) {

            bulletX = x + width;
        }

        else {

            bulletX = x + 80;
        }

        return new Bullet(
                bulletX,
                bulletY,
                bulletDirection,
                this
        );
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
    // COOLDOWN PERCENT
    // ============================================

    public double getCooldownPercent() {

        long elapsed = gameTime - lastReloadTime;

        if (elapsed > cooldown) {
            elapsed = cooldown;
        }

        return (double) elapsed / cooldown;
    }
}