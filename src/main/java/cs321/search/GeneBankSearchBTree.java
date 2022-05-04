//package main.java.cs321.search;
package cs321.search;
import cs321.btree.BTree;

import cs321.btree.BTree;
import cs321.btree.TreeObject;

import java.io.*;
import java.util.Scanner;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws FileNotFoundException, Exception
    {

        boolean useCache = false; // 0 no/false 1 with cache/true
        String btreeFile = args[1];
        int cacheSize = 0;

//      System.out.println("Hello world from cs321.search.GeneBankSearchBTree.main");

        if(args.length < 3 || args.length > 5){
            printUsage();
        }

        if(args[0].equals("1")){
            useCache = true;
        }
        else if(!args[0].equals("0")){
            useCache = false;
        }
        else{
            printUsage();
        }


        if(useCache && args.length >= 4){
            cacheSize = Integer.parseInt(args[3]);
            if(cacheSize <= 0){
                printUsage();
                System.exit(1);
            }
        }

        if(!useCache && args.length == 4 && args[4].equals("0") ){
            printUsage();
        }
       
        Scanner file = new Scanner(new File(args[3]));

        BTree searchTree = new BTree(btreeFile);
        PrintStream logOut = System.out;
        PrintStream ps = new PrintStream("test"); //

        while(file.hasNextLine())
        {
            String sequence = file.nextLine();
            long geneNum = dnaToLong(sequence);
            //searchTree.search(geneNum, ps);
            int freq = searchTree.search(geneNum, ps);
            //dump(searchTree);
            if(freq != -1){
                System.out.println(sequence + ": " + freq);
            }
            

        }
        System.setOut(ps);
        System.setOut(logOut);
        

    }

    /**
     * BTree constructor
     */
    public static void BTree(RandomAccessFile raf) throws IOException
    {
        raf.seek(0);
        int degree = raf.readInt();
        long root = raf.readLong();
        int sizeOfBTreeNode = raf.readInt();
        long nextAddress = raf.readLong();
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
                retVal = retVal<<2;
            }
            else if(c == 't' || c == 'T')
            {
                retVal += 3;
                retVal = retVal<<2;
            }
            else if(c == 'c' || c == 'C')
            {
                retVal += 1;
                retVal = retVal<<2;
            }
            else if(c == 'g' || c == 'G')
            {
                retVal += 2;
                retVal = retVal<<2;
            }
            else
            {
                return -1;
            }
        }
        return-1;
    }

    public static void printUsage()
    {
        System.out.println("java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        System.exit(1); // exit out of program as not expected
    }
}


