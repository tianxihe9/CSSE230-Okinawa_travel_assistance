import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TDBRTree<T extends Comparable<? super T>> implements Iterable<TDBRTree.BinaryNode> {

	public enum Color {
		RED, BLACK
	};

	public BinaryNode root;
	private int rotationCount;

	public TDBRTree() {
		this.root = null;
		this.rotationCount = 0;
	}

	public TDBRTree(BinaryNode n) {
		this.root = n;
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

		return this.root.getHeight();
	}

	public int size() {
		if (this.isEmpty()) {
			return 0;
		}
		return this.root.getSize();
	}

	@Override
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
		return this.root.toArrayList(aList);
	}

	public Object[] toArray() {
		return toArrayList().toArray();
	}

	public Boolean insert(T i, String city, ArrayList<String> path) {
		if (i == null)
			throw new IllegalArgumentException();
		BinaryNode newNode = new BinaryNode(i, city, path);
		if (this.root == null) {
			this.root = newNode;
			this.root.setBlack();
			return true;
		}
		RedBlackInsertBox box = new RedBlackInsertBox();
		this.root = this.root.insert(newNode, box);
		this.root.setBlack();
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
		RedBlackInsertBox rotationBox = new RedBlackInsertBox();
		this.root = root.removeStep1(checkBox, rotationBox);
		if (this.root == null) {
			this.rotationCount += rotationBox.getRotationCount();
			return checkBox.isRemoveable();
		}
		this.root.setBlack();// Step 4
		this.rotationCount += rotationBox.getRotationCount();
		return checkBox.isRemoveable();
	}

	@Override
	public Iterator<TDBRTree.BinaryNode> iterator() {
		return new PreOrderLazyIterator(this);
	}

	//
	//
	//
	// RedBlackTree inner class BinaryBode;
	//
	//
	//
	//

	public class BinaryNode {
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		private Color color;
		protected String city;
		protected ArrayList<String> path;

		public BinaryNode(T element, String city, ArrayList<String> path) {
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;
			this.color = TDBRTree.Color.RED;
			this.city = city;
			this.path = path;
		}

		public BinaryNode getMin() {
			if (this.getLeftChild() != null) {
				return this.getLeftChild().getMin();
			}
			return this;

		}

		public BinaryNode removeStep2(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// System.out.println("Step2");
			BinaryNode child = this.nextChild(checkBox);
			if (child == null) {
				return this;
			}
			if (child.hasBothBlackChild()) {
				return this.removeStep2A(checkBox, rotationBox);
			}
			BinaryNode a = null;
			a = child.removeStep2B(checkBox, rotationBox);
			if (child.getElement() == this.getLeftChild().getElement()) {
				this.setLeftChild(a);
			} else {
				this.setRightChild(a);
			}
			return this;

		}

		private BinaryNode removeStep2A(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// System.out.println("Step2A");
			int label = 0;// indicate left or right child
			BinaryNode child = this.nextChild(checkBox);
			BinaryNode sibling = null;
			BinaryNode parent = this;

			if (child.getElement() == this.leftChild.getElement()) {
				// System.out.println("L");
				label = 1;
				sibling = this.rightChild;
			}
			if (child.getElement() == this.rightChild.getElement()) {
				// System.out.println("R");
				label = 2;
				sibling = this.leftChild;
			}

			if (sibling.hasBothBlackChild()) {// Step2A1
				// System.out.println("Step2A1");
				this.setBlack();
				sibling.setRed();
				child.setRed();
			}

			if (label == 1 && sibling.getRightChild() != null && sibling.getRightChild().color == TDBRTree.Color.RED
					|| label == 2 && sibling.getLeftChild() != null
							&& sibling.getLeftChild().color == TDBRTree.Color.RED) {// Step2A3
				// System.out.println("Step2A3");
				if (label == 1) {
					rotationBox.addSingleRotation();
					parent = this.remSingleLeftrotation();
				} else {
					rotationBox.addSingleRotation();
					parent = this.remSingleRightrotation();
				}
			}

			if (label == 1 && sibling.getLeftChild() != null && sibling.getLeftChild().color == TDBRTree.Color.RED
					|| label == 2 && sibling.getRightChild() != null
							&& sibling.getRightChild().color == TDBRTree.Color.RED) {// Step2A2
				// System.out.println("Step2A2");

				if (label == 1) {
					rotationBox.addSingleRotation();
					rotationBox.addSingleRotation();
					parent = this.remDoubleLeftrotation();
				} else {
					rotationBox.addSingleRotation();
					rotationBox.addSingleRotation();
					parent = this.remDoubleRightrotation();
				}

			}

			if (child.getElement().compareTo(checkBox.getRemoveElement()) == 0) {
				if (label == 1) {
					this.setLeftChild(child.removeStep3(checkBox, rotationBox));
				} else if (label == 2) {
					this.setRightChild(child.removeStep3(checkBox, rotationBox));
				}

			} else {
				if (label == 1) {
					this.setLeftChild(child.removeStep2(checkBox, rotationBox));
				} else if (label == 2) {
					this.setRightChild(child.removeStep2(checkBox, rotationBox));
				}
			}
			return parent;

		}

		private TDBRTree<T>.BinaryNode remDoubleRightrotation() {
			BinaryNode temp = this.getLeftChild();
			BinaryNode newParent = temp.getRightChild();
			this.setBlack();
			this.rightChild.setRed();
			temp.setRightChild(newParent.getLeftChild());
			newParent.setLeftChild(temp);
			this.setLeftChild(newParent.getRightChild());
			newParent.setRightChild(this);
			return newParent;
		}

		private TDBRTree<T>.BinaryNode remDoubleLeftrotation() {
			BinaryNode temp = this.getRightChild();
			BinaryNode newParent = temp.getLeftChild();
			this.setBlack();
			this.leftChild.setRed();
			temp.setLeftChild(newParent.getRightChild());
			newParent.setRightChild(temp);
			this.setRightChild(newParent.getLeftChild());
			newParent.setLeftChild(this);
			return newParent;
		}

		private BinaryNode remSingleRightrotation() {
			// System.out.println("REMOVE single Right Rtation");
			BinaryNode temp = this.getLeftChild();
			this.setLeftChild(temp.getRightChild());
			temp.setRightChild(this);
			temp.setRed();
			this.setBlack();
			temp.leftChild.setBlack();
			this.rightChild.setRed();
			return temp;

		}

		private BinaryNode remSingleLeftrotation() {
			// System.out.println("REMOVE single Left Rtation");
			BinaryNode temp = this.getRightChild();
			this.setRightChild(temp.getLeftChild());
			temp.setLeftChild(this);
			temp.setRed();
			this.setBlack();
			temp.rightChild.setBlack();
			this.leftChild.setRed();
			return temp;

		}

		public BinaryNode removeStep3(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// System.out.println("Step3");
			checkBox.setTrue();
			if (this.hasBothChild()) {// X has two children
				// System.out.println(" Step 3: 2 child cases");
				BinaryNode i = this.leftChild;
				switch (this.color) {
				case RED:
					// System.out.println("RED");
					checkBox.setRemoveElement(i.getElement());
					this.removeStep2(checkBox, rotationBox);
					this.setElement(i);
					return this;
				case BLACK:
					// System.out.println("BLACK");
					BinaryNode newParent = null;
					checkBox.setRemoveElement(i.getElement());
					newParent = this.removeStep2B(checkBox, rotationBox);
					this.setElement(i);
					// System.out.println(this.getElement());
					return newParent;
				}

			} else if (this.hasNoChild()) {// X is a leaf.
				// System.out.println(" Step 3: 0 child cases");
				switch (this.color) {
				case RED:
					// System.out.println("RED");
					return null;
				case BLACK:
					// System.out.println("BLACK");
					throw new IllegalStateException();
				}

			} else if (this.getLeftChild() != null || this.getRightChild() != null) {// X
																						// has
																						// one
																						// child
				// System.out.println(" Step 3: 1 child cases");
				if (null == this.leftChild) {
					this.setElement(this.getRightChild());
					this.setRightChild(null);
				} else {
					this.setElement(this.getLeftChild());
					this.setLeftChild(null);
				}
				return this;
			}

			throw new IllegalStateException();
		}

		private TDBRTree<T>.BinaryNode removeStep2B(TDBRTree<T>.MyBoolean checkBox,
				TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// System.out.println("Step2B");
			if (this.getElement().compareTo(checkBox.getRemoveElement()) == 0) {
				// System.out.println(" Remove Root");
				return this.removeStep3(checkBox, rotationBox);
			}
			BinaryNode newNode = this.nextChild(checkBox);
			if (newNode == null) {
				// System.out.println("??");
				return this;
			}
			if (newNode.color == TDBRTree.Color.RED) {
				if (newNode == this.leftChild) {
					// System.out.println("L");
					this.setLeftChild(newNode.removeStep2B1(checkBox, rotationBox));
				}
				if (newNode == this.rightChild) {
					// System.out.println("R");
					this.setRightChild(newNode.removeStep2B1(checkBox, rotationBox));

				}
				return this;
			} else if (newNode.color == TDBRTree.Color.BLACK) {
				int ind = 0;
				BinaryNode result = null;
				if (newNode == this.leftChild) {
					// System.out.println("L");
					ind = 1;
					result = this.removeStep2B2(checkBox, rotationBox, ind);
					this.removeStep2(checkBox, rotationBox);

				}
				if (newNode == this.rightChild) {
					// System.out.println("R");
					ind = 2;
					result = this.removeStep2B2(checkBox, rotationBox, ind);
					// System.out.println(result.getElement());
					this.removeStep2(checkBox, rotationBox);
					// System.out.println(result.getElement());
				}
				return result;
			}
			// System.out.println("RemoveStep2B WARNNING WARNNING");
			return null;
		}

		private BinaryNode removeStep2B2(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox,
				int ind) {
			// System.out.println("Step2B2");
			rotationBox.addSingleRotation();
			BinaryNode temp = null;
			if (ind == 1) {
				temp = this.getRightChild();
				temp.setBlack();
				this.setRightChild(temp.getLeftChild());
				this.setRed();
				temp.setLeftChild(this);
			} else if (ind == 2) {
				temp = this.getLeftChild();
				temp.setBlack();
				this.setLeftChild(temp.getRightChild());
				this.setRed();
				temp.setRightChild(this);
			} else if (ind == 0) {
				throw new IllegalStateException();
			}
			return temp;

		}

		private BinaryNode removeStep2B1(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// System.out.println("Step2B1");
			BinaryNode child = this.nextChild(checkBox);
			if (this.getElement().compareTo(checkBox.getRemoveElement()) == 0) {
				// System.out.println("???");
				return this.removeStep3(checkBox, rotationBox);
			}
			if (child == null) {// the node we need to remove is not here.
				return this;
			}
			return this.removeStep2(checkBox, rotationBox);

		}

		private BinaryNode removeStep1(TDBRTree<T>.MyBoolean checkBox, TDBRTree<T>.RedBlackInsertBox rotationBox) {
			// step 1 case 1
			// System.out.println("______________________");
			// System.out.println("Step1");
			if (this.hasBothBlackChild()) {
				this.setRed();
				if (this.getElement().compareTo(checkBox.getRemoveElement()) == 0) {
					return this.removeStep3(checkBox, rotationBox);
				}
				return this.removeStep2(checkBox, rotationBox);
			}
			// step 1 case 2
			return this.removeStep2B(checkBox, rotationBox);

		}

		private BinaryNode nextChild(MyBoolean checkBox) {
			int temp = this.element.compareTo(checkBox.getRemoveElement());
			BinaryNode nextChild = null;
			if (temp < 0) {
				nextChild = this.rightChild;
			} else if (temp > 0) {
				nextChild = this.leftChild;
			} else if (temp == 0) {
				// System.out.println("NEXTCHILD METHOD WARNNING WARNNING");
				nextChild = this;
			}
			return nextChild;
		}

		private boolean hasBothBlackChild() {
			if (this == null) {
				return false;
			}
			if (this.hasBothChild()) {
				if (this.getLeftChild().color == TDBRTree.Color.BLACK
						&& this.getRightChild().color == TDBRTree.Color.BLACK) {
					return true;
				}
				return false;
			}
			if (this.hasNoChild()) {
				return true;
			}
			if (this.getLeftChild() != null && this.getLeftChild().color == TDBRTree.Color.BLACK) {
				return true;
			}
			if (this.getRightChild() != null && this.getRightChild().color == TDBRTree.Color.BLACK) {
				return true;
			}
			return false;
		}

		public void setBlack() {
			this.color = TDBRTree.Color.BLACK;

		}

		private void setRed() {
			this.color = TDBRTree.Color.RED;

		}

		public BinaryNode insert(TDBRTree<T>.BinaryNode a, TDBRTree<T>.RedBlackInsertBox box) {
			int temp = this.element.compareTo(a.getElement());
			if (this.hasBothRedChild()) {// check the node has two red
				// children or not
				this.acceptRed();
			}

			if (temp < 0) {// insert to right
				if (this.rightChild == null) {
					box.setTrue();
					this.setRightChild(a);
					box.addKillPoint();
					box.setSide('R');
					if (this.colorCheck(box)) {
						box.addKillPoint();
						return this.leftRotationReform(box);
					}
					return this;

				}

				this.setRightChild(this.rightChild.insert(a, box));
				box.setSide('R');
				if (this.colorCheck(box)) {
					return this.leftRotationReform(box);
				}
				return this;

			}

			if (temp > 0) {// insert to left
				if (this.leftChild == null) {
					box.setTrue();
					this.setLeftChild(a);
					box.addKillPoint();
					box.setSide('L');
					if (this.colorCheck(box)) {
						box.addKillPoint();
						return this.leftRotationReform(box);
					}
					return this;

				}
				this.setLeftChild(this.leftChild.insert(a, box));
				box.setSide('L');
				if (this.colorCheck(box)) {
					return this.rightRotationReform(box);
				}

				return this;

			}

			return this;
		}

		private TDBRTree<T>.BinaryNode rightRotationReform(TDBRTree<T>.RedBlackInsertBox box) {
			// Right Rotation
			box.addSingleRotation();
			if (box.rotationKind == 2) {// do
				// the
				// double
				// rotation
				box.addSingleRotation();
				BinaryNode temp0 = this.getLeftChild();
				this.setLeftChild(temp0.getRightChild());
				temp0.setRightChild(this.getLeftChild().getLeftChild());
				this.leftChild.setLeftChild(temp0);
			} // Single Right Rotation
			return insSingleRightrotation();
		}

		private TDBRTree<T>.BinaryNode leftRotationReform(TDBRTree<T>.RedBlackInsertBox box) {
			box.addSingleRotation();

			if (box.rotationKind == 2) {// do
										// the
										// double
										// rotation

				box.addSingleRotation();
				BinaryNode temp0 = this.getRightChild();
				this.setRightChild(temp0.getLeftChild());
				temp0.setLeftChild(this.getRightChild().getRightChild());
				this.rightChild.setRightChild(temp0);
			} // Single Left Rotation
			return insSingleLeftrotation();
		}

		//
		// return true only in the situation that the node is color black and
		// has two red go together.
		//

		private boolean colorCheck(TDBRTree<T>.RedBlackInsertBox box) {
			if (this.color == TDBRTree.Color.RED) {
				box.addKillPoint();
			} else {// only the node has the black color

				if (box.killPointCheck()) {// only we have 2 red together
					if (box.sideCheck[0] != box.sideCheck[1]) {
						box.rotationKind = 2;
					} else {
						box.rotationKind = 1;
					}
					box.resetKillPoint();
					return true;
				}
				box.resetKillPoint();
			}
			return false;
		}

		private void acceptRed() {
			this.setRed();
			this.leftChild.setBlack();
			this.rightChild.setBlack();

		}

		private boolean hasBothRedChild() {
			if (this.hasBothChild()) {
				if (this.leftChild.color == TDBRTree.Color.RED && this.rightChild.color == TDBRTree.Color.RED) {
					return true;
				}
			}
			return false;
		}

		private BinaryNode insSingleRightrotation() {
			BinaryNode temp1 = this.getLeftChild();
			temp1.setBlack();
			this.setRed();
			this.setLeftChild(temp1.getRightChild());
			temp1.setRightChild(this);
			return temp1;
		}

		private BinaryNode insSingleLeftrotation() {
			BinaryNode temp = this.getRightChild();
			temp.setBlack();
			this.setRed();
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
			return this.leftChild;
		}

		public BinaryNode getRightChild() {
			return this.rightChild;
		}

		public T getElement() {
			return this.element;
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

		private T getLargest() {
			if (this.getRightChild() != null) {
				return this.rightChild.getLargest();
			}
			return this.getElement();

		}

		private void setElement(BinaryNode node) {
			this.city = node.city;
			this.path = node.path;
			this.element = node.getElement();
		}

		public Object getColor() {
			return this.color;
		}

	}

	//
	//
	//
	// RedBlackTree inner class Myboolean;
	//
	//
	//

	public class MyBoolean {
		private Boolean result;
		private T removeElement;

		public MyBoolean(T num) {
			this.result = false;
			this.removeElement = num;
		}

		public void setRemoveElement(T i) {
			this.removeElement = i;
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

	//
	//
	//
	// RedBlackTree inner class PreOrderLazyIterator
	//
	//
	//
	public class PreOrderLazyIterator implements Iterator {
		private Stack<BinaryNode> s;
		private TDBRTree tree;
		private BinaryNode garbuge = null;
		private int sizeCheck;

		public PreOrderLazyIterator(TDBRTree bN) {
			this.s = new Stack<BinaryNode>();
			this.tree = bN;
			if (this.tree.root != null) {
				s.push(this.tree.root);
				sizeCheck = tree.size();
			}
		}

		@Override
		public boolean hasNext() {
			return !s.isEmpty();
		}

		@Override
		public BinaryNode next() {
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
			return garbuge;
		}

	}

	public int getRotationCount() {
		return this.rotationCount;
	}

	//
	//
	//
	// RedBlackTree inner class RedBlackInsertBox;
	//
	//
	//
	//

	public class RedBlackInsertBox {
		private boolean isInsert;
		private int rotationCount;
		private int killPoint;
		protected int rotationKind;
		protected char[] sideCheck;

		public RedBlackInsertBox() {
			this.isInsert = false;
			this.rotationCount = 0;
			this.killPoint = 0;
			this.rotationKind = -1;
			this.sideCheck = new char[2];
		}

		public void setSide(char a) {
			if (this.sideCheck.length == 0) {
				this.sideCheck[0] = a;
				this.sideCheck[1] = a;
			}
			this.sideCheck[0] = this.sideCheck[1];
			this.sideCheck[1] = a;
		}

		public void addKillPoint() {
			this.killPoint++;
		}

		public void resetKillPoint() {
			this.killPoint = 0;
		}

		public boolean killPointCheck() {
			if (this.killPoint == 2) {
				return true;
			} else if (this.killPoint < 2) {
				return false;
			}
			throw new IllegalStateException();
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
