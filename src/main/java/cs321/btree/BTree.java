
package cs321.btree;

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
    root = new BTreeNode(true, 0);
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
          if(r.numKeys == 2*degree - 1)
          {
              // BTreeNode s = new BTreeNode();
              // nextAddress += sizeOfBTreeNode;
              // root = s;
              // s.leaf = false;
              // s.numKeys = 0; // nnope
              // s.c[1] = r.address; // nope
              // s.BTreeSplitChild(1);
              // s.BTreeInsertNonFull(key);
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

        TreeObject[] keys = new TreeObject[2*degree];
        int numKeys = 0;

        long[] c = new long[2*degree + 1];  // key array size == 2t+1
        long address; 

        public BTreeNode(boolean leaf, long address)
        {
          this.leaf = leaf;
          this.address = address;
          size = 0;
          numKeys = 0;
          c = new long[2*degree +1]; // key array size == 2t + 1
        }

        public BTreeNode()
        {
          this.leaf = leaf;
          this.address = address;
          size = 0;
          keys = new TreeObject[2*degree + 2];
          numKeys = 0;
          c = new long[2*degree +1]; // key array size == 2t + 1
        }


        public void BTreeInsertNonFull(long key)
        {
          int i = this.size;
          if(this.leaf)
          {
            while(i >= 1 && key < this.keys[i].getDNA())
            {
              this.keys[i+1] = this.keys[i];
              i = i - 1;
            }
            this.keys[i+1] = new TreeObject(key);
           size++;
          }
        }

        public void BTreeSplitChild(int i)
        {
            BTreeNode z = new BTreeNode();
            BTreeNode y = DiskRead(this.c[i]);
            z.leaf = y.leaf;
            z.numKeys = degree - 1;
            for (int j = 1; j <= degree - 1; j++) {
                z.keys[j] = y.keys[j + degree];
            }
            
            if(y.leaf == false){
              for(int j = 1; j <= degree; j++ ){
                  z.c[j] = y.c[j + degree];
              }
            }

            y.numKeys = degree - 1;
            
            for(int j = this.numKeys + 1; j >= i + 1; j--){
                this.c[j + 1] = this.c[j];
            }

            this.c[i + 1] = z.address;

            for (int j = this.numKeys; j >= i; j--){
                this.keys[j + 1] = this.keys[j];
            }
            this.keys[i] = y.keys[degree];
            this.numKeys++;
            y.DiskWrite();
            z.DiskWrite();
            this.DiskWrite();
        }

        public void DiskWrite()
        {

        }

        public BTreeNode DiskRead(long address){
          return null;
        }

       // public BTreeNode DiskRead(long i) //static
       // {

       // }

        public String toString()
        {
            int i = 1;
            String result = "";
            while(i < keys.length && keys[i] != null)
            {
                result += keys[i].getDNA() + " ";
                i++;
            }
                return result;
        }

    }
}

