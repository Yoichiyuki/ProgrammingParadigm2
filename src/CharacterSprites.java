import java.awt.image.BufferedImage;

public class CharacterSprites {

    // ============================================
    // IDLE SPRITE
    // ============================================

    BufferedImage idle;

    // ============================================
    // WALKING SPRITES
    // ============================================

    BufferedImage walkDown;
    BufferedImage walkUp;

    BufferedImage walkLeft;
    BufferedImage walkRight;

    BufferedImage walkLeftDown;
    BufferedImage walkLeftUp;

    BufferedImage walkRightDown;
    BufferedImage walkRightUp;

    // ============================================
    // SHOOTING SPRITE
    // ============================================

    BufferedImage shootin;

    boolean defaultFacingRight;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public CharacterSprites(
            BufferedImage idle,
            BufferedImage walkDown,
            BufferedImage walkUp,
            BufferedImage walkLeft,
            BufferedImage walkRight,
            BufferedImage walkLeftDown,
            BufferedImage walkLeftUp,
            BufferedImage walkRightDown,
            BufferedImage walkRightUp,
            BufferedImage shootin,
            boolean defaultFacingRight
    ) {

        this.idle = idle;

        this.walkDown = walkDown;
        this.walkUp = walkUp;

        this.walkLeft = walkLeft;
        this.walkRight = walkRight;

        this.walkLeftDown = walkLeftDown;
        this.walkLeftUp = walkLeftUp;

        this.walkRightDown = walkRightDown;
        this.walkRightUp = walkRightUp;

        this.shootin = shootin;

        this.defaultFacingRight = defaultFacingRight;
    }
}

