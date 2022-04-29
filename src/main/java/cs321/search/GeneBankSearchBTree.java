//package main.java.cs321.search;
package cs321.search;

import cs321.btree.TreeObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws FileNotFoundException, Exception
    {
        boolean useCache; // 0 no/false 1 with cache/true

//        System.out.println("Hello world from cs321.search.GeneBankSearchBTree.main");

        Scanner file = new Scanner(new File("query6.txt"));
//        BTree;
        while(file.hasNextLine())
        {
           String sequence = file.nextLine();
            long geneNum = dnaToLong(sequence);
            TreeObject searchNode = new TreeObject(geneNum);


        }
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

    /* Convert DNA to long */
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
    }
}


