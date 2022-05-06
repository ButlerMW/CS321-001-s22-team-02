////package test.java.cs321.create;
//package  cs321.create;
//
//import cs321.common.ParseArgumentException;
////import main.java.cs321.create.GeneBankCreateBTree;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//public class GeneBankCreateBTreeTest
//{
//    private String[] args;
//    private cs321.create.GeneBankCreateBTreeArguments expectedConfiguration;
//    private cs321.create.GeneBankCreateBTreeArguments actualConfiguration;
//
//    @Test
//    public void parse4CorrectArgumentsTest() throws ParseArgumentException
//    {
//        args = new String[4];
//        args[0] = "0";
//        args[1] = "20";
//        args[2] = "fileNameGbk.gbk";
//        args[3] = "13";
//
//        expectedConfiguration = new cs321.create.GeneBankCreateBTreeArguments(false, 20, "fileNameGbk.gbk", 13, 0, 0);
//        actualConfiguration = GeneBankCreateBTree.parseArguments(args);
//        assertEquals(expectedConfiguration, actualConfiguration);
//    }
//
////    @Test
////    public void parse4CorrectArgumentsTest() throws ParseArgumentException
////    {
////        args = new String[4];
////        args[0] = "0";
////        args[1] = "20";
////        args[2] = "fileNameGbk.gbk";
////        args[3] = "13";
////
////        expectedConfiguration = new cs321.create.GeneBankCreateBTreeArguments(false, 20, "fileNameGbk.gbk", 13, 0, 0);
////        actualConfiguration = GeneBankCreateBTree.parseArguments(args);
////        assertEquals(expectedConfiguration, actualConfiguration);
////    }
//}
