
package cs321.btree;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * BTree Class
 */
public class BTree
{
  private BTreeNode root;
  private int degree;
  private long nextAddress = 1000;
  private int sizeOfBTreeNode = 1000; // calulate
  private int numOfNodes;
//  private RandomAccessFile raf;
  private FileChannel fc;
  private Cache BTreeCache;

    /**
   * BTree Contstructor
   * @param degree
   * @param file
   * @throws FileNotFoundException
   */
  public BTree(int degree, String file, int cacheSize) throws FileNotFoundException
  {
    this.degree = degree;
    root = new BTreeNode(true, 0);
    //nextAddress = 0;
    int sizeOfBTreeNode = 1000; // calulate later
    numOfNodes = 1;
    this.BTreeCache = new Cache<>(cacheSize);
    
    try
    {
      RandomAccessFile raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
        fc = raf.getChannel();
    }
    catch (Exception e)
    {
      System.err.println("Error");
      System.exit(1);
    }
  }

  /**
   * BTreeNode class
   */
  public class BTreeNode
  {
      int size = 0; // number of keys
      boolean isLeaf;
      TreeObject[] keys = new TreeObject[2*degree];
      // int numKeys = 0;
      long[] c = new long[2*degree + 1];  // key array size == 2t+1
      long address; 
      long parent;
      
      /**
       * Default BtreeNode contructor
       */
      public BTreeNode()
      {
        this.isLeaf = isLeaf;
        this.address = address;
        size = 0;
        keys = new TreeObject[2*degree + 2];
        // numKeys = 0;
        c = new long[2*degree +1]; // key array size == 2t + 1
      }
      
   /**
       * BTreeNode Constructor
       * @param address
       * @throws IOException
       */
      public BTreeNode(long address) throws IOException
      {
        // return null;
        fc.position(address);
          ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
          fc.read(bb);
          bb.flip();
          bb.position(0);
//          raf.seek(address);
//          this.size = raf.readInt();
          this.size = bb.getInt();
//          this.address = raf.readLong();
          this.address = bb.getLong();
//          this.leaf = raf.readBoolean();

          // if statement to get leaf boolean
          if(bb.getChar() == 'T')
          {
              this.isLeaf = true;
          }
          else
          {
              this.isLeaf = false;
          }

        for(int i = 1; i <= size; i++) 
        {
//          long l = raf.readLong();
            long l = bb.getLong();
//          int x = raf.readInt();
            int x = bb.getInt();
          keys[i] = new TreeObject(l, x);
        }

        if(!isLeaf)
        {
          for(int i = 1; i <= size+1; i++) 
          {
//            c[i] = raf.readLong();
              c[i] = bb.getLong();
            // raf.writeInt(c[i].getFrequency());
          }
        }
      }

      /**
       * BTreeNode Contstructor
       * 
       * @param isLeaf
       * @param address
       * @throws FileNotFoundException
       */
      public BTreeNode(boolean isLeaf, long address) throws FileNotFoundException
      {
        this.isLeaf = isLeaf;
        this.address = address;
        size = 0;
        // numKeys = 0;
        c = new long[2*degree +1]; // key array size == 2t + 1
      }

      /**
       * BTreeNode Constructor without isLeaf boolean
       * @param address
       * @param fileName
       * @throws IOException
       */
      public BTreeNode(long address, String fileName) throws IOException
      {
//        raf.seek(address);
          ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
          fc.read(bb);
          bb.flip();
//          int age = raf.readInt();
          int age = bb.getInt();
//        this.address = raf.readLong();
          this.address = bb.getLong();
//          int n = raf.readInt();
          int n = bb.getInt();
        for(int i = 0; i < n; i++)
        {
//          long a = raf.readLong();
            long a = bb.getLong();
        }
      }

      /**
       * Insert Non null
       * @param key
       * @throws IOException
       */
      public void BTreeInsertNonFull(long key) throws IOException
      {
//          System.out.println(this.size);
          int i = this.size;
          if(this.isLeaf)
          {
            while(i >= 1 && key < this.keys[i].getDNA())
            {
                this.keys[i+1] = this.keys[i];
                i = i - 1;
            }
            this.keys[i+1] = new TreeObject(key);
//          this.keys[i] = new TreeObject(key);
            this.size++;
            this.DiskWrite();
          }
          else
          {
            while(i >= 1 && key < this.keys[i].getDNA())
            {
               i--;
            }
              i++;
              // DiskRead();
              BTreeNode b = new BTreeNode(c[i]);
            if(b.size == 2*degree - 1)
            {
              this.BTreeSplitChild(i);
              b = new BTreeNode(c[i]);
                if(key > this.keys[i].getDNA())
              {
                i++;
                b = new BTreeNode(c[i]);
              }
            }
              b.BTreeInsertNonFull(key);
          }
      }

      /**
       * Split Child
       * @param i
       * @throws IOException
       */
      public void BTreeSplitChild(int i) throws IOException
      {
          BTreeNode z = new BTreeNode();
          z.address = nextAddress;
          nextAddress += sizeOfBTreeNode;
          BTreeNode y = new BTreeNode(this.c[i]);
          z.isLeaf = y.isLeaf;
          z.size = degree - 1;
          for (int j = 1; j <= degree - 1; j++) 
          {
            z.keys[j] = y.keys[j + degree];
          }
          if(y.isLeaf == false)
          {
            for(int j = 1; j <= degree; j++ )
            {
              z.c[j] = y.c[j + degree];
            }
          }
          y.size = degree - 1;
          for(int j = this.size + 1; j >= i + 1; j--)
          {
            this.c[j + 1] = this.c[j];
          }
          this.c[i + 1] = z.address;
          for (int j = this.size; j >= i; j--)
          {
            this.keys[j + 1] = this.keys[j];
          }
          this.keys[i] = y.keys[degree];
          this.size++;
          y.DiskWrite();
          z.DiskWrite();
          this.DiskWrite();
      }

      /**
       * Writed BTree Node to file
       */
      public void DiskWrite() //BTreeNode, int offset...
      {
        // int i = 0;
        ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
        try
        {
          // RandomAccessFile raf = file; // file cannot be resolved to a variable Java(33554515)
//          raf.seek(address);
            fc.position(address);
//          raf.writeInt(size);
            bb.putInt(size);
//          raf.writeLong(address); // key
            bb.putLong(nextAddress);
//          raf.writeBoolean(leaf);
            if(isLeaf)
            {
                bb.putChar('T'); // true
            }
            else
            {
                bb.putChar('F'); // false
            }

          for(int i = 1; i <= size; i++) 
          {
//            raf.writeLong(keys[i].getDNA());

              bb.putLong(keys[i].getDNA());
//            raf.writeInt(keys[i].getFrequency());
              bb.putInt(keys[i].getFrequency());
          }

          if(!isLeaf)
          {
            for(int i = 1; i <= size+1; i++) 
            {
//              raf.writeLong(c[i]);
                bb.putLong(c[i]);
              // raf.writeInt(c[i].getFrequency());
            }
          }
            fc.write(bb);
        }
        catch (IOException ioe)
        {
          System.out.println("ERROR!");
        }

      }

      /**
       * ToString 
       */
      public String toString()
      {
          int i = 1;
          String result = "";
          while(i <= size && keys[i] != null)
          {
              result += keys[i].getDNA() + " ";
              i++;
          }
          return result;
      }

  }
  
  /**
   * Insert into BTree
   * @param key
   * @throws IOException
   */
  public void BTreeInsert(long key) throws IOException
  {
    BTreeNode r = root;
//    System.out.println(r.size);
    if(r.size == 2*degree - 1)
    {  
      BTreeNode s = new BTreeNode(false, nextAddress);
      nextAddress += sizeOfBTreeNode;
      root = s;
      s.isLeaf = false;
      s.size = 0; 
      s.c[1] = r.address;
      s.BTreeSplitChild(1);
      s.BTreeInsertNonFull(key);
    }
    else
    {
      r.BTreeInsertNonFull(key);
    }
  }

  /**
   * Search for node at the index
   * @param index
   * @return
   * @throws IOException
   */
  public String getNodeAtIndex(int index) throws IOException
  {
     if(index < 1)
     {
      System.out.println("Error");
     }
     Queue<BTreeNode> q = new LinkedList<>();
     q.add(root);
     int i = 1;
     while( !q.isEmpty() )
     {
        BTreeNode n = q.remove();
        if(i == index)
        {
          return n.toString();
        }
        else
        {
          i++;
        }
        if(!n.isLeaf)
        {
          for(int j = 1; j <= n.size + 1; j++)
          {
            // BTreeNode child = n.DiskRead(n.c[j]);
            BTreeNode child = new BTreeNode(n.c[j]);
            q.add(child);
          }
        }
     }
     return null; 
  }
}
