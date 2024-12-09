import java.util.ArrayList;
import java.awt.Color;

public class P3Tree extends Pktree {
    String direction;

    // Constructors
    // Complexity : O(1)
    public P3Tree() {
        super(null, 3);
        direction = "up";
    }

    // Complexity : O(1)
    public P3Tree(Point p) {
        super(p, 3);
        direction = "up";
    }

    // Complexity : O(1)
    public P3Tree(Point p, String direction) {
        super(p, 3);
        this.direction = direction;
    }

    protected Pktree createInstance(Point p) {
        return new P3Tree(p);
    }

    // Getters
    @Override
    protected int getNumberInParent() {
        Point p = this.parent.center;
        String dire = ((P3Tree) this.parent).direction;
        switch (dire) {
            case "up":
                if (center.getY() <= p.getY()) {
                    return 2;
                } else if (center.getX() <= p.getX()) {
                    return 0;
                } else {
                    return 1;
                }
            case "down":
                if (center.getY() > p.getY()) {
                    return 0;
                } else if (center.getX() <= p.getX()) {
                    return 2;
                } else {
                    return 1;
                }
            case "left":
                if (center.getX() > p.getX()) {
                    return 1;
                } else if (center.getY() <= p.getY()) {
                    return 2;
                } else {
                    return 0;
                }
            case "right":
                if (center.getX() <= p.getX()) {
                    return 0;
                } else if (center.getY() <= p.getY()) {
                    return 2;
                } else {
                    return 1;
                }
            default:
                return -1;
        }

    }

    // Complexity : O(1) / same as extends
    public void addQTree(Center c, String dir) {
        super.addQTree(c);
        this.direction = dir;
    }

    /**
     * Add centers one by one to the tree
     * <p>
     * Complexity : O(number of center)
     * 
     * @param centers    list of centers
     * @param directions list of directions because it's P3Tree
     */
    public void buildQTree(ArrayList<Center> centers, ArrayList<String> directions) {
        this.addQTree(centers.get(0), directions.get(0)); // Création du noeud du Pktree
        centers.remove(0);
        directions.remove(0);
        P3Tree currentTree;
        for (int i = 0; i < centers.size(); i++) {

            currentTree = (P3Tree) searchQTree(centers.get(i));
            currentTree.addQTree(centers.get(i), directions.get(i));
        }

    }

    protected Region searchRegion(Point p) {
        Region res;
        switch (direction) {
            case "up":
                if (p.getY() <= center.getY()) {
                    res = searchRegionRecu(p, 2);
                } else if (p.getX() <= center.getX()) {
                    res = searchRegionRecu(p, 0);
                } else {
                    res = searchRegionRecu(p, 1);
                }
                break;
            case "down":
                if (p.getY() > center.getY()) {
                    res = searchRegionRecu(p, 0);
                } else if (p.getX() <= center.getX()) {
                    res = searchRegionRecu(p, 2);
                } else {
                    res = searchRegionRecu(p, 1);
                }
                break;
            case "left":
                if (p.getX() > center.getX()) {
                    res = searchRegionRecu(p, 1);
                } else if (p.getY() <= center.getY()) {
                    res = searchRegionRecu(p, 2);
                } else {
                    res = searchRegionRecu(p, 0);
                }
                break;
            case "right":
                if (p.getX() <= center.getX()) {
                    res = searchRegionRecu(p, 0);
                } else if (p.getY() <= center.getY()) {
                    res = searchRegionRecu(p, 2);
                } else {
                    res = searchRegionRecu(p, 1);
                }
                break;
            default:
                res = new Region();
                break;
        }
        return res;

    }

    //
    protected void toImageRecu(Image im, Point topLeftCorner, Point bottomRightCorner) {
        int strokeOffset = im.getStrokeWeight() / 2;
        int yInverted = im.height() - this.center.getY(); // Compensate upside down Y coordinate in Image

        switch (direction) {
            case ("up"):
                if (getChild(0) != null) {
                    getChild(0).toImageRecu(im, topLeftCorner, new Point(this.center.getX(), yInverted));
                } else {
                    im.setRectangle(topLeftCorner.getX(), this.center.getX(), topLeftCorner.getY(), yInverted,
                            getColor(0));
                }
                if (getChild(1) != null) {
                    getChild(1).toImageRecu(im, new Point(this.center.getX(), topLeftCorner.getY()),
                            new Point(bottomRightCorner.getX(), yInverted));
                } else {
                    im.setRectangle(this.center.getX(), bottomRightCorner.getX(), topLeftCorner.getY(), yInverted,
                            getColor(1));
                }
                if (getChild(2) != null) {
                    getChild(2).toImageRecu(im, new Point(topLeftCorner.getX(), yInverted), bottomRightCorner);
                } else {
                    im.setRectangle(topLeftCorner.getX(), bottomRightCorner.getX(), yInverted, bottomRightCorner.getY(),
                            getColor(2));
                }
                // Vertical stroke
                im.setRectangle(this.center.getX() - strokeOffset, this.center.getX() + strokeOffset + 1,
                        topLeftCorner.getY(), yInverted, Color.BLACK);
                // Horizontal Stroke
                im.setRectangle(topLeftCorner.getX(), bottomRightCorner.getX(), yInverted - strokeOffset,
                        yInverted + strokeOffset + 1, Color.BLACK);
                break;

            case ("down"):
                if (getChild(0) != null) {
                    getChild(0).toImageRecu(im, topLeftCorner, new Point(bottomRightCorner.getX(), yInverted));
                } else {
                    im.setRectangle(topLeftCorner.getX(), bottomRightCorner.getX(), topLeftCorner.getY(), yInverted,
                            getColor(0));
                }
                if (getChild(1) != null) {
                    getChild(1).toImageRecu(im, new Point(this.center.getX(), yInverted), bottomRightCorner);
                } else {
                    im.setRectangle(this.center.getX(), bottomRightCorner.getX(), yInverted, bottomRightCorner.getY(),
                            getColor(1));
                }
                if (getChild(2) != null) {
                    getChild(2).toImageRecu(im, new Point(topLeftCorner.getX(), yInverted),
                            new Point(this.center.getX(), bottomRightCorner.getY()));
                } else {
                    im.setRectangle(topLeftCorner.getX(), this.center.getX(), yInverted, bottomRightCorner.getY(),
                            getColor(2));
                }
                // Vertical stroke
                im.setRectangle(this.center.getX() - strokeOffset, this.center.getX() + strokeOffset + 1, yInverted,
                        bottomRightCorner.getY(), Color.BLACK);
                // Horizontal stroke
                im.setRectangle(topLeftCorner.getX(), bottomRightCorner.getX(), yInverted - strokeOffset,
                        yInverted + strokeOffset + 1, Color.BLACK);
                break;

            case ("left"):
                if (getChild(0) != null) {
                    getChild(0).toImageRecu(im, topLeftCorner, new Point(this.center.getX(), yInverted));
                } else {
                    im.setRectangle(topLeftCorner.getX(), this.center.getX(), topLeftCorner.getY(), yInverted,
                            getColor(0));
                }
                if (getChild(1) != null) {
                    getChild(1).toImageRecu(im, new Point(this.center.getX(), topLeftCorner.getY()), bottomRightCorner);
                } else {
                    im.setRectangle(this.center.getX(), bottomRightCorner.getX(), topLeftCorner.getY(),
                            bottomRightCorner.getY(), getColor(1));
                }
                if (getChild(2) != null) {
                    getChild(2).toImageRecu(im, new Point(topLeftCorner.getX(), yInverted),
                            new Point(this.center.getX(), bottomRightCorner.getY()));
                } else {
                    im.setRectangle(topLeftCorner.getX(), this.center.getX(), yInverted, bottomRightCorner.getY(),
                            getColor(2));
                }
                // Vertical stroke
                im.setRectangle(this.center.getX() - strokeOffset, this.center.getX() + strokeOffset + 1,
                        topLeftCorner.getY(), bottomRightCorner.getY(), Color.BLACK);
                // Horizontal stroke
                im.setRectangle(topLeftCorner.getX(), this.center.getX(), yInverted - strokeOffset,
                        yInverted + strokeOffset + 1, Color.BLACK);
                break;

            case ("right"):
                if (getChild(0) != null) {
                    getChild(0).toImageRecu(im, topLeftCorner, new Point(this.center.getX(), yInverted));
                } else {
                    im.setRectangle(topLeftCorner.getX(), this.center.getX(), topLeftCorner.getY(),
                            bottomRightCorner.getY(), getColor(0));
                }
                if (getChild(1) != null) {
                    getChild(1).toImageRecu(im, new Point(this.center.getX(), topLeftCorner.getY()),
                            new Point(bottomRightCorner.getX(), yInverted));
                } else {
                    im.setRectangle(this.center.getX(), bottomRightCorner.getX(), topLeftCorner.getY(), yInverted,
                            getColor(1));
                }
                if (getChild(2) != null) {
                    getChild(2).toImageRecu(im, new Point(this.center.getX(), yInverted), bottomRightCorner);
                } else {
                    im.setRectangle(this.center.getX(), bottomRightCorner.getX(), yInverted, bottomRightCorner.getY(),
                            getColor(2));
                }
                // Vertical stroke
                im.setRectangle(this.center.getX() - strokeOffset, this.center.getX() + strokeOffset + 1,
                        topLeftCorner.getY(), bottomRightCorner.getY(), Color.BLACK);
                // Horizontal stroke
                im.setRectangle(this.center.getX(), bottomRightCorner.getX(), yInverted - strokeOffset,
                        yInverted + strokeOffset + 1, Color.BLACK);
                break;

            default:
                break;
        }
    }

    public String toText(String s) {
        s += "'" + direction + "'";
        s += super.toText("");
        return s;
    }

}
