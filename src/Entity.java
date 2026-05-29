import java.awt.*;

public abstract class Entity {

    protected int x;
    protected int y;

    protected int width;
    protected int height;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Entity(
            int x,
            int y,
            int width,
            int height
    ) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    // ============================================
    // HITBOX
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