import java.awt.*;

public class Bullet {

    int x;
    int y;

    int width = 12;
    int height = 6;

    int speed = 10;

    int direction;

    Player owner;

    boolean active = true;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Bullet(
            int x,
            int y,
            int direction,
            Player owner
    ) {

        this.x = x - 40;
        this.y = y - 20;

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

        g2.fillRect(
                x,
                y,
                width,
                height
        );
    }

    // ============================================
    // BULLET HITBOX
    // ============================================

    public Rectangle getBounds() {

        return new Rectangle(
                x,
                y,
                width,
                height
        );
    }
}