package week3

class NIL[T] extends List[T] {
  override def isEmpty: Boolean = true

  override def head: Nothing = throw new NoSuchElementException("NIL.head")

  override def tail: Nothing = throw new NoSuchElementException("NIL.tail")

  override def nth(n: Int): T = throw new IndexOutOfBoundsException(n)
}
