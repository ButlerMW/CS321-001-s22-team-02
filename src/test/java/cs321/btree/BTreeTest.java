package cs321.btree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BTreeTest
{
    // HINT:
    //  instead of checking all intermediate states of constructing a tree
    //  you can check the final state of the tree and
    //  assert that the constructed tree has the expected number of nodes and
    //  assert that some (or all) of the nodes have the expected values
    @Test
    public void btreeDegree2Test()
    {
//        //TODO instantiate and populate a bTree object
//        int expectedNumberOfNodes = TBD;
//
//        // it is expected that these nodes values will appear in the tree when
//        // using a level traversal (i.e., root, then level 1 from left to right, then
//        // level 2 from left to right, etc.)
//        String[] expectedNodesContent = new String[]{
//                "TBD, TBD",      //root content
//                "TBD",           //first child of root content
//                "TBD, TBD, TBD", //second child of root content
//        };
//
//        assertEquals(expectedNumberOfNodes, bTree.getNumberOfNodes());
//        for (int indexNode = 0; indexNode < expectedNumberOfNodes; indexNode++)
//        {
//            // root has indexNode=0,
//            // first child of root has indexNode=1,
//            // second child of root has indexNode=2, and so on.
//            assertEquals(expectedNodesContent[indexNode], bTree.getArrayOfNodeContentsForNodeIndex(indexNode).toString());
//        }
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_247() throws IOException
    {
        BTree test = new BTree(2, "TEST", 100);
        test.BTreeInsert(2);
        test.BTreeInsert(4);
        test.BTreeInsert(7);

        String[] expectedNodesContent = new String[]{
                "2 4 7 ", //root content
        };

        assertEquals(expectedNodesContent[0], test.getNodeAtIndex(1));
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_2478() throws IOException
    {
        BTree test = new BTree(2, "TEST", 100);
        test.BTreeInsert(2);
        test.BTreeInsert(4);
        test.BTreeInsert(7);
        test.BTreeInsert(8);

        String[] expectedNodesContent = new String[]{
                "4 ", "2 ","7 8 "  //root content
        };

        assertEquals(expectedNodesContent[0], test.getNodeAtIndex(1));
        assertEquals(expectedNodesContent[1], test.getNodeAtIndex(2));
        assertEquals(expectedNodesContent[2], test.getNodeAtIndex(3));
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_Add5_Add6_472568() throws IOException
    {
        BTree test = new BTree(2, "TEST",100);
        test.BTreeInsert(2);
        test.BTreeInsert(4);
        test.BTreeInsert(7);
        test.BTreeInsert(8);
        test.BTreeInsert(5);
        test.BTreeInsert(6);

        String[] expectedNodesContent = new String[]{
                "4 7 ", "2 ","5 6 ","8 "   //root content
        };

        for(int i = 1; i <= 4; i++)
        {
            System.out.println(test.getNodeAtIndex(i));
//            assertEquals(expectedNodesContent[i-1], test.getNodeAtIndex(i));
        }
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_Add5_Add6_Add9_Add10_Add11_47925681011() throws IOException
    {
        BTree test = new BTree(2, "TEST", 100);
        int[] intArray = new int[]{ 2,4,7,8,5,6,9,10,11 };
        for(int i = 0; i < intArray.length; i++)
        {
//            System.out.println(intArray[i]);
            test.BTreeInsert(intArray[i]);
        }

//        test.BTreeInsert(2);
//        test.BTreeInsert(4);
//        test.BTreeInsert(7);
//        test.BTreeInsert(8);
//        test.BTreeInsert(5);
//        test.BTreeInsert(6);
//        test.BTreeInsert(9);
//        test.BTreeInsert(10);
//        test.BTreeInsert(11);

        String[] expectedNodesContent = new String[]{
                "4 7 9 ", "2 ","5 6 ","8 ","10 11 "   //root content
        };

        for(int i = 1; i <= 5; i++)
        {
            assertEquals(expectedNodesContent[i-1], test.getNodeAtIndex(i));
        }
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_Add5_Add6_Add9_Add10_Add11_Add12_4792568101112() throws IOException
    {
        BTree test = new BTree(2, "TEST", 100);
        int[] intArray = new int[]{ 2,4,7,8,5,6,9,10,11,12 };
        for(int i = 0; i < intArray.length; i++)
        {
            test.BTreeInsert(intArray[i]);
        }

        String[] expectedNodesContent = new String[]{
                "7 ","4 ","9 ","2 ","5 6 ","8 ","10 11 12 "   //root content
        };

        for(int i = 1; i <= 7; i++)
        {
            assertEquals(expectedNodesContent[i-1], test.getNodeAtIndex(i));
        }
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_Add5_Add6_Add9_Add10_Add11_Add12_Add3_Add13_Add14_Add15_Add16_711491314235810121516() throws IOException
    {
        BTree test = new BTree(2, "TEST", 100);
        int[] intArray = new int[]{ 2,4,7,8,5,6,9,10,11,12,3,13,14,15,16 };
        for(int i = 0; i < intArray.length; i++)
        {
            test.BTreeInsert(intArray[i]);
        }

        String[] expectedNodesContent = new String[]{
                "7 11 ", "4 ","9 ","13 ","2 3 ","5 6 ","8 ","10 ","12 ","14 15 16 "   //root content
        };

        for(int i = 1; i <= 10; i++)
        {
            assertEquals(expectedNodesContent[i-1], test.getNodeAtIndex(i));
        }
    }
}
