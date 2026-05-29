import java.awt.*;

public interface GameObject {

    void update(boolean paused);

    void draw(Graphics2D g2);

    Rectangle getBounds();
}