import java.util.ArrayList;

/**
 * Leighana Ruiz
 * CS 284 E
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

/**
 * class that uses methods for a double linked list 
 * @param <E>
 */
public class IDLList<E> {
	/**
	 * Is the inner class 
	 * defined before the attributes
	 * has data fields for stored data, next node and previous node
	 * 
	 * @param <E>
	 */
	private class Node<E>{
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        /**
         * makes a node with no next or prev data
         * @param data
         */
        public Node(E elem) {
            this.elem = elem;
            next = null;
            prev = null;
        }

        /**
         * makes a node and with the data assigns the proper next and prev
         * @param elem
         * @param next
         * @param prev
         */
        public Node(F elem, Node<E> next, Node<E> prev) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
	}

	// Data Fields
	private Node<E> head; //head node
	private Node<E> tail; //tail node
	private int size; //stores the node position
	private ArrayList<Node<E>> indices = new ArrayList<Node<E>>();


	/**
	 * constructor of an empty double-linked list
	 */
	public IDLList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.indices = new ArrayList<Node<E>>();
	}

	/**
	 * Adds elem at position index 
	 * which continues from where the head node is 
	 * @param index for fast access 
	 * @param elem data to be added.
	 * @return true always  
	 * @throws IndexOutOfBoundsException 
	 */
	public boolean add(int index, E elem) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			add(elem);
			return true;
		} 
		else if (index == size-1){
			append(elem);
			return true;
		}
		else if (index < size){
			Node<E> curr = indices.get(index);
			Node<E> newCurr = new Node<>(elem, curr, curr.prev);
			curr.prev.next = newCurr;
			curr.prev = newCurr;
			size++;
			indices.add(index, newCurr);
		}
		return true;
	}
	
	/**
	 * adds elem at the head of list 
	 * 
	 * @param elem data to be added
	 * @return true always 
	 */
	public boolean add(E elem) {
        if (head == null) {
            head = new Node<E>(elem);
            tail = head;
            return true;
        } else if (head == tail) { // Singleton list
            head = new Node<E>(elem, tail, null);
            tail.prev = head;
        } else {
            head = new Node<E>(elem, head, null);
            head.next.prev = head;
        }

        indices.add(0, head);
        size++;
        return true;
    }
	
	/**
	 * Adds elem as the new last element of the list (i.e tail)
	 * 
	 * @param elem data to be added
	 * @return true alwasy 
	 */
	public boolean append(E elem) {
		if (head == null) {
            head = new Node<E>(elem);
            tail = head;
            size++;
            return true;
        }

        if (head == tail) { 
            tail = new Node<E>(elem, null, head);
            head.next = tail;
            size++;
            return true;
		}
		
        tail.next = new Node<E>(elem, null, tail);
        tail = tail.next;
        size++;
        return true;
	}

	/**
	 * Returns the object at position index from the head
	 * @param index for the purpose of fast access
	 * @return data of the node located at index
	 * @throws IllegalArgumentException
	 */
	public E get(int index) {
		// Illegal arguments (empty list, negative index or too large index)
		if (head == null || index < 0 || index > size - 1) {
			throw new IllegalArgumentException();
		} else {
			return indices.get(index).data;
		}
	}

	/**
	 * Returns the object at the head
	 * @return the data of head
	 * @throws IllegalArgumentException
	 */
	public E getHead() {
		// Empty list
		if (head == null)
			throw new IllegalArgumentException("Empty list!");
		return head.data;
	}

	/**
	 * Returns the object at the tail 
	 * @return the data of tail
	 * @throws IllegalArgumentException
	 */
	public E getLast() {
		// Empty list
		if (size == 0)
			throw new IllegalArgumentException("Empty list!");
		return tail.data;
	}

	/**
	 * Returns the list size 
	 * @return List size
	 */
	public int size() {
		return size;
	}

	/**
	 * Removes and returns the element at the head
	 * @return the data of head
	 * @throws IllegalStateException if no such element in the list
	 */
	public E remove() {
		if (head == null) { 
            throw new IllegalStateException();
        }

        if (head == tail) { 
            Node<E> curr = head;
            head = null;
            tail = null;
            size--;
            indices.clear();
            return curr.data;
        }

        Node<E> curr = head;
        head = head.next;
        indices.remove(0);
        size--;
        return curr.data;
	}

	/**
	 * Removes and returns the element at the tail
	 * @return the data of Tail
	 * @throws IllegalStateException if no such element is in the list
	 */
	public E removeLast() {
		if (head == null) { 
            throw new IllegalStateException();
        }

        if (head == tail) { 
            Node<E> cur = tail;
            head = null;
            tail = null;
            size = 0;
            indices.clear();
            return cur.data;
        }

        Node<E> cur = tail;
        tail = tail.prev;
        tail.next = null;
        indices.remove(size - 1);
        size--;
        return cur.data;
	}

	/**
	 * Removes and returns the element at index
	 * @param index used for fast access 
	 * @return The data of the node located at index
	 * @throws IllegalStateException if there is no such element in the list 
	 */
	public E removeAt(int index) {
		if (index < 0 || index > size) { 
            throw new IllegalStateException("Illegal Index!");
        }
		
        if (index == 0) {
            return remove();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<E> cur = indices.remove(index);
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        size--;
        return cur.data;
	}

	/**
	 * Removes the first occurrence of the elem in the list 
	 * @param elem data to be removed
	 * @return true if found and removed and false if elem not found
	 */
	public boolean remove(E elem) {
		if (head == null) {
            return false;
        }
		if (elem.equals(head.data)){
			remove();
			return true;
		}
		else if (elem.equals(tail.data)) { 
			removeLast();
			return true;
		}

		Node<E> cur = head.next;
		while (cur != null) {
			if (cur.data.equals(elem)) {
				cur.prev.next = cur.next;
				cur.next.prev = cur.prev;
				indices.remove(cur);
				size--;
				return true;
			}
			cur = cur.next;
		}

		return false;
	}

	/**
	 * Presents a string representation of the list
	 */
	public String toString() {
		Node<E> cur = head;
		String a = "";
		while (cur != null) {
			 = a + cur.data + ",";
			cur = cur.next;
		}
		return a;
	}
}