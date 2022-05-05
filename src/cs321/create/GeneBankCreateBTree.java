package main.java.cs321.create;
//package cs321.create;

//import cs321.btree.BTree;
//import cs321.btree.TreeObject;
//import cs321.common.ParseArgumentException;
//import cs321.search.GeneBankSearchBTree;

import main.java.cs321.btree.BTree;
import main.java.cs321.common.ParseArgumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
        String cacheCall = args[0]; // test numbers 0, 1
        int debug = 0; // default number
        int cacheSize = 0; // default to 0 // test number 0, 100, 500
        int degreeCall = Integer.parseInt(args[1]);
        String gbkFile = args[2]; // test 
        int sequenceLength = Integer.parseInt(args[3]); // test number 1 <= k <= 31
        String dumpFileName = gbkFile + "btree.myTestDump." + sequenceLength;

        BTree workingBTree = null; // default to null?
//      workingBTree = new BTree(degreeCall, "testRAF", 500, sequenceLength);

        /* check cacheCall */
        if(!cacheCall.equals("0") && !cacheCall.equals("1"))
        {
            System.out.println("check 0");
            printUsageAndExit();
        }

        /* check if sequence length is in the correct range */
        if(sequenceLength < 1 || sequenceLength > 31)
        {
            System.out.println("First Check");
            printUsageAndExit();
        }

        // if outside of range
        if(args.length < 4 || args.length > 6)
        {
            System.out.println("check 1");
            printUsageAndExit();
        }

        // no cache and no debug check
        if(args.length == 4 && !cacheCall.equals("0"))
        {
            System.out.println("Second Check");
            printUsageAndExit();
        }

        // cache or debug
        if(args.length == 5 && cacheCall.equals("0"))
        {
            if(args[4].equals("0") || args[4].equals("1"))
            {
                debug = Integer.parseInt(args[4]);
            }
            else if(cacheCall.equals("1"))
            {
                cacheSize = Integer.parseInt(args[4]);
            }
        }

        // cache and debug
        if(args.length == 6)
        {
            if(cacheCall.equals("1"))
            {
                cacheSize = Integer.parseInt(args[4]);
            }
            else if(!cacheCall.equals("0"))
            {
                System.out.println("Fifth Check");
                printUsageAndExit();
            }
            if(args[5].equals("0") || args[5].equals("1"))
            {
                debug = Integer.parseInt(args[5]);
            }
        }

        // /* check if args length is in appropriate range */
        // if(args.length >= 4 || args.length <= 6)
        // {
        /* no cache and no debug */
        /* cache or debug */
        //   if(args.length == 5 && cacheCall.equals("0"))
        //   {
        //       else
        //       {
        // System.out.println("Third Check");
        //           printUsageAndExit();
        //       }
        //   }
        //   else if(cacheCall.equals("1"))
        // else
        // {

        //     printUsageAndExit();
        // }
        /* cache and debug */
        // }
        // else
        // {
        //   System.out.println("Seventh Check");
        //     printUsageAndExit();
        // }

        workingBTree = new BTree(degreeCall, "testRAF", cacheSize, sequenceLength);

//        /* check if args length is in correct range */
//        /* no cache and no debug */
//        if(args.length == 4)
//        {
//          if(!cacheCall.equals("0"))
//          {
//            printUsageAndExit();
//          }
//          workingBTree = new BTree(degreeCall, "", 0, sequenceLength); // degree, gbk file,
//        }
//        /* cache or debug */
//        else if(args.length == 5)
//        {
//          if(cacheCall.equals("0"))
//          {
//            if(args[4].equals("0"))
//            {
//              // debug to args[4]
//                debug = Integer.parseInt(args[4]);
//            }
//            else if(args[4].equals("1"))
//            {
//              // debug level 1
////                debug = 1;
//                cacheSize = args[4];
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
//
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
//        String searchList = Parse(gbkFile);
        List segments = Parse(gbkFile);
        cs321.create.GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args); // ??
//        int sequenceLength = 3; // change after everything works

        /* break data into sequence length groups */
        int index = 0;
        while(index < segments.size())
        {
            String searchList = (String) segments.get(index);
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
            index++;
        }

//        workingBTree.dump("dumptest.txt");
        /* Debug results */
        if(debug == 0)
        {
            System.out.println("Debug selction: " + debug);
            System.out.println("java GeneBankCreate <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
            System.out.println("Possible exceptions to be thrown: \n\tFileNotFound \n\tIOException");
        }
        if(debug == 1)
        {
            workingBTree.dump("linkedlistback" + dumpFileName);
        }
        else
        {
            printUsageAndExit();
        }
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
     * Parse Method
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static List <String> Parse (String name) throws FileNotFoundException
    {
        File file = new File (name);
        Scanner scan = new Scanner (file);
        List segments = new LinkedList();
        String returnString = "";
        while (scan.hasNextLine())
        {
            String line = scan.nextLine();
            if (line.contains("ORIGIN"))
            {
                while (scan.hasNextLine())
                {
                    String str = scan.nextLine();
                    if (!str.contains("//") && !str.equals(" "))
                    {
                        if(str.contains(" "))
                        {
                            for(int i = 0; i < str.length(); i++)
                            {
                                if (!"1234567890 /\n".contains(String.valueOf(str.charAt(i))))
                                {
                                    returnString += str.charAt(i);
                                }
                            }
                        }
                        else
                        {
                            returnString = str;
                        }
                    }
                    else
                    {
                        Scanner strScan = new Scanner(returnString);
                        strScan.useDelimiter("nn*");
                        while(strScan.hasNext())
                        {
                            String testPrint = strScan.next();
                            segments.add(testPrint);
                        }
                        returnString = "";
                        break;
                    }
                }
            }
        }
        return segments;
    }

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




    public void GeneBankCreateDatabase(File dump){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Scanner file = new Scanner(dump);
            Statement s = connection.createStatement();
            s.executeQuery("CREATE TABLE DNA (key varchar(10), frequency int);");
            String value = "";
            int freq = 0;

            while (file.hasNextLine()){
                Scanner line = new Scanner(file.nextLine());
                value = line.next();
                freq = line.nextInt();

                value = value.stripTrailing();

                s.executeQuery("insert into DNA (key, frequency) values (value, freq);");
            }


        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}