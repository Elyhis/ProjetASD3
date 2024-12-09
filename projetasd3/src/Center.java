import java.awt.*;
import java.util.ArrayList;

public class Center {

    private ArrayList<Color> colors;
    private Point p;

    // Constructor
    // Complexity : O(1)
    Center(int x, int y, ArrayList<Color> colors) {
        this.p = new Point(x, y);
        this.colors = colors;
    }

    // Getters
    // Complexity : O(1)
    public ArrayList<Color> getColors() {
        return colors;
    }

    // Complexity : O(1)
    public Color getColor(int colorNumber) {
        return colors.get(colorNumber);
    }

    // Complexity : O(1)
    public Point getPoint() {
        return p;
    }
}