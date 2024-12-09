public class Region {
    Pktree tree_;
    int regionNumber_;

    // Contructors:
    // Complexity : O(1)
    public Region() {
        tree_ = null;
        regionNumber_ = -1;
    }

    // Complexity : O(1)
    public Region(Pktree tree, int regionNumber) {
        this.tree_ = tree;
        this.regionNumber_ = regionNumber;
    }

    // Getters:
    // Complexity : O(1)
    public int getRegionNumber_() {
        return regionNumber_;
    }

    // Complexity : O(1)
    public Pktree getTree_() {
        return tree_;
    }
}
