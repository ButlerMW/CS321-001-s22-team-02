package test.java.cs321.create;

import main.java.cs321.create.GeneBankCreateBTree;

import java.util.LinkedList;
import java.util.List;


public class SequenceUtilsTest
{	public static void main(String[] args){

        List <String> l = GeneBankCreateBTree.getPatterns(3, "TAGCTAAC");
        List <String> e = new LinkedList<>();

        e.add("TAG");
        e.add("AGC");
        e.add("GCT");
        e.add("CTA");
        e.add("TAA");
        e.add("AAC");

        for (int i = 0; i < e.size(); i++){
            System.out.println(l.get(i) + " " + e.get(i) + " " + (l.get(i) == e.get(i)));

        }





//        List <String> Tst = GeneBankCreateBTree.Parse("TEST");
//        List <String> Exp = new LinkedList<>();
//
//        Exp.add("ACGCT");
//        Exp.add("ACT");
//
//        for (int i = 0; i < Exp.size(); i++){
//            System.out.println(Tst.get(i) + " " + Exp.get(i) + " " + (Tst.get(i) == Exp.get(i)));
//
//        }


    }
}

