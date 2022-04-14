package src.main.java.cs321.btree;


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

    public String toString(int numK){
        return String.valueOf(DNA) + ":" + " " + frequency;
    }



}
