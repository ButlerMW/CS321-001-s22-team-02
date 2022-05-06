// package main.java.cs321.search;
//package cs321.search;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GeneBankSearchDatabase
{
    public static void main(String[] args) throws Exception
    {
        if (args.length > 3 || args.length < 2)
        {
            printUsage();
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        Scanner file = new Scanner(new File(args[1]));

        while(file.hasNextLine())
        {
            String sequence = file.nextLine();

            Statement s = connection.createStatement();

            ResultSet matches = s.executeQuery("SELECT * FROM DNA WHERE SEQUENCE = \"" + sequence + "\"");

            while (matches.next())
            {
                String key = matches.getString(1);
                String frequency = matches.getString(2);

                System.out.println (key + ": " + frequency);

            }
        }
    }

    public static void printUsage()
    {
        System.out.println();
        System.exit(1);
    }
}
