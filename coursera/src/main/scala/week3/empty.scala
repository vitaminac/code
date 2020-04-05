package week3

object empty extends IntSet {
  override def contains(x: Int): Boolean = false;

  override def include(x: Int): IntSet = new BST(x, empty, empty)

  override def union(other: IntSet): IntSet = other

  override def toString: String = "null"
}
