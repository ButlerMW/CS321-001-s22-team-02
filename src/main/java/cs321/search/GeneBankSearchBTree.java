package cs321.search;

import cs321.btree.*;

import java.io.*;
import java.util.Scanner;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws FileNotFoundException, Exception
    {
        // long startTime = System.currentTimeMillis(); // start time for calulating run time
        boolean useCache = false; // 0 no/false 1 with cache/true
        String btreeFile = args[1];
        int cacheSize = 0;

        if(args.length < 3 || args.length > 5)
        {
            printUsage();
        }

        if(args[0].equals("1"))
        {
            useCache = true;
        }
        else if(args[0].equals("0"))
        {
            useCache = false;
        }
        else
        {
            printUsage();
        }

        if(useCache && args.length >= 4)
        {
            cacheSize = Integer.parseInt(args[3]);
            if(cacheSize <= 0)
            {
                printUsage();
                System.exit(1);
            }
        }

        if(!useCache && args.length == 4 && !args[3].equals("0"))
        {
            printUsage();
        }

        Scanner file = new Scanner(new File(args[2]));

        BTree searchTree = new BTree(btreeFile, cacheSize);
        PrintStream logOut = System.out;
        PrintStream ps = new PrintStream("test"); //

        while(file.hasNextLine())
        {
            String sequence = file.nextLine();
            long geneNum = dnaToLong(sequence);
            int freq = searchTree.search(geneNum, ps);
            if(freq != -1)
            {
                System.out.println(sequence.toLowerCase() + ": " + freq);
            }
        }
        System.setOut(ps);
        System.setOut(logOut);

        // // Running Time Calculation
        // long endTime = System.currentTimeMillis();
        // System.out.println("start time: " + startTime);
        // System.out.println("end time: " + endTime);
        // System.out.println("total (calculated) run time : " + (endTime - startTime));
    }

    /**
     * Convert DNA to long
     */
    public static long dnaToLong(String DNA)
    {
        long retVal = 0;
        for(int i = 0; i < DNA.length(); i++)
        {
            char c = DNA.charAt(i);
            if(c == 'a' || c == 'A')
            {
                retVal += 0;
            }
            else if(c == 't' || c == 'T')
            {
                retVal += 3;
            }
            else if(c == 'c' || c == 'C')
            {
                retVal += 1;
            }
            else if(c == 'g' || c == 'G')
            {
                retVal += 2;
            }
            else
            {
                return -1;
            }
            if(i < DNA.length()-1)
            {
                retVal = retVal<<2;
            }
        }
        return retVal;
    }

    public static void printUsage()
    {
        System.out.println("java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        System.exit(1); // exit out of program as not expected
    }
}


