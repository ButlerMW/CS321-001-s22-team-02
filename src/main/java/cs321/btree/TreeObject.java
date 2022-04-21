package cs321.btree;


public class TreeObject
{
    private long DNA;
    private int frequency;

    public TreeObject(long DNA){
        this.frequency = 0;
        this.DNA = DNA;
    }
    
    public TreeObject(long DNA, int frequency)
    {
      this.frequency = frequency;
      this.DNA = DNA;
    }

    public long getDNA(){
        return DNA;
    }

    public int getFrequency()
    {
      return frequency;
    }

    public void increaseFrequency(){
        frequency++;
    }

    public String toString(int numK){
        return String.valueOf(DNA) + ":" + " " + frequency;
    }


}
