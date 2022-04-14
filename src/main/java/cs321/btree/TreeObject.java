package cs321.btree;

public class TreeObject
{
    private long key;
    private int frequency = 0;

    public TreeObject(long key)
    {
        this.key = key;
    }

    public void incrementFrequency()
    {
        frequency++;
    }

    public long getKey()
    {
        return key;
    }

    public int getFrequency()
    {
        return frequency;
    }
}
