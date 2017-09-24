package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	public Deque() {
		first = last = null;
		count = 0;
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public int size() {
		return count;
	}
	
	
	public void addFirst(Item item) {
		if (item == null)	throw new NullPointerException();
		if (first == null) { // null list
			first = new Node<Item>(item);
			last = first;
		} else if (first.next == null) { // one node
			first = new Node<Item>(item);
			last.prev = first;
			first.next = last;
		} else { // two nodes or more
			Node<Item> oldFirst = first;
			first = new Node<Item>(item);
			first.next = oldFirst;
			oldFirst.prev = first;
		}
		count++;
		return;
	}
	
	public void addLast(Item item) {
		if (item == null)	throw new NullPointerException();
		if (last == null) {
			last = new Node<Item>(item);
			first = last;
		} else if (last.prev == null) {
			last = new Node<Item>(item);
			last.prev = first;
			first.next = last;
		} else {
			Node<Item> oldLast = last;
			last = new Node<Item>(item);
			last.prev = oldLast;
			oldLast.next = last;
		}
		count++;
		return;
	}
	
	public Item removeFirst() {
		Item tmp;
		if (first == null)	throw new NoSuchElementException();
		else if (first == last) {
			tmp = first.item;
			first = last = null;
		} else {
			Node<Item> oldFirst = first;
			first = first.next;
			first.prev = null;
			tmp = oldFirst.item;
			oldFirst = null;
		}
		--count;
		return tmp;
	}
	
	public Item removeLast() {
		Item tmp;
		if (last == null)	throw new NoSuchElementException();
		else if (first == last) {
			tmp = last.item;
			first = last = null;
		} else {
			Node<Item> oldLast = last;
			last = last.prev;
			last.next = null;
			tmp = oldLast.item;
			oldLast = null;	// avoid loitering
		}
		--count;
		return tmp;
	}
	
	public Iterator<Item> iterator() {
		return new DequeIteratorLast(last);
	}
	
	private class DequeIteratorFirst<Item> implements Iterator<Item> {
		Node<Item> curr;
		
		DequeIteratorFirst(Node<Item> first) {
			curr = first;
		}
		
		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
			if (!hasNext())		throw new NoSuchElementException();
			Item rtn = curr.item;
			curr = curr.next;
			return rtn;
		}
		
		public void remove () {
			throw new UnsupportedOperationException();
		}
	}
	
	
	private class DequeIteratorLast implements Iterator<Item> {
		Node<Item> curr;
		
		DequeIteratorLast(Node<Item> last) {
			curr = last;
		}
		
		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
			if (!hasNext())	throw new NoSuchElementException();
			Item rtn = curr.item;
			curr = curr.prev;
			return rtn;
		}
		
		public void remove () {
			throw new UnsupportedOperationException();
		}
	}
	
	static class Node<Item> {
		Item item;
		Node<Item> prev;
		Node<Item> next;
		
		Node(Item i){
			item = i;
			prev = null;
			next = null;
		}
	}
	
	Node<Item> first;
	Node<Item> last;
	int count;
	
	public static void main(String[] args) {
		System.out.println("Creating a test case");
		
		Deque<String> list = new Deque<String>();
		list.addFirst("you");
		list.addFirst("me");
		list.addFirst("him");
		
		list.addLast(", hahhahha!");
		list.addLast("who");
		list.addLast("are");
		list.addLast("you!!!");
		
		for (String i : list) {
			StdOut.print(i + " ");
		}
		StdOut.println();
		
		StdOut.println("---size " + list.size());
		StdOut.println("---Empty? " + list.isEmpty());
		
		list.removeFirst();
		list.removeLast();
		
		for (String i : list) {
			StdOut.print(i + " ");
		}
		StdOut.println();
		
		list.removeFirst();
		list.removeLast();
		list.removeFirst();
		list.removeLast();
		list.removeFirst();
		
		StdOut.println("---size " + list.size());
		StdOut.println("---Empty? " + list.isEmpty());
		
		for (String i : list) {
			StdOut.print(i + " ");
		}
	}
}
