package application;

import java.util.ArrayList;

//class creates a Binary Search Tree
public class JunkBST<E> implements JunkTree<E> {
	
	//Create variables
	protected TreeNode<E> root; //root node of tree
	protected int size = 0; //size of the tree
	protected java.util.Comparator<E> c; //comparator for the tree
	
	//create a default BST with natural order comparator
	@SuppressWarnings("unchecked")
	public JunkBST() {
		this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
	}
	
	//Create a BST with a specified comparator
	public JunkBST(java.util.Comparator<E> c) {
		this.c = c;
	}
	
	//Create a binary tree from an array
	@SuppressWarnings("unchecked")
	public JunkBST(E[] objects) {
		this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
	}
	
	//Create a binary tree from an arraylist
	@SuppressWarnings("unchecked")
	public JunkBST(ArrayList<E> objects) {
		this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
		for (E object : objects)  this.add(object);
	}
	
	//empty the tree then add contents from array list
	public void populate(ArrayList<E> objects) {
		this.clear();
		for (E object : objects) {
			this.add(object);
		}
	}
	
	//return true if the element is in the tree
	@Override
	public boolean search(E e) {
		TreeNode<E> current = root; //starting from the root
		
		while(current != null) {
			if (c.compare(e,  current.element) < 0) {
				current = current.left;
			}
			else if (c.compare(e, current.element) > 0) {
				current = current.right;
			}
			else {
				return true; //element is found
			}
		}
		
		return false;
	}
	
	//insert an element into the tree and return true if successfully inserted
	@Override
	public boolean insert(E e) {
		if (root == null) {
			root = createNewNode(e);
		}
		else {
			//Locate parent node
			TreeNode<E> parent = null;
			TreeNode<E> current = root;
			while (current != null) {
				if (c.compare(e,  current.element) < 0) {
					parent = current;
					current = current.left;
				}
				else if (c.compare(e,  current.element) > 0) {
					parent = current;
					current = current.right;
				}
				else {
					return false; //duplicate node not to be inserted
				}
			}
			
			//create a new node and attach it to the parent node
			if (c.compare(e,  parent.element) < 0) {
				parent.left = createNewNode(e);
			}
			else {
				parent.right = createNewNode(e);
			}
		}
		
		size++;
		return true; //element succressfully inserted
	}
	
	//create new generic TreeNode
	protected TreeNode<E>  createNewNode(E e) {
		return new TreeNode<>(e);
	}
	
	//inorder traversal from the root
	@Override 
	public void inorder() {
		inorder(root);
	}
	
	//inorder traversal from subtree
	protected void inorder(TreeNode<E> root) {
		if(root == null) {
			return;
		}
		inorder(root.left);
		System.out.print(root.element + " ");
		inorder(root.right);
	}
	
	//postorder traversal from the root
	@Override
	public void postorder() {
		postorder(root);
	}
	
	//postorder traversal from a subtree
	public void postorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.element + " ");
	}
	
	//preorder traversal from the root
	@Override
	public void preorder() {
		preorder(root);
	}
	
	//preorder traversal from a subtree
	public void preorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		System.out.print(root.element + " ");
		preorder(root.left);
		preorder(root.right);
	}
	
	//Static inner class because it doesn't access any instance members defined in its outer class.
	public static class TreeNode<E> {
		protected E element;
		protected TreeNode<E> left;
		protected TreeNode<E> right;
		
		public TreeNode(E e) {
			element = e;
		}
	}
	
	//Get number of nodes in the tree
	@Override
	public int getSize() {
		return size;
	}
	
	//return the root of the tree
	public TreeNode<E> getRoot() {
		return root;
	}
	
	//return a path from the root leading to the specified element
	public ArrayList<TreeNode<E>> path(E e) {
		ArrayList<TreeNode<E>> list = new ArrayList<>();
		TreeNode<E> current = root; //start from root
		
		while (current != null) {
			list.add(current);//Adds current node to the list
			if (c.compare(e, current.element) < 0) {
				current = current.left;
			}
			else if (c.compare(e, current.element) > 0) {
				current = current.right;
			}
			else {
				break;
			}
		}
		
		//return the array list of nodes
		return list;
	}
	
	//Delete an element from the tree; returns true if successfully deleted, false if not in the tree
	@Override
	public boolean delete(E e) {
		TreeNode<E> parent = null;
		TreeNode<E> current = root;
		
		while (current != null) {
			if (c.compare(e, current.element) < 0) {
				parent = current;
				current = current.left;
			}
			else if (c.compare(e, current.element) > 0) {
				parent = current;
				current = current.right;
			}
			else {
				break; // Element is in the tree pointed at by current
			}
		}
		
		if (current == null) {
			return false; //Element not in the tree
		}
		
		//If current has no left child
		if (current.left == null) {
			//connect parent with the right child of the node
			if (parent == null) {
				root = current.right;
			}
			else {
				if (c.compare(e,  parent.element) < 0) {
					parent.left = current.right;
				}
				else {
					parent.right = current.right;
				}
			}
		}
		else { //if current node has a left child, locate rightmost node in left subtree of current and its parent
			TreeNode<E> parentOfRightMost = current;
			TreeNode<E> rightMost = current.left;
			
			while (rightMost.right != null) {
				parentOfRightMost = rightMost;
				rightMost = rightMost.right;//keeps going right
			}
			
			//replace element in current by the element in rightMost
			current.element = rightMost.element;
			
			//Eliminate rightmost node
			if (parentOfRightMost.right == rightMost) {
				parentOfRightMost.right = rightMost.left;
			}
			else {
				//if parentOfRightMost == current
				parentOfRightMost.left = rightMost.left;
			}
		}
		
		size--;
		return true; //element successfully removed
	}
	
	//Obtain iterator using inorder
	public java.util.Iterator<E> iterator() {
		return new InorderIterator();
	}
	
	//inner class InorderIterator
	private class InorderIterator implements java.util.Iterator<E> {
		//store elements in a list
		private ArrayList<E> list = new ArrayList<>();
		private int current = 0; //point to current element in list
		
		public InorderIterator() {
			inorder(); //traverse tree and store elements in list
		}
		
		//Inorder traversal from the root
		private void inorder() {
			inorder(root);
		}
		
		//Inorder traversal from a subtree
		private void inorder(TreeNode<E> root) {
			if (root == null) {
				return;
			}
			inorder(root.left);
			list.add(root.element);
			inorder(root.right);
		}
		
		//check for more elements for traversing
		@Override
		public boolean hasNext() {
			if (current < list.size()) {
				return true;
			}
			return false;
		}
		
		//Get current element and move to next
		@Override
		public E next() {
			return list.get(current++);
		}
		
		//remove the element returned by the last next()
		@Override
		public void remove() {
			//if next() hasn't been called yet
			if (current == 0) {
				throw new IllegalStateException();
			}
			
			delete(list.get(--current));
			list.clear(); //clear list
			inorder(); //rebuild list
		}
	}
	
	//remove all element from the tree
	public void clear() {
		root = null;
		size = 0;
	}

}



















