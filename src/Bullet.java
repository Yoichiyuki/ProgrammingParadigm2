import java.awt.*;

public class Bullet {

    // Bullet position
    int x;
    int y;

    // Bullet size
    int width = 12;
    int height = 6;

    // Bullet movement speed
    int speed = 10;

    // Direction
    // 1 = RIGHT
    // -1 = LEFT
    int direction;

    // Bullet owner
    // Used so players don't damage themselves
    Player owner;

    // Bullet active state
    boolean active = true;

    // ============================================
    // CONSTRUCTOR
    // ============================================
    public Bullet(int x, int y, int direction, Player owner) {

        this.x = x - 40;
        this.y = y;
        this.direction = direction;

        this.owner = owner;
    }

    // ============================================
    // UPDATE BULLET
    // ============================================
    public void update() {
        // Move bullet horizontally
        x += speed * direction;

        // Remove bullet if outside screen
        if (x < -50 || x > 850) {
            active = false;
        }
    }

    // ============================================
    // DRAW BULLET
    // ============================================
    public void draw(Graphics2D g2) {

        g2.setColor(Color.YELLOW);

        g2.fillRect(x, y, width, height);
    }

    // ============================================
    // COLLISION BOX
    // ============================================
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}