package week3

trait List[T] {
  def isEmpty: Boolean

  def head: T

  def tail: List[T]

  def nth(n: Int): T
}
