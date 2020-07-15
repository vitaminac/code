package week4

import week3.Cons, week3.NIL, week3.List;

object IntListFunc {
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, new NIL)())
}