//package main.java.cs321.create;
package cs321.create;

import cs321.btree.BTree;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
import cs321.search.GeneBankSearchBTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Use a string that takes in everything from ORIGIN to // and add ATCG and N to a string.
 */
public class GeneBankCreateBTree
{
    public int degree, sequenceLength, cacheSize;
    public File filename;
    public String fileString;
    // public BTree workingBTree;
    // public boolean useCache;
    public boolean debugCall;

    public static void main(String[] args) throws Exception
    {
      String cacheCall = "0"; // args[0]
      boolean useCache = false;
      int cacheSize = 0;
      int degreeCall = 3; // Integer.parseInt(args[1]);
      String gbkFile = "data/files_gbk/test0.gbk"; // args[2];
      int sequenceLength = 4; // Integer.parseInt(args[3]);
        String dumpFileName = "gbkFile.test0." + "btree.dump" + sequenceLength;

      BTree workingBTree = null;
      workingBTree = new BTree(degreeCall, "testRAF", 0, sequenceLength); // degree, gbk file,

//        /* check args */
//        /* no cache and no debug */
//        if(args.length == 4)
//        {
//          if(!cacheCall.equals("0"))
//          {
//            printUsageAndExit();
//          }
//          workingBTree = new BTree(degreeCall, gbkFile, 0); // degree, gbk file,
//        }
//        /* cache or debug */
//        else if(args.length == 5)
//        {
//          if(cacheCall.equals("0"))
//          {
//            if(args[4].equals("0"))
//            {
//              // debug level 0
//            }
//            else if(args[4].equals("1"))
//            {
//              // debug level 1
//            }
//            else
//            {
//              printUsageAndExit();
//            }
//          }
//        }
//        /* cache and debug */
//        else if(args.length == 6)
//        {
//          if(cacheCall.equals("1"))
//          {
//            useCache = true;
//          }
//          else
//          {
//            printUsageAndExit();
//          }
//        }
//        /* args < 4 or args > 6 */
//        else
//        {
//          printUsageAndExit();
//        }

//        String searchList = Parse("file.txt");
        String searchList = Parse(gbkFile);

        cs321.create.GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args); // ??
//        int sequenceLength = 3; // change after everything works

        /* break data into sequence length group */
        for(int i = 0; i < searchList.length() - sequenceLength+1; i++)
        {
            /* Creating the subsequences */
            String currStr = "";
            for(int j = 0; j < sequenceLength; j++)
            {
                currStr += searchList.charAt(i + j);
            }

//            System.out.println(currStr);
            long newData = dnaToLong(currStr);
//            System.out.println(newData);
            workingBTree.BTreeInsert(newData);
        }
//        workingBTree.dump("dumptest.txt");
        workingBTree.dump(dumpFileName);
    }

    /**
     * dnaToLong method
     * Convert DNA to long
     * @param DNA
     * @return
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

    private static cs321.create.GeneBankCreateBTreeArguments parseArgumentsAndHandleExceptions(String[] args)
    {
        cs321.create.GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = null;
        try
        {
            geneBankCreateBTreeArguments = parseArguments(args);
        }
        catch (ParseArgumentException e)
        {
            printUsageAndExit(e.getMessage());
        }
        return geneBankCreateBTreeArguments;
    }

    /**
     * Print with inputed error massage
     * @param errorMessage
     */
    private static void printUsageAndExit(String errorMessage)
    {
        System.exit(1);
    }

    /**
     * Default print error message
     */
    private static void printUsageAndExit()
    {
        System.out.println("java GeneBankCreate <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
        System.exit(1);
    }

    public static cs321.create.GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        return null;
    }

    /**
     * New Parse Method
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static String Parse (String name) throws FileNotFoundException
    {
        File file = new File (name);
        Scanner scan = new Scanner (file);
//        List segments = new LinkedList();
        String segments = "";
        String returnString = "";
        while (scan.hasNextLine())
        {
            String line = scan.nextLine();
            if (line.contains("ORIGIN"))
            {
//                scan.useDelimiter("[^1234567890acntgACNTG /]+");

                while (scan.hasNextLine())
                {

                    String str = scan.nextLine();
                    if (!str.contains("//") && !str.equals(" "))
                    {
//                        returnString = "";
                        if(str.contains(" "))
                        {
                            for(int i = 0; i < str.length(); i++)
                            {
                                if (!"1234567890 /\n".contains(String.valueOf(str.charAt(i))))
                                {
                                    returnString += str.charAt(i);
                                }
                            }
//                            System.out.println(str);
                        }
                        else
                        {
                            returnString = str;
                        }
//                        segments.add(returnString); // removing linked list
                    }
                    else
                    {
//                        System.out.println(str);
                        Scanner strScan = new Scanner(returnString);
                        strScan.useDelimiter("nn*");
                        while(strScan.hasNext())
                        {
                            String testPrint = strScan.next();
//                            segments.add(testPrint);
                            segments += testPrint;
                        }
                        returnString = "";
                        break;
                    }
                }
//                scan.useDelimiter(Pattern.compile("\\n"));
            }
        }
//        segments.add(returnString);
        return segments;
    }
    /* END OF NEW PARSE */

//    /**
//     * Parse Method
//     * @param name
//     * @return
//     * @throws FileNotFoundException
//     */
//    public static List <String> Parse (String name) throws FileNotFoundException
//    {
//        File file = new File (name);
//        Scanner scan = new Scanner (file);
//        List segments = new LinkedList();
//        String returnString = "";
//        while (scan.hasNextLine())
//        {
//            String line = scan.nextLine();
//            if (line.contains("ORIGIN"))
//            {
////                scan.useDelimiter("[^1234567890acntgACNTG /]+");
//
//                while (scan.hasNextLine())
//                {
//
//                    String str = scan.nextLine();
//                    if (!str.contains("//") && !str.equals(" "))
//                    {
////                        returnString = "";
//                        if(str.contains(" "))
//                        {
//                            for(int i = 0; i < str.length(); i++)
//                            {
//                                if (!"1234567890 /\n".contains(String.valueOf(str.charAt(i))))
//                                {
//                                    returnString += str.charAt(i);
//                                }
//                            }
////                            System.out.println(str);
//                        }
//                        else
//                        {
//                            returnString = str;
//                        }
////                        segments.add(returnString); // removing linked list
//                    }
//                    else
//                    {
////                        System.out.println(str);
//                        Scanner strScan = new Scanner(returnString);
//                        strScan.useDelimiter("nn*");
//                        while(strScan.hasNext())
//                        {
//                            String testPrint = strScan.next();
//                            segments.add(testPrint);
//                        }
//                        returnString = "";
//                        break;
//                    }
//                }
////                scan.useDelimiter(Pattern.compile("\\n"));
//            }
//        }
////        segments.add(returnString);
//        return segments;
//    }

    //

    /**
     * getPatterns
     * @param len
     * @param sequence
     * @return
     */
    public static List <String> getPatterns(int len, String sequence)
    {
        List patterns = new LinkedList();
        int subStart = 0;
        int subEnd = len;

        if (len > sequence.length())
        {
            return null;
        }

        if (len > 31 || len < 1)
        {
            return null;
        }

        for (int i = len-1; i < sequence.length(); i++)
        {
            patterns.add(sequence.substring(subStart, subEnd));
            subStart++;
            subEnd++;
        }

        return patterns;
    }
}
