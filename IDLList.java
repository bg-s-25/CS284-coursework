/**
 * IDLList.java
 * @author Bobby Georgiou
 * Date: Mar 2018
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class IDLList<E> {
	
	public class Node<E> {
		private E data;
		private Node<E> next;
		private Node<E> prev;
		
		Node(E elem) {
			data = elem;
			prev = null;
			next = null;
		}
		
		Node(E elem, Node<E> prev, Node<E> next) {
			data = elem;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;

	public IDLList() {
		head = null;
		tail = null;
		size = 0;
		indices = new ArrayList<Node<E>>();
	}
	
	/**
	 * Adds elem at position index
	 * @param index
	 * @param elem
	 * @return Returns boolean value representing if the operation was successful
	 */
	public boolean add(int index, E elem) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		} else {
			Node<E> adding;
			if (size == 0) {
				adding = new Node<E>(elem, null, null);
				head = adding;
				tail = adding;
			} else {
				if (index <= size - 1) { // adding between nodes
					Node<E> oldItem = indices.get(index);
					adding = new Node<E>(elem, oldItem.prev, oldItem);
					oldItem.prev = adding;
					if (index == 0) {
						head = adding;
					}
				} else { // appending (index == size)
					append(elem);
					return true;
				}
			}
			indices.add(index, adding);
			size++;
			return true;
		}
	}
	
	/**
	 * Adds elem at the head of the list
	 * @param elem
	 * @return Returns boolean value representing if the operation was successful
	 */
	public boolean add(E elem) {
		Node<E> newHead = new Node<E>(elem, null, head);
		if (size == 0) {
			head = newHead;
			tail = head;
		} else if (size == 1) {
			head = newHead;
			tail.prev = head;
		} else {
			head.next.prev = newHead;
			head = newHead;
		}
		indices.add(0, head);
		size++;
		return true;
	}
	
	/**
	 * Adds elem at the end of the list
	 * @param elem
	 * @return Returns boolean value representing if the operation was successful
	 */
	public boolean append(E elem) {
		Node<E> newTail = new Node<E>(elem, tail, null);
		if (size == 0) {
			tail = newTail;
			head = tail;
		} else if (size == 1) {
			tail = newTail;
			head.next = tail;
		} else {
			Node<E> prevTail = tail;
			tail = newTail;
			prevTail.next = tail;
		}
		indices.add(tail);
		size++;
		return true;
	}
	
	/**
	 * Gets object at position index
	 * @param index
	 * @return Returns the object at position index
	 */
	public E get(int index) {
		if (indices.get(index) == null) {
			throw new NoSuchElementException();
		} else {
			return indices.get(index).data;		
		}
	}
	
	/**
	 * Gets object at the head of the list
	 * @return Returns the head object
	 */
	public E getHead() {
		if (head == null) {
			throw new NoSuchElementException();
		} else {
			return indices.get(0).data;	
		}
	}
	
	/**
	 * Gets object at the tail of the list
	 * @return Returns the tail object
	 */
	public E getLast() {
		if (tail == null) {
			throw new NoSuchElementException();
		} else {
			return indices.get(size - 1).data;	
		}
	}
	
	/**
	 * Returns the size of the list
	 * @return Returns size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Removes and returns the element at the head
	 * @return Returns the head object
	 */
	public E remove() {
		if (head == null) {
			throw new NoSuchElementException();
		} else {
			Node<E> temp = head;
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				head = head.next;
				head.prev = null;
			}
			indices.remove(0);
			size--;
			return temp.data;
		}
	}
	
	/**
	 * Removes and returns the last element
	 * @return Returns the tail object
	 */
	public E removeLast() {
		if (tail == null) {
			throw new NoSuchElementException();
		} else {
			Node<E> temp = tail;
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				tail = tail.prev;
				tail.next = null;
			}
			indices.remove(size - 1);
			size--;
			return temp.data;
		}
	}
	
	/**
	 * Removes and returns the element at position index
	 * @param index
	 * @return Returns the object at position index
	 */
	public E removeAt(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			Node<E> temp = indices.get(index);
			if (head == tail) {
				head = null;
				tail = null;
			} else {
				if (index == size - 1) {
					temp.prev.next = null;
					temp.prev = null;
					tail = temp.next;
				} else if (index == 0) {
					head = head.next;
					head.prev = null;
				} else {
					temp.prev.next = temp.next;
					temp.next.prev = temp.prev;
				}
			}
			indices.remove(index);
			size--;
			return temp.data;
		}
	}
	
	/**
	 * Removes the first occurrence of elem in the list
	 * @param elem
	 * @return Returns a boolean value representing if an occurrence of elem was removed
	 */
	public boolean remove(E elem) {
		for (int i = 0; i < size; i++) {
			if (indices.get(i).data == elem) {
				removeAt(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of IDLList
	 */
	public String toString() {
		Node<E> current = head;
		StringBuilder sb = new StringBuilder();
		while (current != null) {
			sb.append("," + current.data);
			current = current.next;
		}
		return sb.toString();
	}
}
