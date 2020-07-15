package week3

trait List[T] {
  def isEmpty: Boolean

  def head: T

  def tail: List[T]

  def nth(n: Int): T
}

class NIL[T] extends List[T] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException("NIL.head")

  override def tail: Nothing = throw new NoSuchElementException("NIL.tail")

  override def nth(n: Int): T = throw new IndexOutOfBoundsException(n)
}

class Cons[T](val head: T, _tail: List[T] = new NIL[T]) extends List[T] {
  override def isEmpty: Boolean = false

  override def tail: List[T] = _tail

  override def nth(n: Int): T =
    if (n == 0) head
    else _tail.nth(n - 1)
}