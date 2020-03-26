package week2

import scala.math.abs

object session {
  def sum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = if (a > b) acc else loop(a + 1, f(a) + acc)

    loop(a, 0)
  }

  def sum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)

    sumF
  }

  def sumCurrying(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sumCurrying(f)(a + 1, b)

  def product(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 1 else f(a) * product(f)(a + 1, b)

  def factorial(n: Int): Int = product(x => x)(1, n)

  def mapReduce(reduce: (Int, Int) => Int, zero: Int)(map: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) zero
    else reduce(map(a), mapReduce(reduce, zero)(map)(a + 1, b))
  }

  def product2: (Int => Int) => (Int, Int) => Int = mapReduce((a, b) => a * b, 1)

  val tolerance = 0.0001

  def isCloseEnough(x: Double, y: Double) = abs((x - y) / x) / x < tolerance

  def fixedPoint(f: Double => Double)(firstGuess: Double) = {
    def iterate(guess: Double): Double = {
      val next = f(guess)
      if (isCloseEnough(guess, next)) next
      else iterate(next)
    }

    iterate(firstGuess)
  }

  fixedPoint(x => 1 + x / 2)(1)

  def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

  def sqrt(x: Double) = fixedPoint(averageDamp(y => x / y))(1)
}
