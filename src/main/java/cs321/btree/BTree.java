//package main.java.cs321.btree;
package cs321.btree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

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
  private RandomAccessFile raf;
//  private FileChannel fc;
    private long sequenceLength; // sequence length
  private Cache<Long, BTreeNode> BTreeCache;

    /**
   * BTree Contstructor
   * @param degree
   * @param file
   * @throws FileNotFoundException
   */
  public BTree(int degree, String file, int cacheSize) throws FileNotFoundException
  {
    this.degree = degree;
    root = new BTreeNode(true, 12);
    nextAddress = 0;
    int sizeOfBTreeNode = 1000; // calulate later
    numOfNodes = 1;
    this.BTreeCache = new Cache<>(cacheSize);

    try
    {
        raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
        raf.seek(0);
        raf.writeInt(degree);
        raf.writeLong(0);
        raf.writeInt(sizeOfBTreeNode);
        raf.writeLong(nextAddress);
    }
    catch (Exception e)
    {
      System.err.println("Error");
      System.exit(1);
    }
  }

  public void dump(String filename) throws IOException{
    PrintStream ps = new PrintStream(filename);
    PrintStream stdout = System.out;

    dumpNode(root, ps);     

    System.setOut(ps);
    System.setOut(stdout);
    
  }

  public void dumpNode(BTreeNode node,PrintStream ps ) throws IOException{

    if(node.isLeaf){
      for(int i = 1; i <= node.size; i++){
        ps.append(node.keys[i].toString());
        ps.append("\n");
      }

      return; 
    }
    for(int i = 1; i <= node.size; i++){
      BTreeNode child = new BTreeNode(node.c[i]);
      dumpNode(child, ps);
      ps.append(node.keys[i].toString());
      ps.append("\n");
    }
      BTreeNode rChild = new BTreeNode(node.c[node.size + 1]);
      dumpNode(rChild, ps);

  }



  /**
   * BTreeNode class
   */
  public class BTreeNode
  {
      int size = 0; // number of keys
      boolean isLeaf;
      cs321.btree.TreeObject[] keys = new cs321.btree.TreeObject[2*degree];
      long address;
      long c[] = new long[2*degree + 1];
      /**
       * Default BtreeNode contructor
       */
      public BTreeNode()
      {
        this.isLeaf = isLeaf;
        this.address = address;
        size = 0;
        keys = new cs321.btree.TreeObject[2*degree + 2];
        // numKeys = 0;
        c = new long[2*degree +1]; // key array size == 2t + 1
      }

   /**
       * BTreeNode Constructor
       * Disk Read Call
       * @param address
       * @throws IOException
       */
      public BTreeNode(long address) throws IOException
      {
        if(BTreeCache.getSize() > 0){
          BTreeNode node = BTreeCache.removeFromCache(address);
          if(node != null){
            BTreeCache.AddToCache(address, node);
            this.size = node.size;
            this.address = node.address;
            this.keys = node.keys;
          }
        }
        // return null;
        raf.seek(address);
          ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
          raf.read(bb.array());
//          bb.flip();
//          bb.position(0);
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
          keys[i] = new cs321.btree.TreeObject(l, x);
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

//      /**
//       * BTreeNode Constructor without isLeaf boolean
//       * @param address
//       * @param fileName
//       * @throws IOException
//       */
//      public BTreeNode(long address, String fileName) throws IOException
//      {
////        raf.seek(address);
//          ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
////          fc.read(bb);
////          bb.flip();
//          raf.read(bb.array());
////          int age = raf.readInt();
//          int age = bb.getInt();
////        this.address = raf.readLong();
//          this.address = bb.getLong();
////          int n = raf.readInt();
//          int n = bb.getInt();
//        for(int i = 0; i < n; i++)
//        {
////          long a = raf.readLong();
//            long a = bb.getLong();
//        }
//      }

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
            this.keys[i+1] = new cs321.btree.TreeObject(key);
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
              b.BTreeInsertNonFull(key); /// StackOverFlow @emptyBTree_Add2_Add4_Add7_Add8_Add5_Add6_Add9_Add10_Add11_47925681011()
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
//           RandomAccessFile raf = file; // file cannot be resolved to a variable Java(33554515)
          raf.seek(address);
//            fc.position(address);
//          raf.writeInt(size);
            bb.putInt(size);
//          raf.writeLong(address); // key
            bb.putLong(address);
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

            for(int i = 1; i <= size+1; i++) 
            {
//              raf.writeLong(c[i]);
                bb.putLong(c[i]);
              // raf.writeInt(c[i].getFrequency());
            }

            raf.write(bb.array());
//          bb.get
        }
        catch (IOException ioe)
        {
          System.out.println("ERROR!");
        }
      }

      public int search(long key) throws IOException{
        int i = 1;
        while(i <= this.size && key > keys[i].getDNA()){
          i++;
        }
        if(i <= this.size && key == this.keys[i].getDNA()){
          return keys[i].getFrequency();
        }
        else if(this.isLeaf){
          return -1;
        }
        else{
          BTreeNode node = new BTreeNode(this.c[i]);
          return node.search(key);
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
      BTreeNode s = new BTreeNode(false, nextAddress); // false, nextAddress
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