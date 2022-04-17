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
        BTree test = new BTree(2, "TEST");
        test.BTreeInsert(2);
        test.BTreeInsert(4);
        test.BTreeInsert(7);

        String[] expectedNodesContent = new String[]{
                "2 4 7 ", //root content
        };

        assertEquals(expectedNodesContent[0], test.getNodeAtIndex(0));
    }

    @Test
    public void emptyBTree_Add2_Add4_Add7_Add8_2478() throws IOException
    {
        BTree test = new BTree(2, "TEST");
        test.BTreeInsert(2);
        test.BTreeInsert(4);
        test.BTreeInsert(7);
        test.BTreeInsert(8);

        String[] expectedNodesContent = new String[]{
                "4 ", "2 ","7 8 "  //root content
    
        };

        assertEquals(expectedNodesContent[0], test.getNodeAtIndex(0));
    }
}
