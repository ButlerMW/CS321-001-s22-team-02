import java.util.LinkedList;

public class BTreeNode {

    private long parent;
    private boolean leaf;
    private int numberOfKeys;
    private LinkedList<Long> children;
    private LinkedList<TreeObject> k;

    public BTreeNode() 
    {
        numberOfKeys = 0;
    }

}
