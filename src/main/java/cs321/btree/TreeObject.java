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

        String retVal = "";
        String biString = Long.toBinaryString(DNA);
        while(biString.length() < 2*numK){
            biString += "0";
        }
        for(int i = 0; i < biString.length(); i+=2){
            if(biString.substring(i, i + 2).equals("00")){
                retVal += "A";
            }
            if(biString.substring(i, i + 2).equals("11")){
                retVal += "T";
            }
            if(biString.substring(i, i + 2).equals("01")){
                retVal += "C";
            }
            if(biString.substring(i, i + 2).equals("10")){
                retVal += "G";
            }

        }

        return retVal + ":" + " " + frequency;
    }


}
