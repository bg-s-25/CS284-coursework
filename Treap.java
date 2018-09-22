/**
 * Treap.java
 * @author Bobby Georgiou
 * Date: Apr 2018
 */

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

public class Treap<E extends Comparable<E>> {
	private static class Node<E> {
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		public Node(E data, int priority) {
			if (data == null) {
				throw new IllegalArgumentException();
			} else {
				this.data = data;
				this.priority = priority;
				left = null;
				right = null;
			}
		}
		
		/**
		 * Performs a right rotation on a node
		 * @return Returns the resultant root node
		 */
		Node<E> rotateRight() {			
			Node<E> newRoot = this.left;
			Node<E> temp = newRoot.right;
			newRoot.right = this;
			this.left = temp;
			return newRoot;
		}
		
		/**
		 * Performs a left rotation on a node
		 * @return Returns the resultant root node
		 */
		Node<E> rotateLeft() {
			Node<E> newRoot = this.right;
			Node<E> temp = newRoot.left;
			newRoot.left = this;
			this.right = temp;
			return newRoot;
		}
		
		/**
		 * Returns a string representation of a single node
		 */
		public String toString() {
			return "(key=" + data + ", priority=" + priority + ")";
		}
	}
	
	private Random priorityGenerator;
	private Node<E> root;
	private final int PRIORITY_RANGE = 50;
	
	public Treap() {
		root = null;
		priorityGenerator = new Random();
	}
	
	public Treap(long seed) {
		root = null;
		priorityGenerator = new Random();
	}
	
	/**
	 * Adds a node to the treap with random priority according to comparison of keys and 
	 * bubbles up the node using reheap method
	 * @param key
	 * @return Returns true if the operation succeeded; otherwise, false
	 */
	boolean add(E key) {
		return add(key, priorityGenerator.nextInt(PRIORITY_RANGE));
	}
	
	/**
	 * Adds a node to the treap with specified priority according to comparison of keys and 
	 * bubbles up the node using reheap method
	 * @param key
	 * @param priority
	 * @return Returns true if the operation succeeded; otherwise, false
	 */
	boolean add(E key, int priority) {
		if (find(key)) { return false; }
		boolean cont = true;
		Node<E> newNode = new Node<E>(key, priority);
		Node<E> curNode;
		Stack<Node<E>> nodeStack = new Stack<Node<E>>();
		
		if (root == null) {
			root = newNode;
			return true;
		} else if (root.left == null && root.right == null) {
			curNode = root;
			nodeStack.push(curNode);
			int comparison = key.compareTo(curNode.data);
			if (comparison > 0) {
				curNode.right = newNode;
			} else {
				curNode.left = newNode;
			}
		} else { // one or both child nodes is/are not null
			curNode = root;
			while (cont) {
				nodeStack.push(curNode);
				int comparison = key.compareTo(curNode.data);
				if (comparison > 0) {
					if (curNode.right != null) {
						curNode = curNode.right;
					} else {
						curNode.right = newNode;
						cont = false;
					}
				} else {
					if (curNode.left != null) {
						curNode = curNode.left;
					} else {
						curNode.left = newNode;
						cont = false;
					}
				}
			}
		}

		reheap(nodeStack, newNode);
		return true;
	}
	
	/**
	 * Helper method for restoring heap invariant
	 * @param nodeStack
	 * @param newNode
	 */
	private void reheap(Stack<Node<E>> nodeStack, Node<E> newNode) {
		Stack<Node<E>> localStack = nodeStack;
		Node<E> curNode;
		
		while (!(localStack.empty())) {
			curNode = localStack.pop();
			if (newNode.priority > curNode.priority) {
				if (curNode.left == newNode) {
					if (localStack.empty()) {
						root = curNode.rotateRight();
					} else {
						localStack.peek().right = curNode.rotateRight();
					}
				} else if (curNode.right == newNode) {
					if (localStack.empty()) {
						root = curNode.rotateLeft();
					} else {
						localStack.peek().left = curNode.rotateLeft();
					}
				}
			}
		}
		
	}
	
	/**
	 * Deletes the node with the specified key from the treap
	 * @param key
	 * @return Returns true if the operation succeeded; otherwise, false
	 */
	boolean delete(E key) {
		if (!(find(key))) { return false; }
		Node<E> delNode = findNode(root, key, root).get(0);
		Node<E> delParent = findNode(root, key, root).get(1);
		
		while (delNode.left != null || delNode.right != null) {
			int comparison = key.compareTo(delParent.data);
			if (delNode.left == null) { // left child of node is null
				if (comparison > 0) {
					delParent.right = delNode.rotateLeft();
					delParent = delParent.right;
				} else {
					delParent.left = delNode.rotateLeft();
					delParent = delParent.left;
				}
			} else if (delNode.right == null) { // right child of node is null
				if (comparison > 0) {
					delParent.right = delNode.rotateRight();
					delParent = delParent.right;
				} else {
					delParent.left = delNode.rotateRight();
					delParent = delParent.left;
				}
			} else { // both children of node are not null
				if (delParent.left == delNode) {
					if (delNode.left.priority > delNode.right.priority) {
						delParent.left = delNode.rotateRight();
						delParent = delParent.left;
					} else {
						delParent.left = delNode.rotateLeft();
						delParent = delParent.left;
					}
				} else {
					if (delNode.left.priority > delNode.right.priority) {
						delParent.right = delNode.rotateRight();
						delParent = delParent.right;
					} else {
						delParent.right = delNode.rotateLeft();
						delParent = delParent.right;
					}
				}
			}
		}
		
		if (delParent.left == delNode) {
			delParent.left = null;
		} else if (delParent.right == delNode) {
			delParent.right = null;
		}
		
		return true;
	}
	
	/**
	 * Helper function for delete operation
	 * @param n
	 * @param key
	 * @param nParent
	 * @return Returns an array list containing the node to be deleted and its parent
	 */
	private ArrayList<Node<E>> findNode(Node<E> n, E key, Node<E> nParent) {
		if (n == null) {
			return null;
		} else if (n.data == key) {
			ArrayList<Node<E>> returnObj = new ArrayList<Node<E>>();
			returnObj.add(n);
			returnObj.add(nParent);
			return returnObj;
		} else {
			return findNode(n.left, key, n) == null ? findNode(n.right, key, n) : findNode(n.left, key, n);
		}
	}
	
	/**
	 * Attempts to locate a node in the treap with the specified key
	 * @param key
	 * @return Returns true if a node with the key is found; otherwise, false
	 */
	boolean find(E key) {
		return find(root, key);
	}
	
	private boolean find(Node<E> root, E key) {
		if (root == null) {
			return false;
		} else if (root.data == key) {
			return true;
		} else {
			return find(root.left, key) || find(root.right, key);
		}
	}
	
	/**
	 * Returns a string representation of the treap
	 */
	public String toString() {
		return toString(root, 0);
	}
	
	private String toString(Node<E> n, int depth) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			r.append("  ");
		}
		if (n == null) {
			r.append("null");
		} else {
			r.append(n.toString());
			r.append("\n");
			r.append(toString(n.left, depth + 1));
			r.append("\n");
			r.append(toString(n.right, depth + 1));
		}
		return r.toString();
	}
}
