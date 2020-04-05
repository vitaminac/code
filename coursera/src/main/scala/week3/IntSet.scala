package week3

trait IntSet {
  def include(x: Int): IntSet

  def contains(x: Int): Boolean

  def union(other: IntSet): IntSet
}
