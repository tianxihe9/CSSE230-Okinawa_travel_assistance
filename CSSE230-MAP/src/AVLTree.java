
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class AVLTree<T extends Comparable<? super T>> implements Iterable<T> {
	public BinaryNode root;
	private int rotationCount;

	public AVLTree() {
		root = null;
		rotationCount = 0;
	}

	public AVLTree(BinaryNode n) {
		root = n;
	}

	public BinaryNode getMin() {
		return this.root.getMin();
	}

	public boolean isEmpty() {
		if (this.root == null) {
			return true;
		}
		return false;
	}

	public int height() {
		if (this.isEmpty()) {
			return -1;
		}

		return root.getHeight();
	}

	public int size() {
		if (this.isEmpty()) {
			return 0;
		}
		return root.getSize();
	}

	public String toString() {
		if (this.isEmpty()) {
			return "[]";
		}
		return Arrays.toString(this.toArray());

	}

	public ArrayList<T> toArrayList() {
		ArrayList aList = new ArrayList();
		if (size() == 0)
			return aList;
		return root.toArrayList(aList);
	}

	public Object[] toArray() {
		return toArrayList().toArray();
	}

	public Iterator<T> preOrderIterator() {
		return new PreOrderLazyIterator(this);
	}

	@Override
	public Iterator iterator() {
		return new PreOrderLazyIterator(this);
	}

	// ******************************************************************************
	//
	// Tree Insert
	//
	// ******************************************************************************

	public Boolean insert(T i, String city, ArrayList<String> path, double time) {
		if (i == null)
			throw new IllegalArgumentException();
		BinaryNode newNode = new BinaryNode(i, city, path, time);
		if (root == null) {
			this.root = newNode;
			return true;
		}
		AVLInsertBox box = new AVLInsertBox();
		this.root = this.root.insert(newNode, box);
		this.rotationCount += box.getRotationCount();
		return box.getBoolean();
	}

	public Boolean remove(T object) {
		if (object == null)
			throw new IllegalArgumentException();
		if (this.root == null) {
			return false;
		}
		if (this.root.getElement() == object && this.root.hasNoChild()) {
			this.root = null;
			return true;
		}
		MyBoolean checkBox = new MyBoolean(object);
		AVLInsertBox rotationBox = new AVLInsertBox();
		root = root.remove(checkBox, rotationBox);
		this.rotationCount += rotationBox.getRotationCount();
		return checkBox.isRemoveable();
	}

	public class BinaryNode {
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		protected String city;
		protected Double time;
		protected ArrayList<String> path;

		public BinaryNode(T element, String city, ArrayList<String> path, double time) {
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;
			this.city = city;
			this.path = path;
			this.time = time;
		}

		public BinaryNode getMin() {
			if (this.getLeftChild() != null) {
				return this.getLeftChild().getMin();
			}
			return this;
		}

		public BinaryNode insert(AVLTree<T>.BinaryNode a, AVLTree<T>.AVLInsertBox box) {
			int temp = this.element.compareTo(a.getElement());

			if (temp < 0) {

				if (this.rightChild == null) {
					box.setTrue();
					this.setRightChild(a);
					if (!isPerfect())
						return reform(box);

					return this;

				}
				this.setRightChild(this.rightChild.insert(a, box));
				if (!isPerfect())
					return reform(box);

				return this;

			}

			if (temp > 0) {
				if (this.leftChild == null) {
					box.setTrue();
					this.setLeftChild(a);
					if (!isPerfect())
						return reform(box);

					return this;

				}
				this.setLeftChild(this.leftChild.insert(a, box));
				if (!isPerfect())
					return reform(box);

				return this;

			}

			return this;
		}

		/**
		 * 
		 * Check the balance of the Node. If the node is balance, return the
		 * node, without rotation. If the checkValue>0, do the rightnotation,
		 * and return the node. If the checkVakue<0, do the leftnotation, and
		 * return the node.
		 *
		 * @param box
		 * @return
		 */
		private BinaryNode reform(AVLTree<T>.AVLInsertBox box) {

			int checkValue = -1;
			if (this.hasBothChild()) {
				checkValue = this.getLeftChild().getHeight() - this.getRightChild().getHeight();
				if (Math.abs(checkValue) < 2)
					return this;
			} else {
				if (this.getHeight() < 2)
					return this;
				if (this.getLeftChild() != null)
					checkValue = 1;
			}
			// Right Rotation
			if (checkValue > 0) {
				if (this.leftChild.leftChild == null) {// do the double rotation
														// only if
														// this.leftChild.leftChild=null
					box.addSingleRotation();
					BinaryNode temp0 = this.getLeftChild();
					this.setLeftChild(temp0.getRightChild());
					this.leftChild.setLeftChild(temp0);
					temp0.setRightChild(null);
				}
				// Single Right Rotation
				box.addSingleRotation();
				BinaryNode temp1 = this.getLeftChild();
				this.setLeftChild(temp1.getRightChild());
				temp1.setRightChild(this);
				return temp1;
			}
			// Left Rotation
			box.addSingleRotation();
			if (this.rightChild.rightChild == null) {// do the double rotation
														// only if
														// this.rightChild.rightChild=null
				box.addSingleRotation();
				BinaryNode temp0 = this.getRightChild();
				this.setRightChild(temp0.getLeftChild());
				this.rightChild.setRightChild(temp0);
				temp0.setLeftChild(null);
			}
			// Single Left Rotation
			BinaryNode temp = this.getRightChild();
			this.setRightChild(temp.getLeftChild());
			temp.setLeftChild(this);
			return temp;
		}

		public void setLeftChild(BinaryNode leftChild) {
			this.leftChild = leftChild;
		}

		public void setRightChild(BinaryNode rightChild) {
			this.rightChild = rightChild;
		}

		public BinaryNode getLeftChild() {
			return leftChild;
		}

		public BinaryNode getRightChild() {
			return rightChild;
		}

		public T getElement() {
			return element;
		}

		public int getHeight() {
			int countL = -1;
			int countR = -1;

			if (this.leftChild != null)
				countL = this.leftChild.getHeight();
			if (this.rightChild != null)
				countR = this.rightChild.getHeight();
			if (countR > countL)
				return countR + 1;
			return countL + 1;
		}

		public int getSize() {
			int countL = 0;
			int countR = 0;

			if (this.leftChild != null) {
				countL++;
				countL = this.leftChild.getSize();
			}
			if (this.rightChild != null) {
				countR++;
				countR = this.rightChild.getSize();
			}

			return countL + 1 + countR;
		}

		public boolean hasNoChild() {
			if (this.getRightChild() == null && this.getLeftChild() == null) {
				return true;
			}
			return false;
		}

		public boolean hasBothChild() {
			if (this.getRightChild() != null && this.getLeftChild() != null) {
				return true;
			}
			return false;
		}

		public ArrayList<T> toArrayList(ArrayList a) {
			if (this.leftChild != null) {
				a = this.leftChild.toArrayList(a);
			}
			a.add(element);

			if (this.rightChild != null) {
				a = this.rightChild.toArrayList(a);
			}
			return a;
		}

		// ----------------------------------------------------------------------------------
		//
		// Remove funtion inside BINARYNODE
		//
		// ------------------------------------------------------------------------------------
		public BinaryNode remove(MyBoolean checkBox, AVLTree<T>.AVLInsertBox rotationBox) {

			T checkRoot = this.getElement();
			int temp = this.element.compareTo(checkBox.getRemoveElement());

			if (temp < 0) {
				if (this.rightChild == null)
					return this;
				this.rightChild = this.rightChild.remove(checkBox, rotationBox);
				if (!isPerfect())
					return reform(rotationBox);

				return this;
			} else if (temp > 0) {
				if (this.leftChild == null)
					return this;
				this.leftChild = this.leftChild.remove(checkBox, rotationBox);
				if (!isPerfect())
					return reform(rotationBox);

				return this;

			} else if (temp == 0) {
				checkBox.setTrue();
				if (this.hasBothChild()) {
					this.setElement(this.leftChild.getLargest());
					MyBoolean newBoxRemove = new MyBoolean(this.element);
					this.setLeftChild(this.leftChild.remove(newBoxRemove, rotationBox));
					if (!isPerfect())
						return reform(rotationBox);

					return this;
				} else if (this.getLeftChild() != null) {
					return this.leftChild;
				} else if (this.getRightChild() != null) {
					return this.rightChild;

				}
				return null;
			}
			return null;
		}

		private boolean isPerfect() {
			int check;
			if (this.hasBothChild()) {
				check = Math.abs(this.leftChild.getHeight() - this.getRightChild().getHeight());
			} else {
				check = this.getHeight();
			}
			if (check > 1) {
				return false;
			}
			return true;
		}

		private BinaryNode getLargest() {
			if (this.getRightChild() != null) {
				return this.rightChild.getLargest();
			}
			return this;

		}

		private void setElement(BinaryNode node) {
			this.city = node.city;
			this.time = node.time;
			this.path = node.path;
			this.element = node.getElement();
		}

	}

	public class MyBoolean {
		private BinaryNode node;
		private Boolean result;
		private T removeElement;

		public MyBoolean(T num) {
			result = false;
			removeElement = num;
		}

		public T getRemoveElement() {
			return this.removeElement;
		}

		public void setTrue() {
			this.result = true;
		}

		public Boolean isRemoveable() {
			return this.result;
		}

	}

	public class PreOrderLazyIterator implements Iterator {
		private Stack<BinaryNode> s;
		private AVLTree tree;
		private BinaryNode garbuge = null;
		private int sizeCheck;

		public PreOrderLazyIterator(AVLTree bN) {
			s = new Stack<BinaryNode>();
			tree = bN;
			if (tree.root != null) {
				s.push(tree.root);
				sizeCheck = tree.size();
			}
		}

		@Override
		public boolean hasNext() {
			return !s.isEmpty();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();

			BinaryNode returnNode = s.pop();
			garbuge = returnNode;

			if (returnNode.rightChild != null) {
				s.push(returnNode.rightChild);
			}
			if (returnNode.leftChild != null) {
				s.push(returnNode.leftChild);
			}
			return garbuge.getElement();
		}

		public void remove() {
			if (this.sizeCheck != this.tree.size())
				throw new ConcurrentModificationException();
			if (s.isEmpty() || this.garbuge == null)
				throw new IllegalStateException();
			this.tree.remove(garbuge.getElement());
			if (garbuge.hasBothChild()) {
				s.push(garbuge);
			}
			this.sizeCheck = this.tree.size();
			this.garbuge = null;
		}

	}

	public int getRotationCount() {

		return this.rotationCount;
	}

	public class AVLInsertBox {
		private boolean isInsert;
		private int rotationCount;

		public AVLInsertBox() {
			this.isInsert = false;
			this.rotationCount = 0;
		}

		public void addSingleRotation() {
			this.rotationCount++;
		}

		public int getRotationCount() {
			return this.rotationCount;
		}

		public void setTrue() {
			this.isInsert = true;
		}

		public boolean getBoolean() {
			return this.isInsert;
		}
	}
}

//
