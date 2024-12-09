import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Pktree {
    protected Point center;
    protected ArrayList<Color> colors;
    protected ArrayList<Pktree> children;
    protected final int branchCount;
    protected Pktree parent;

    // Contructor
    // Complexity : O(1)
    protected Pktree(Point center, int branchCount) {
        this.center = center;
        this.colors = new ArrayList<>();
        this.children = new ArrayList<>();
        this.branchCount = branchCount;
        this.parent = null;

        for (int i = 0; i < branchCount; i++) { // Initialize tree depending on its size
            children.add(null);
            colors.add(null);
        }
    }

    /**
     * Create a Instance of either a PQuadTree or P3tree
     * <p>
     * Complexity : O(1)
     * 
     * @param p
     * @return as Instance of a given tree
     */
    protected abstract Pktree createInstance(Point p);

    // Getters
    /**
     * Return the child at the given index
     * <p>
     * Complexity : O(1)
     * 
     * @param childNumber The number of the child
     * @return The corresponding child
     */
    public Pktree getChild(int childNumber) {
        try {
            return children.get(childNumber);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Child number " + childNumber + " is out of bounds." + e);
            return null;
        }
    }

    /**
     * Return the color at the given child's index
     * <p>
     * Complexity : O(1)
     * 
     * @param colorNumber The number of the child
     * @return The corresponding color
     */
    public Color getColor(int colorNumber) {
        try {
            return colors.get(colorNumber);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Color number " + colorNumber + " is out of bounds." + e);
            return null;
        }
    }

    // Complexity : O(1)
    public int getBranchCount() {
        return branchCount;
    }

    /**
     * Return the region where a point is
     * <p>
     * Complexity : O(size of the tree)
     * 
     * @param p The point we are looking for
     * @return the region where the point is
     * 
     */
    protected Region searchRegionRecu(Point p, int regionNumber) {
        if (children.get(regionNumber) == null) {
            return new Region(this, regionNumber);
        } else {
            return children.get(regionNumber).searchRegion(p);
        }
    }

    /**
     * Return the region where a point is
     * <p>
     * Complexity : O(size of the tree)
     * 
     * @param p The point we are looking for
     * @return the region where the point is
     * 
     */
    protected abstract Region searchRegion(Point p);

    /**
     * Return the subtree where a center is
     * <p>
     * Complexity : same as searchRegion
     * 
     * @param c The center we are looking for
     * @return the subtree where th center is
     */
    public Pktree searchQTree(Center c) {
        Point p = c.getPoint();
        Region region = searchRegion(p);
        Pktree currentTree = region.getTree_();
        currentTree.children.set(region.getRegionNumber_(), createInstance(p)); // Create a new tree division
        currentTree.children.get(region.getRegionNumber_()).parent = region.getTree_(); // Specify the parent of the new
                                                                                        // tree
        return currentTree.getChild(region.regionNumber_); // Return the new tree
    }

    /**
     * Modify Pktree with a given center values
     * <p>
     * Complexity : O(1)
     * 
     * @param c Center with the new values
     */
    public void addQTree(Center c) {
        this.center = c.getPoint();
        this.colors = c.getColors();
    }

    /**
     * Add one by one center to a Pktree
     * <p>
     * Complexity : O(size of the list)
     * 
     * @param centers list of center to add
     */
    public void buildQTree(ArrayList<Center> centers) {
        this.addQTree(centers.get(0)); // Create first node of tree
        centers.remove(0);
        Pktree currentTree;
        for (Center c : centers) {
            currentTree = searchQTree(c);
            currentTree.addQTree(c);
        }

    }

    /**
     * Modify image between top left and bottom right with predifined color in
     * Pktree and add border
     * <p>
     * Complexity : O(number of node)
     *
     * @param im                Image to modify
     * @param topLeftCorner     Top left coordinate point
     * @param bottomRightCorner Bottom right coordinate point
     */
    protected abstract void toImageRecu(Image im, Point topLeftCorner, Point bottomRightCorner);

    /**
     * Setting up Image with paramaters for toImageRecu
     * <p>
     * Complexity : O(number of node)
     *
     * @param im Image to modify
     */
    public void toImage(Image im) {
        int width = im.width();
        int height = im.height();
        Point topLeft = new Point(0, 0);
        Point bottomRight = new Point(width, height);
        toImageRecu(im, topLeft, bottomRight);
    }

    /**
     * Translate Pktree to string formatting "(c1...ck)" k ∈ N
     * If a region is subdivided, print subdivision instead
     * Exemple :
     * "(c1(c2.1...c2.k)...ck)"
     * <p>
     * Complexity : O(number of line of f)
     * 
     * @param s a String
     * @return Tree formated to a string with parentheses
     */
    public String toText(String s) {
        s += "(";
        int index = 0;
        for (Pktree tree : children) {
            if (tree == null) {
                s += colorToString(colors.get(index));
            } else {
                s += tree.toText("");
            }
            index++;
        }
        s += ")";
        return s;
    }

    /**
     * Return region where the point belongs
     * <p>
     * Complexity : O(1)
     * 
     * @return region it belongs to
     */
    protected abstract int getNumberInParent();

    /**
     * Method to fuse all child if they have all the same color and check for the
     * parent
     * <p>
     * Complexity : O(size of tree)
     */
    private void compressQtree() {
        if (parent != null) { // Assert that we are not on the root node
            boolean sameColors = true,
                    noChild = true;
            Color c1 = colors.get(0);
            int i = 1;
            while (noChild && sameColors && i < colors.size()) { // Check wether we can compress the tree or not
                if (c1 != colors.get(i)) {
                    sameColors = false;
                }
                if (children.get(i) != null) {
                    noChild = false;
                }
                i++;
            }
            if (noChild && sameColors) { // Compression process
                int regionNumber = getNumberInParent();
                parent.children.set(regionNumber, null);
                parent.colors.set(regionNumber, c1);
                parent.compressQtree();
            }
        }
    }

    /**
     * Modify color to at a given point
     * <p>
     * Complexity : same as compressQtree
     * 
     * @param p a Point
     * 
     * @param c a Color
     */
    public void reColor(Point p, Color c) {
        Region regionToRecolor = searchRegion(p);
        Pktree treeToRecolor = regionToRecolor.getTree_();
        treeToRecolor.colors.set(regionToRecolor.getRegionNumber_(), c);
        treeToRecolor.compressQtree();
    }

    /**
     * Translate String to a color
     * <p>
     * Complexity : O(1)
     * 
     * @param s a String
     * 
     * @return translate String to a color
     * 
     */
    private Color stringToColor(String s) {
        switch (s) {
            case "G":
                return Color.LIGHT_GRAY;
            case "B":
                return Color.BLUE;
            case "N":
                return Color.BLACK;
            case "R":
                return Color.RED;
            case "J":
                return Color.YELLOW;
            default:
                return null;
        }
    }

    /**
     * Translate color to String
     * <p>
     * Complexity : O(1)
     * 
     * @param c a color
     * 
     * @return translate color to String
     */
    private String colorToString(Color c) {
        switch (c.toString()) {
            case "java.awt.Color[r=192,g=192,b=192]":
                return "G";
            case "java.awt.Color[r=0,g=0,b=255]":
                return "B";
            case "java.awt.Color[r=0,g=0,b=0]":
                return "N";
            case "java.awt.Color[r=255,g=0,b=0]":
                return "R";
            case "java.awt.Color[r=255,g=255,b=0]":
                return "J";
            default:
                return null;

        }
    }

    /**
     * Read a file
     * <p>
     * Complexity : O(number of line of f)
     * 
     * @param f             the file we are reading
     * 
     * @param centerTab     Array we modified to add all centers
     * 
     * @param recolorTab    Array we modified to add all recolors
     * 
     * @param directionsTab Array we modified to add all directions IF IT'S A P3TREE
     * 
     * @return Image we create using centerTab
     */
    public Image readFile(File f, ArrayList<Center> centerTab, ArrayList<Recolor> recolorTab,
            ArrayList<String> directionsTab) {
        String structureType = "";
        int pictureSize = 0,
                strokeWeight = 0;

        // Fill the tree with the file's content
        try {
            int x, y, nbPoint, nbRecolor;
            Scanner reader = new Scanner(f);

            String[] splitingTab;
            structureType = reader.nextLine(); // 1st line must be structure type otherwise it doesn't work
            pictureSize = reader.nextInt(); // 2st line must be pictureSize otherwise it doesn't work
            nbPoint = reader.nextInt(); // 3nd line must be the number of point for the same reason
            String s = reader.nextLine();

            for (int i = 0; i < nbPoint; i++) {
                s = reader.nextLine();
                splitingTab = s.split(" , |, | ,| |,");
                x = Integer.parseInt(splitingTab[0]);
                y = Integer.parseInt(splitingTab[1]);
                ArrayList<Color> colors = new ArrayList<>();
                for (int j = 0; j < getBranchCount(); j++) {
                    colors.add(stringToColor(splitingTab[j + 2]));
                }
                if (structureType.equals("P3")) {
                    directionsTab.add(splitingTab[5]);
                }
                centerTab.add(new Center(x, y, colors));
            }
            strokeWeight = reader.nextInt();
            nbRecolor = reader.nextInt();
            reader.nextLine();
            for (int i = 0; i < nbRecolor; i++) {
                s = reader.nextLine();
                splitingTab = s.split(" , |, | ,| |,");
                x = Integer.parseInt(splitingTab[0]);
                y = Integer.parseInt(splitingTab[1]);
                Color c1 = stringToColor(splitingTab[2]);
                recolorTab.add(new Recolor(x, y, c1));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return new Image(pictureSize, pictureSize, strokeWeight); // Construct and return image with corresponding size
    }
}
