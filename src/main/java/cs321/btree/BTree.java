package cs321.btree;

import cs321.btree.*;

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
    private long nextAddress; // test number 1000
    private int sizeOfBTreeNode; // test number 1000 calulate later
    private int numOfNodes;
    private RandomAccessFile raf;
    private Cache<Long, BTreeNode> BTreeCache;
    private int sequenceLength;

    /**
     * BTree Contstructor
     * @param degree
     * @param file
     * @throws FileNotFoundException
     */
    public BTree(int degree, String file, int cacheSize, int sequenceLength) throws FileNotFoundException
    {
        if(degree == 0)
        {
            degree = (int) Math.floor(4094/40); // optimal degree calculation
            this.degree = degree;
        }
        else
        {
            this.degree = degree;
        }
        root = new BTreeNode(true, 24);

        sizeOfBTreeNode = 4 + 8 + 2 + (2*degree -1)*12 + 16*degree; // calulate later
        nextAddress = 24 + sizeOfBTreeNode;
        numOfNodes = 1;
        this.BTreeCache = new Cache<>(cacheSize);
        this.sequenceLength = sequenceLength;

        try
        {
            raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
            raf.seek(0);
            raf.writeInt(degree);
            raf.writeLong(24);
            raf.writeInt(sequenceLength);
            raf.writeLong(nextAddress);
        }
        catch (Exception e)
        {
            System.err.println("Error");
            System.exit(1);
        }
    }

    /**
     * Search BTree constructor
     * @throws IOException
     */
    public BTree(String file, int cacheSize) throws IOException
    {
        raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
        raf.seek(0);
        degree = raf.readInt();
        sizeOfBTreeNode = 4 + 8 + 2 + (2*degree -1)*12 + 16*degree; // calulate later
        long rootAddress = raf.readLong();
        root = new BTreeNode(rootAddress);
        sequenceLength = raf.readInt();
        nextAddress = raf.readLong();
    }

    public void UpdateRootAddress() throws IOException
    {
        raf.seek(4);
        raf.writeLong(root.address);
    }

    /**
     * BTree Contstructor
     * For Btree test
     * @param degree
     * @param file
     * @throws FileNotFoundException
     */
    public BTree(int degree, String file, int cacheSize) throws FileNotFoundException
    {
        this.degree = (int) Math.floor(4094/40); // optimal degree calculation
        root = new BTreeNode(true, 0);

        sizeOfBTreeNode = 4 + 8 + 2 + (2*degree -1)*12 + 16*degree; // calulate later
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

    /**
     * BTree constructor
     * Create Gene Bank
     * @throws IOException
     */
    public BTree(int degree, String file) throws IOException
    {
        raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
        raf.seek(0);
        raf.writeInt(degree);
        raf.writeLong(0);
        raf.writeInt(sizeOfBTreeNode);
        raf.writeLong(nextAddress);
    }

    /**
     * BTree constructor
     * Search Gene Bank
     * @throws IOException
     */
    public BTree(String file) throws IOException
    {
        raf = new RandomAccessFile(file, "rw"); // file cannot be resolved to a variableJava(33554515) file: ???; mode: "rw" = Read/Write;
        raf.seek(0);
        raf.writeInt(degree);
        raf.writeLong(0);
        raf.writeInt(sizeOfBTreeNode);
        raf.writeLong(nextAddress);
    }

    /**
     * dump
     * @param filename
     * @throws IOException
     */
    public void dump(String filename) throws IOException
    {
        PrintStream ps = new PrintStream(filename);
        PrintStream stdout = System.out;

        dumpNode(root, ps);

        System.setOut(ps);
        System.setOut(stdout);
    }

    /**
     *
     * @param node
     * @param ps
     * @throws IOException
     */
    public void dumpNode(BTreeNode node,PrintStream ps ) throws IOException
    {
        if(node.isLeaf)
        {
            for(int i = 1; i <= node.size; i++)
            {
                ps.append(node.keys[i].toString(sequenceLength));
                ps.append('\n');
            }

            return;
        }

        for(int i = 1; i <= node.size; i++)
        {
            BTreeNode child = new BTreeNode(node.c[i]);
            dumpNode(child, ps);
            ps.append(node.keys[i].toString(sequenceLength));
            ps.append('\n');
        }

        BTreeNode rChild = new BTreeNode(node.c[node.size + 1]);
        dumpNode(rChild, ps);
    }

    /**
     * search
     */
    public int search(long key, PrintStream ps) throws IOException
    {
        return root.search(key, ps);
    }

    /**
     * BTreeNode class
     */
    public class BTreeNode
    {
        int size = 0; // number of keys
        boolean isLeaf;
        TreeObject[] keys = new TreeObject[2*degree];
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
            keys = new TreeObject[2*degree + 2];
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
            if(BTreeCache != null && BTreeCache.getSize() > 0)
            {
                BTreeNode node = BTreeCache.removeFromCache(address);
                if(node != null)
                {
                    BTreeCache.AddToCache(address, node);
                    this.size = node.size;
                    this.address = node.address;
                    this.keys = node.keys;
                }
            }
            raf.seek(address);
            ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
            raf.read(bb.array());
            this.size = bb.getInt();
            this.address = bb.getLong();
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
                long l = bb.getLong();
                int x = bb.getInt();
                keys[i] = new TreeObject(l, x); // tests are failing here
            }
            if(!isLeaf)
            {
                for(int i = 1; i <= size+1; i++)
                {
                    c[i] = bb.getLong();
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
            c = new long[2*degree +1]; // key array size == 2t + 1
        }

        /**
         * InsertNonFull
         * @param key
         * @throws IOException
         */
        public void BTreeInsertNonFull(long key) throws IOException
        {
            int i = this.size;
            if(this.isLeaf)
            {
                for(int j = 1; j <= size; j++) // while loop
                {
                    // Check for duplicates
                    if(j > 0 && this.keys[j].getDNA() == key)
                    {
                        this.keys[j].increaseFrequency();
                        this.DiskWrite();
                        return;
                    }
                }
                while(i >= 1 && key < this.keys[i].getDNA())
                {
                    this.keys[i+1] = this.keys[i];
                    i = i - 1;
                }

                this.keys[i+1] = new TreeObject(key);
                this.size++;
                this.DiskWrite();
            }
            else
            {
                while(i >= 1 && key < this.keys[i].getDNA())
                {
                    i--;
                }
                // Check for duplicates
                if(i > 0 && this.keys[i].getDNA() == key)
                {
                    this.keys[i].increaseFrequency();
                    this.DiskWrite();
                    return;
                }
                i++;
                BTreeNode b = new BTreeNode(c[i]); // DiskRead();
                if(b.size == 2*degree - 1)
                {
                    this.BTreeSplitChild(i);
                    // check if a duplicate
                    if(this.keys[i].getDNA() == key)
                    {
                        this.keys[i].increaseFrequency();
                        this.DiskWrite();
                        return;
                    }
                    b = new BTreeNode(c[i]);
                    if(key > this.keys[i].getDNA())
                    {
                        i++;
                    }
                    b = new BTreeNode(c[i]);
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
         * Write BTree Node to file
         */
        public void DiskWrite()
        {
            ByteBuffer bb = ByteBuffer.allocate(sizeOfBTreeNode);
            try
            {
                raf.seek(address);
                bb.putInt(size);
                bb.putLong(address);
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
                    bb.putLong(keys[i].getDNA());
                    bb.putInt(keys[i].getFrequency());
                }

                for(int i = 1; i <= size+1; i++)
                {
                    bb.putLong(c[i]);
                }

                raf.write(bb.array());
            }
            catch (IOException ioe)
            {
                System.out.println("ERROR!");
            }
        }

        public int search(long key, PrintStream ps) throws IOException
        {
            int i = 1;
            while(i <= this.size && key > keys[i].getDNA())
            {
                i++;
            }
            if(i <= this.size && key == this.keys[i].getDNA())
            {
                ps.append(keys[i].toString());
                ps.append("\n");
                return keys[i].getFrequency();
            }
            else if(this.isLeaf)
            {
                return -1;
            }
            else
            {
                BTreeNode node = new BTreeNode(this.c[i]);
                return node.search(key, ps);
            }
        }

        /**
         m* ToString
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
                    BTreeNode child = new BTreeNode(n.c[j]); // DiskRead
                    q.add(child);
                }
            }
        }
        return null;
    }
}