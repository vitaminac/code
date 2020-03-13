package week2

class Rational(x: Int, y: Int) {
  require(y > 0, "denominator must be positive")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  val g = gcd(x, y)

  def numerator: Int = x / g

  def denominator: Int = y / g

  def + (that: Rational) = new Rational(
    this.numerator * that.denominator + that.numerator * this.denominator,
    this.denominator * that.denominator
  )

  def unary_- : Rational = new Rational(-numerator, denominator)

  def - (that: Rational): Rational = this + -that

  def < (that: Rational) = this.numerator * that.denominator < that.numerator * this.denominator

  def max(that: Rational) = if (this < that) that else this

  override def toString: String = this.numerator + "/" + this.denominator
}
