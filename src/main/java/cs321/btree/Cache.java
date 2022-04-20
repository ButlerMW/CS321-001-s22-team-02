package cs321.btree;

import java.util.LinkedList;

import cs321.btree.BTree.BTreeNode;

public class Cache<T, E> // T = search key E = what to store
{
  private LinkedList<T> searchKeys = new LinkedList<T>();
  private LinkedList<E> storedKeys = new LinkedList<E>();
  private int size;

  /**
   * 
   * @param size
   */
  public Cache(int size)
  {
    this.size = size;
    this.searchKeys = new LinkedList<T>();
    this.storedKeys = new LinkedList<E>();
  }

  /**
   * Add to Cheche Linked List
   * @param searchKey
   * @param storedKey
   */
  public E AddToCache(T searchKey, E storedKey)
  {
    E removedNode = null;
    if(storedKeys.size() == size)
    {
      removedNode = storedKeys.removeLast();
      searchKeys.removeLast();
    }
    storedKeys.addFirst(storedKey);
    searchKeys.addFirst(searchKey);

    return removedNode;
  }

  /**
   * Removes and returns E 
   * @param target
   * @return
   */
  public E removeFromCache(T target)
  {
    if(searchKeys.indexOf(target) < 0)
    {
      return null;
    }
    return storedKeys.remove(searchKeys.indexOf(target));
  }

  /**
   * Clear cache of both lists
   */
  public void clearCache()
  {
    searchKeys.clear();
    storedKeys.clear();
  }
  
}
