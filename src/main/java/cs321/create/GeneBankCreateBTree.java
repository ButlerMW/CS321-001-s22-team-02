//package main.java.cs321.create;
package cs321.create;

import cs321.common.ParseArgumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GeneBankCreateBTree
{

    public static void main(String[] args) throws Exception
    {
//        System.out.println(Parse("file.txt")); // file.txt
        System.out.println(Parse("data/files_gbk/test3.gbk"));

        cs321.create.GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args);
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

    private static void printUsageAndExit(String errorMessage)
    {
        System.exit(1);
    }

    public static cs321.create.GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        return null;
    }

    public static List <String> Parse (String name) throws FileNotFoundException {
        File file = new File (name);
        Scanner scan = new Scanner (file);
        List segments = new LinkedList();
        String returnString = "";
        while (scan.hasNextLine()){
            String line = scan.nextLine();
            if (line.contains("ORIGIN")){
//                scan.useDelimiter("[^1234567890actg /\n]+");
                scan.useDelimiter(Pattern.compile("[^1234567890actg /\\n]+"));
                while (scan.hasNext()) {
                    String str = scan.next();
                    if (!str.equals("//") && !str.equals(" ")) {

                        returnString = "";
                        if(str.contains(" ")){
                            for(int i = 0; i < str.length(); i++){
                                if (!"1234567890 /\n".contains(String.valueOf(str.charAt(i)))){
                                    returnString += str.charAt(i);
                                }
                            }
                        }
                        else{
                            returnString = str;
                        }

                        segments.add(returnString);
                    }

                    else {
                        break;
                    }
                }
                scan.useDelimiter(Pattern.compile("[\\n]"));
            }
        }
        return segments;
    }

    public static List <String> getPatterns(int len, String sequence){
        List patterns = new LinkedList();
        int subStart = 0;
        int subEnd = len;

        if (len > sequence.length()){
            return null;
        }

        if (len > 31 || len < 1){
            return null;
        }

        for (int i = len-1; i < sequence.length(); i++){
            patterns.add(sequence.substring(subStart, subEnd));
            subStart++;
            subEnd++;
        }

        return patterns;
    }
}
