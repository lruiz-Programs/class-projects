package cs284;

//Use the class signature <E extends Comparable<E>> 
//so that you can use compareTo on the data of nodes.
public class BSTree<E extends Comparable<E>> {
	private Node root;
	private int size;

	public class Node<E>{
	
		private E data;
		private Node left;
		private Node right;
		
		public Node(){
			data = null;
			left = null;
			right = null;
			size = 0;
		}
		public Node(E data){
			this.data = data;
			left = null;
			right = null;
		}
	} //Node Class Ends
	public void inorder(){
		 inorder(root);
	}
	private void inorder(Node root){
		if (root != null) {
			inorder(root.left);
			System.out.print(root.data + ", ");
			inorder(root.right);
		}		
	}
	public void preorder(){
		 preorder(root);
	}
	private void preorder(Node root){
		if(root != null){
			System.out.print(root.data+ " , ");
			preorder(root.left);
			preorder(root.right);
		}
	}
	public Node<E> search(E data){
		 return search(data, root);
	}
	
	private Node<E> search(E data, Node<E> current){

		if(data == null) throw new IllegalArgumentException("Searched item is null!");
		
		int compare = data.compareTo(current.data);
		if (current == null || compare == 0)
			return current;
		
		if(compare < 0)
			return search(data, current.left);
		else
			return search(data, current.right);
	}
	
	/** Wrapper method add.
 	pre: The object to insert must implement the Comparable interface.
 	@param target The Comparable object being inserted
	 */
	public void add(E item) {
		root = add(item, root);
	}
	
	private Node<E> add(E data, Node<E> current) {
		if (data == null )throw new IllegalArgumentException("Add item is null!");
		
		if (current == null) { //base case
			return new Node<>(data);
		}
		int compare = data.compareTo(current.data); //0, 1, -1
		if (compare == 0) { 
			throw new IllegalStateException("add: duplicate data");
		}
		if (compare < 0) { //-1
			current.left = add(data, current.left);
			return current;
		} else { // 1
			current.right = add(data, current.right);
			return current;
		}
	}
	
	public void deleteMin(){
		root = deleteMin(root);
	}
	
	private Node deleteMin(Node current){
		if(current.left == null) return current.right;
		current.left = deleteMin(current.left);
		return current;
	}
	
	public void delete(E data){
		if(data == null) throw new IllegalArgumentException("Delete: data is null");
		root = delete(root, data);
	}
	
	private Node<E> delete(Node<E> current, E data){
		if(current  == null)
			return null;
		int compare = data.compareTo(current.data);
		if(compare < 0)
			current.left = delete(current.left, data);
		else if(compare > 0)
			current.right = delete(current.right, data);
		else{
			//Case 2
			if(current.right == null)
			return current.left;
			else if(current.left == null)
			return current.right;
			//Case 3
			Node temp = current;
			current = min(current.right);
			x.right = deleteMin(temp.right);
			current.left = temp.left;
		}
		return current;
	}
	//return the left most node	
	public Node min(Node current) {
		if (current.left == null) return current;
		else return min(current.left);
	}
	//retur the right most node
	public Node max(Node<E> current) { 
		if (current.right == null) return x;
		else return max(current.right);
		
	}
	
	public Node inOrderSuccessor(E data){
		if(data == null) throw new IllegalArgumentException("Successor: data null!");
		return inOrderSuccessor(root, data);
	}
	/*
	 * If right subtree of node is not NULL, 
		 * then succ lies in right subtree. Do the following. 
		 * Go to right subtree and return the node with minimum 
		 data value in the right subtree.
	 * If right subtree of node is NULL, 
		 * then start from the root and use search-like technique. 
		 Do the following. 
		 * Travel down the tree, if a node’s data is greater than root’s 
		 data then go right side, otherwise, go to left side.
	*/
	public Node inOrderSuccessor(Node<E> node, E data){
		Node<E> x = search(data);
		
		if(x.right != null)
			return min(x.right);
		
		Node successor = null;
		while(node != null){
			int compare = data.compareTo(node.data);
			if(compare < 0){
				successor = node;
				node = node.left;
			}else if(compare > 0)
				node = node.right;
			else break;
			}
		return successor;
	}

	
	
	public static void main(String[] args) {
		BSTree<Integer> btree = new BSTree<Integer>();
		btree.add(22);
		btree.add(10);
		btree.add(30);
		btree.add(3);
		btree.add(12);
		btree.add(5);
		btree.add(28);
		btree.add(34);
		btree.add(25);
		btree.add(29);
		System.out.println("In order Traversal Called:");
		btree.inorder();
		
		BSTree.Node successor = btree.inOrderSuccessor(btree.root, 28);
		System.out.println("\nSuccessor of 25: "+successor.data);
		successor = btree.inOrderSuccessor(btree.root, 34);
		System.out.println("Successor of 29: "+successor.data);

		
		System.out.println("Delete 22 in the tree");
		btree.delete(22);
		btree.inorder();
	}
}
