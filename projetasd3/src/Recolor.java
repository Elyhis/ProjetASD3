import java.awt.*;

public class Recolor extends Point {
    private final Color c;

    // Constructor
    // Complexity : O(1)
    public Recolor(int x, int y, Color c) {
        super(x, y);
        this.c = c;
    }

    // Getters
    // Complexity : O(1)
    public Point getPoint() {
        return new Point(getX(), getY());
    }

    // Complexity : O(1)
    public Color getColor() {
        return c;
    }
}
