package week3

class BST(element: Int, left: IntSet, right: IntSet) extends IntSet {
  override def contains(x: Int): Boolean =
    if (x < element) left contains x
    else if (x > element) right contains x
    else true

  override def include(x: Int): IntSet =
    if (x < element) new BST(element, left include x, right)
    else if (x > element) new BST(element, left, right include x)
    else this

  override def union(other: IntSet): IntSet =
    ((left union right) union other) include element

  override def toString: String = "{" + left + element + right + "}"
}
