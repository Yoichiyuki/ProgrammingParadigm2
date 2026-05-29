import java.awt.*;

public class Bullet extends Entity {

    // ============================================
    // BULLET SETTINGS
    // ============================================

    int speed = 10;
    int direction;

    boolean active = true;

    // ============================================
    // OWNER
    // ============================================

    Player owner;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Bullet(int x, int y, int direction, Player owner) {

        super(x - 40, y - 20, 12, 6);

        this.direction = direction;
        this.owner = owner;
    }

    // ============================================
    // UPDATE BULLET
    // ============================================

    public void update(boolean paused) {

        if (paused) return;

        x += speed * direction;

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
}