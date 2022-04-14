
public class TreeObject
{
    private long DNA;
    private int frequency;

    public TreeObject(long DNA){
        this.frequency = 0;
        this.DNA = DNA;

    }

    public long getDNA(){
        return DNA;
    }

    public void increaseFrequency(){
        frequency++;
    }

    public String toString(long DNA, int numKeys){
        return null;
    }


}
