package randomizedQueue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item>{
	static final int DEFAULT_CAPACITY = 10;
	Item [] list;
	int count = 0;
	int modCount = 0;
	
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		list = (Item []) new Object[DEFAULT_CAPACITY];
		count = 0;
//		traverse();
	}
	public boolean isEmpty() {
		return count == 0;
	}
	public int size() {
		return count;
	}
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		Item[] tmp = (Item [])new Object[capacity];
		for (int i = 0; i < count; ++i)
			tmp[i] = list[i];
		list = tmp;
	}
	public void enqueue(Item item) {	// direct add to the end of array
		if (item == null)	throw new NullPointerException();
		if (count == list.length)
			ensureCapacity(list.length * 2);
		list[count++] = item;
		++modCount;
	}
	
	public Item dequeue() {
		if (count == 0)	throw new NoSuchElementException();
		if(count <= list.length / 4)
			ensureCapacity(list.length / 2);
		
		Item rtn;
		Random rd = new Random();
		
		int rmvIndex = rd.nextInt(count);
		rtn = list[rmvIndex];
		list[rmvIndex] = (count == 1) ? null : list[count - 1];	// move last to dequeue position, if only one exists, make it null
		list[--count] = null;
		++modCount;
		
		return rtn;
	}
	
	public Item sample() {
		if (count == 0)
			throw new NoSuchElementException("No element in the list!!");
		Random rd = new Random();
		int randomIndex = rd.nextInt(count);

		return list[randomIndex];
	}
	public Iterator<Item> iterator() {
		return new QueueIterator<Item>(list);
	}
	
//	public void show() {
//		for (int i = 0; i < count; ++i)
//			StdOut.print(list[i] + " ");
//		StdOut.println();
//	}
	
	@SuppressWarnings("hiding")
	class QueueIterator<Item> implements Iterator<Item> {
		Set<Integer> set;
		Item[] curr;
		int itemNumber;
		int remainItmNumber;
		int expectedModCount;
		
		QueueIterator(Item[] list) {
			set = new HashSet<Integer>();
			expectedModCount = modCount;
			remainItmNumber = count;
			itemNumber = count;
			curr = list;
		}
		
		@Override
		public boolean hasNext() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException("List changed when iterating!!");
			return itemNumber > 0;
		}

		@Override
		public Item next() {
			Random rd = new Random();
			int nextIdx = rd.nextInt(remainItmNumber);
			
			while (set.contains(nextIdx))
				nextIdx = rd.nextInt(remainItmNumber);
			set.add(nextIdx);
			--itemNumber;

			return curr[nextIdx];
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("A");
		rq.enqueue("B");
		rq.enqueue("C");
		rq.enqueue("D");
		rq.enqueue("E");
		rq.enqueue("F");
		rq.enqueue("G");
		rq.enqueue("H");
		rq.enqueue("I");
		rq.enqueue("J");
		

		System.out.println();

		StdOut.println("---- size " + rq.size());
		StdOut.println("---- is Empty? " + rq.isEmpty());
		for (String s : rq) {
			StdOut.print(s + " ");
		}
		StdOut.println();
		
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();
		rq.dequeue();

		for (int i = 0; i < 10; ++i)
			StdOut.print(rq.sample() + " ");
		
		System.out.println();
		StdOut.println("---- size " + rq.size());
		StdOut.println("---- is empty " + rq.isEmpty());
		for (String s : rq) {
			StdOut.print(s + " ");
		}
	}
}
