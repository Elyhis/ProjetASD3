import java.awt.Color;

/**
 * PQuadtree is composed of four cardinal directions North West(NO), North
 * East(NE),
 * South West(SO), South East(SE)
 * Each directions have a colour and can be subdivided
 */
public class PQuadtree extends Pktree {

    // Constructors
    // Complexity : O(1)
    public PQuadtree() {
        super(null, 4);
    }

    // Complexity : O(1)
    public PQuadtree(Point p) {
        super(p, 4);
    }

    protected Region searchRegion(Point p) {
        Region res;
        if (p.getX() <= center.getX() && p.getY() <= center.getY()) {
            res = searchRegionRecu(p, 3);
        } else if (p.getX() > center.getX() && p.getY() <= center.getY()) {
            res = searchRegionRecu(p, 2);
        } else if (p.getX() <= center.getX() && p.getY() > center.getY()) {
            res = searchRegionRecu(p, 0);
        } else {
            res = searchRegionRecu(p, 1);
        }
        return res;
    }

    @Override
    protected int getNumberInParent() { // Mostly a copy of code above, should compress the 2 method if possible
        Point p = this.parent.center;
        if (center.getX() <= p.getX() && center.getY() <= p.getY()) {
            return 3;
        } else if (center.getX() > p.getX() && center.getY() <= p.getY()) {
            return 2;
        } else if (center.getX() <= p.getX() && center.getY() > p.getY()) {
            return 0;
        } else {
            return 1;
        }
    }

    protected Pktree createInstance(Point p) {
        return new PQuadtree(p);
    }

    protected void toImageRecu(Image im, Point topLeftCorner, Point bottomRightCorner) {
        int yInverted = im.height() - this.center.getY(); // Compensate upside down Y coordinate in Image

        if (getChild(0) != null) {
            getChild(0).toImageRecu(im, topLeftCorner, new Point(this.center.getX(), yInverted));
        } else {
            im.setRectangle(topLeftCorner.getX(), this.center.getX(), topLeftCorner.getY(), yInverted, getColor(0));
        }
        if (getChild(1) != null) {
            getChild(1).toImageRecu(im, new Point(this.center.getX(), topLeftCorner.getY()),
                    new Point(bottomRightCorner.getX(), yInverted));
        } else {
            im.setRectangle(this.center.getX(), bottomRightCorner.getX(), topLeftCorner.getY(), yInverted, getColor(1));
        }
        if (getChild(2) != null) {
            getChild(2).toImageRecu(im, new Point(this.center.getX(), yInverted), bottomRightCorner);
        } else {
            im.setRectangle(this.center.getX(), bottomRightCorner.getX(), yInverted, bottomRightCorner.getY(),
                    getColor(2));
        }
        if (getChild(3) != null) {
            getChild(3).toImageRecu(im, new Point(topLeftCorner.getX(), yInverted),
                    new Point(this.center.getX(), bottomRightCorner.getY()));
        } else {
            im.setRectangle(topLeftCorner.getX(), this.center.getX(), yInverted, bottomRightCorner.getY(), getColor(3));
        }
        // print stroke at regions intersections

        int strokeOffset = im.getStrokeWeight() / 2;
        // Vertical stroke
        im.setRectangle(this.center.getX() - strokeOffset, this.center.getX() + strokeOffset + 1, topLeftCorner.getY(),
                bottomRightCorner.getY(), Color.BLACK);
        // Horizontal stroke
        im.setRectangle(topLeftCorner.getX(), bottomRightCorner.getX(), yInverted - strokeOffset,
                yInverted + strokeOffset + 1, Color.BLACK);

    }

}
