/**
 * MyListLL.java
 * @author Bobby Georgiou
 * Date: Mar 2018
 */

public class MyListLL<E extends Comparable<E>> {
	
	public static class Node<E> {
		private E data;
		private Node<E> next; // points to next node list
		
		Node(E data) {
			this.data = data;
			next = null;
		}
		
		Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node<E> head;
	int size;
	
	// Constructor
	MyListLL() {
		head = null;
		size = 0;
	}
	
	public boolean add(E item) {
		head = new Node<E>(item, head); // adds to beginning of linked list
		size++;
		return true;
	}
	
	public boolean addLast(E item) {
		// check if list is empty; if it is, head must be updated
		if (head == null) {
			head = new Node<E>(item);
		} else {
			Node<E> current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new Node<E>(item);
		}
		size++;
		return true;
	}
	
	/**
	 * Removes first item of the list
	 * @return returns the item removed
	 */
	public E removeFirst() {
		if (head == null) {
			throw new IllegalArgumentException();
		}
		E temp = head.data;
		head = head.next;
		size--;
		return temp;
	}
	
	/**
	 * Removes last item of the list
	 * @return returns the item removed
	 */
	public E removeLast() {
		if (head == null) {
			throw new IllegalArgumentException();
		}
		// list has 1 item
		E temp;
		if (head.next == null) {
			temp = head.data;
			head = null;
		} else {
			// list has two or more items
			Node<E> current = head;
			while (current.next.next != null) {
				current = current.next;
			}
			temp = current.next.data;
			current.next = null;
		}
		size--;
		return temp;
	}
	
	/**
	 * Removes item of the list at index
	 * @param index
	 * @return returns the item removed
	 */
	public E remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Index is out of bounds");
		}
		if (index == 0) {
			// remove first item
			return this.removeFirst();
		} else {
			Node<E> current = head;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			E temp = current.next.data;
			current.next = current.next.next; // set to node after the one that was removed
			size--;
			return temp;
		}
	}
	
	private boolean isSingleton(Node<E> list) {
		return list != null && list.next == null;
	}
	
	private Node<E> take(int n, Node<E> list) {
		if (n == 0) {
			return null;
		} else {
			return list == null ? null : new Node<E>(list.data, take(n - 1, list.next));
		}
	}
	
	/**
	 * Takes n nodes from the current instance of class MyListLL 
	 * @param n
	 * @return Returns a new MyListLL list containing just the first n nodes
	 */
	public MyListLL<E> take(int n) {
		MyListLL<E> result = new MyListLL<E>();
		result.size = Math.min(n, this.size);
		result.head = take(n, this.head);
		return result;
	}
	
	public String toString() {
		String r = "";
		Node<E> current = head;
		while (current != null) { // print each data item of data until the current node is NULL
			r += "," + current.data;
			current = current.next;
		}
		return r;
	}
	
	public static void main(String[] args) {
		MyListLL<String> l = new MyListLL<String>();
		
		l.add("Louise");
		l.add("Anne");
		l.add("Tom");
		l.add("Charly");
		l.addLast("Roy");
		
		System.out.println(l);
	}
}
