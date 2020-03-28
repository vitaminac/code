package week1

object session {
  def abs(x: Double): Double = if (x < 0) -x else x

  def sqrt(x: Double) = {
    def isGoodEnough(guess: Double): Boolean = abs(guess * guess - x) / x < 0.001

    def improve(guess: Double): Double = (guess + x / guess) / 2

    def sqrtItr(guess: Double): Double = if (isGoodEnough(guess)) guess else sqrtItr(improve(guess))

    sqrtItr(1.0)
  }

  def factorial(n: Int): Int = {
    def loop(ac: Int, n: Int):Int = if (n == 1) ac else loop(ac * n, n -1)
    loop(1,n)
  }
}
