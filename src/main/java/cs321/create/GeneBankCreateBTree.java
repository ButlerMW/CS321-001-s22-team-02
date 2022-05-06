package cs321.create;

import cs321.btree.BTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Use a string that takes in everything from ORIGIN to // and add ATCG and N to a string.
 */
public class GeneBankCreateBTree
{
    // public int degree, sequenceLength, cacheSize;
    // public File filename;
    // public String fileString;
    // public boolean debugCall;

    public static void main(String[] args) throws Exception
    {
        // long startTime = System.currentTimeMillis(); // start time for calulating run time
        int progressCounter = 0;                        // part of the progress bar
        int debug = 0;                                  // default number
        int cacheSize = 0;                              // default to 0 // test number 0, 100, 500
        String cacheCall = args[0];                     // test numbers 0, 1
        int degreeCall = Integer.parseInt(args[1]);
        String gbkFile = args[2];
        int sequenceLength = Integer.parseInt(args[3]); // test number 1 <= k <= 31
        String dumpFileName = gbkFile + ".btree.dump." + sequenceLength + "." + degreeCall;
        String rafFile = gbkFile + ".btree.data." + sequenceLength + "." + degreeCall;
        BTree workingBTree = null; // default to null

        /* check cacheCall */
        if(!cacheCall.equals("0") && !cacheCall.equals("1"))
        {
            printUsageAndExit();
        }

        /* check if sequence length is in the correct range */
        if(sequenceLength < 1 || sequenceLength > 31)
        {
            printUsageAndExit();
        }

        // if outside of range
        if(args.length < 4 || args.length > 6)
        {
            printUsageAndExit();
        }

        // no cache and no debug check
        if(args.length == 4 && !cacheCall.equals("0"))
        {
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

        workingBTree = new BTree(degreeCall, rafFile, cacheSize, sequenceLength);

        System.out.print("parsing..");
        List<String> segments = Parse(gbkFile);

        /* break data into sequence length groups */
        System.out.print("sequencing..");
        int index = 0;
        while(index < segments.size())
        {
            String searchList = (String) segments.get(index);
            for(int i = 0; i < searchList.length() - sequenceLength+1; i++)
            {
                progressCounter++;
                if(progressCounter % 46000 == 0)
                {
                    System.out.print(".");
                }

                /* Creating the subsequences */
                String currStr = "";
                for(int j = 0; j < sequenceLength; j++)
                {
                    currStr += searchList.charAt(i + j);
                }

                long newData = dnaToLong(currStr);
                workingBTree.BTreeInsert(newData);
            }
            index++;
        }
        workingBTree.UpdateRootAddress();

        /* Debug results */
        if(debug == 0)
        {
            System.out.println("Debug selction: " + debug);
            System.out.println("java GeneBankCreate <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
            System.out.println("Possible exceptions to be thrown: \n\tFileNotFound \n\tIOException");
        }
        else if(debug == 1)
        {
            workingBTree.dump(dumpFileName);
            GeneBankCreateDatabase(new File(dumpFileName), "Sample");
        }
        else
        {
            printUsageAndExit();
        }

        // // Running Time Calculation
        // long endTime = System.currentTimeMillis();
        // System.out.println("start time: " + startTime);
        // System.out.println("end time: " + endTime);
        // System.out.println("total (calculated) run time : " + (endTime - startTime));
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

    /**
     * Default print error message
     */
    private static void printUsageAndExit()
    {
        System.out.println("java GeneBankCreate <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
        System.exit(1);
    }

    /**
     * Parse Method
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static List <String> Parse (String name) throws FileNotFoundException
    {
        int counter = 0;
        File file = new File (name);
        Scanner scan = new Scanner (file);
        List<String> segments = new LinkedList<String>();
        String returnString = "";
        while (scan.hasNextLine())
        {
            counter++;
            if(counter % 800 == 0)
            {
                System.out.print(".");
            }
            String line = scan.nextLine();
            if (line.contains("ORIGIN"))
            {
                while (scan.hasNextLine())
                {
                    counter++;
                    if(counter % 800 == 0)
                    {
                        System.out.print(".");
                    }
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
                            counter++;
                            if(counter % 800 == 0)
                            {
                                System.out.print(".");
                            }
                            String testPrint = strScan.next();
                            segments.add(testPrint);
                        }
                        returnString = "";
                        strScan.close();
                        break;
                    }
                }
            }
        }
        scan.close();
        return segments;
    }

    public static void GeneBankCreateDatabase(File dump, String databaseName)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
            Scanner file = new Scanner(dump);
            Statement s = connection.createStatement();
            // s.executeQuery("CREATE TABLE IF NOT EXISTS DNA (key varchar(10), frequency int);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS DNA (key varchar(10), value string, frequency int);");
            String value = "";
            int freq = 0;

            while (file.hasNextLine())
            {
                Scanner line = new Scanner(file.nextLine());
                value = line.next();
                freq = line.nextInt();

                // value = value.stripTrailing();
                value = value.substring(0, value.length()-1);

                // s.executeQuery("insert into DNA (key, frequency) values (value, freq);");
                s.executeUpdate("insert into DNA values('" + value + "', " + freq +");");
            }
            file.close();
        }
        catch (SQLException | FileNotFoundException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
