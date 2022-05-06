# Team Members

Last Name       | First Name | GitHub User Name
--------------- |------------| --------------------
Butler          | Michael    | ButlerMW
Faizi           | Abdullah   | abdullahf50
Davis           | Luke       | lukedavis43


# Cache Performance Results
### Results for GeneBankCreateBTree.java
java GeneBankCreateBTree <0/1(no/with Cache)> 3 <gbk file> 6 <cache size> 1

| gbk file | no cache  | 100       | 500       |
|----------|-----------|-----------|-----------|
| test0    | 648 ms    | 540 ms    | 698 ms    |
| test1    | 979 ms    | 785 ms    | 797 ms    |
| test2    | 1005 ms   | 816 ms    | 928 ms    |
| test3    | 882 ms    | 894 ms    | 808 ms    |
| test4    | 195 ms    | 208 ms    | 162 ms    |
| test5    | 253018 ms | 197287 ms | 167402 ms |

java GeneBankCreateBTree <0/1(no/with Cache)> 0 <gbk file> 6 <cache size> 1

| gbk file | no cache  | 100       | 500       |
|----------|-----------|-----------|-----------|
| test0    | 338 ms    | 352 ms    | 363 ms    |
| test1    | 356 ms    | 347 ms    | 361 ms    |
| test2    | 438 ms    | 433 ms    | 440 ms    |
| test3    | 484 ms    | 436 ms    | 428 ms    |
| test4    | 102 ms    | 102 ms    | 99 ms     |
| test5    | 180531 ms | 158021 ms | 175323 ms |

### Results for GeneBankSearchBTree.java
java GeneBankSearchBTree 0 testRAF query6 0

| Query | no cache  | 100        | 500        |
|-------|-----------|------------|------------|
| 1     | 168 ms    | 169 ms     | 160 ms     |
| 2     | 181 ms    | 291 ms     | 185 ms     |
| 3     | 207 ms    | 253 ms     | 408 ms     |
| 4     | 270 ms    | 253 ms     | 193 ms     |
| 5     | 694 ms    | 759 ms     | 748 ms     |
| 6     | 1688 ms   | 1485 ms    | 1735 ms    |
| 7     | 1848 ms   | 2033 ms    | 1542 ms    |
| 16    | 484 ms    | 436 ms     | 428 ms     |
| 20    | 102 ms    | 102 ms     | 99 ms      |
| 31    | 180531 ms | 158021 ms  | 175323 ms  |

# BTree Binary File Format and Layout
Binary Files will be formated `xyz.bgk.btree.data.k.t`
xyz.bgk representing the name of the gbk file, k is the sequence length, and t is the degree.

The BTree file contains two classes, the BTree class and the BTreeNode class.
Other classes and files used in this program are listed below. The BTree class
will write metadata consisting of the size, degree, the offset and the next address
of the root node to the disk. The nodes contain an `isLeaf` variable and the number
of keys is written. Each of the nodes contains an array of children and TreeObjects.
Then this information is used to manipulate the BTree more information and details
are commented in the files.

### files in project
- BTree.java
- Cache.java
- GeneBankCreateBTree.java
- GeneBankSearchBTree.java
- GeneBankSearchDatabase.java
- TreeObject.java
- README

# Additional Notes
The results in the table are from a one time track and report, which provides
some inaccuracy. However, the overall results of cache speed and
optimized degree efficiency are still presented.

Progress bar was created to basic progress report during the parsing and sequencing
process of the program. `parsing..` is printed right before the parsing phase begins.
`printing..` is printed before the sequencing phase of the project. In between times
there are two counters which get incremented followed by an `if` statement on
printing additional `.` in the terminal.

