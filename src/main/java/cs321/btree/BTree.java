package src.main.java.cs321.btree;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class BTree
{
  private BTreeNode root;
  private int degree;
  private long nextAddress;
  private int sizeOfBTreeNode; // calulate
  private int numOfNodes;
  
  public BTree(int degree)
  {
        root = new BTreeNode();
        this.degree = degree;
        nextAddress = 0;
        int sizeOfBTreeNode = 1000; // calulate later
        numOfNodes = 1;
  }
      
      public void BTreeInsert(long key)
      {
          // r = T.root
          // if r.n == 2t - 1
          //      s = ALLOCATE-NODE()
          //      T.root = s
          //      s.leaf = FALSE
          //      s.n = 0
          //      s.c1 = r
          //      B-Tree-SplitChild(s, 1)
          //      B-Tree-Insert-NonFull(s,k)
          // else B-Tree-Insert-Nonfull(r, k)
          BTreeNode r = root;
          if(r.keys.length == 2*degree - 1)
          {
              BTreeNode s = ALLOCATE-NODE();
              root = s;
              s.leaf = false;
              s.keys.length = 0;
              s.c = r;
              s.BTreeSplitChild(1);
              s.BTreeInsertNonFull(key);
          }
          else
          {
            r.BTreeInsertNonFull(key);
          }
      }

    public String getNodeAtIndex(int index)
    {
        return root.toString();
        /**
         * if i < 1 
         *  return error
         * q = new queue()
         * q.enqueue(root)
         * while !q.isEmpty()
         *  if(i ==j)
         *    return n 
         *  else 
         *    i++
         *  n = q.dequeue
         * if !n.leaf
         *  for each childPointer c in n 
         *    child = DISK-READ(c)
         *    q.enque(child);
         */
    }

    public class BTreeNode
    {
        int size;
        boolean leaf;
        TreeObject[] keys = new TreeObject[2*degree + 2];
        long[] c = new long[2*degree + 1];  // key array size == 2t+1
        long address; 


        public void BTreeInsertNonFull(long key)
        {
          int i = this.keys.length;
          if(this.leaf)
          {
            while(i >= 1 && key < this.keys[i])
            {
              this.key[i+1] = this.key[i];
              i = i - 1;
            }
            this.key[i] = key;
            this.keys.length = this.keys.length + 1
          }
        }

        public void BTreeSplitChild(int i)
        {
            // z = ALLOCATE-NODE()
            // y = x.ci
            // z.leaf = y.leaf
            // z.n = t-1
            // for j = 1 to t-1
            //      z.keyj = y.keyj+t
            // if not y.leaf
            //      for j = 1 to t
            //          z.cj = y.cj+t
            // y.n = t - 1
            // for j = x.n + 1 downto i + 1
            //      x.cj+1 = x.cj
            // x.ci+1 = z
            // for j = x.n + 1 downto i
//                    x.keyj+1 = x.keyj
            // x.keyi = y.keyt
            // x.n = x.n + 1
            // Disk-Write(y)
            // Disk-Write(y)
            // Disk-Write(y)
        }

        public void DiskWrite()
        {

        }

        public BTreeNode DiskRead(long i) //static
        {

        }

        public String toString()
        {
            int i = 1;
            String result = "";
            while(i < keys.length && keys[i] != null)
            {
                result += keys[i] + ", ";
                i++;
            }
                return result;
        }

    }
}

