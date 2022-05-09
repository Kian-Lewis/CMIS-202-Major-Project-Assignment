package application;

//class creates a generic tree
public interface JunkTree<E> {
	
	//search for e in the tree.
	public boolean search(E e);
	
	//insert e into the tree
	public boolean insert(E e);
	
	//delete e from the tree
	public boolean delete(E e);
	
	//get the number of elements in the tree
	public int getSize();
	
	//inorder traversal from root
	public void inorder();
	
	//postorder traversal from root
	public void postorder();
	
	//preorder traversal from root
	public void preorder();
	
	//check whether the tree is empty
	public default boolean isEmpty() {
		return getSize() == 0;
	}
	
	//check whether the tree contains e
	@SuppressWarnings("unchecked")
	public default boolean contains(Object e) {
		return search( (E) e);
	}
	
	//add e to the tree
	public default boolean add(E e) {
		return insert((E) e);
	}
	
	//remove e from the tree
	@SuppressWarnings("unchecked")
	public default boolean remove(Object e) {
		return delete((E) e);
	}
}
