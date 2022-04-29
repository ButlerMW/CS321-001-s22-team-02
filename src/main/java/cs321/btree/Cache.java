//package main.java.cs321.btree;
package cs321.btree;

import java.util.LinkedList;

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

  public int getSize()
  {
    return size;
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

    int index =  searchKeys.indexOf(target);
    if(index < 0)
    {
      return null;
    }

    searchKeys.remove(index);
    return storedKeys.remove(index);
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
