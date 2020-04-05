package week3

class Cons[T](val head: T, _tail: List[T] = new NIL[T]) extends List[T] {
  override def isEmpty: Boolean = false

  override def tail: List[T] = _tail

  override def nth(n: Int): T =
    if (n == 0) head
    else _tail.nth(n - 1)
}
