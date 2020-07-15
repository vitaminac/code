package week4

trait Nat {
  def isZero: Boolean;

  def predecessor: Nat;

  def successor: Nat = new Successor(this)

  def +(that: Nat): Nat

  def -(that: Nat): Nat
}

object Zero extends Nat {
  override def isZero: Boolean = True

  override def predecessor: Nat = throw new Error("0.predecessor")

  override def +(that: Nat): Nat = that

  override def -(that: Nat): Nat = if (that.isZero == True) Zero else throw new Error("negative number")
}

class Successor(n: Nat) extends Nat {
  override def isZero: Boolean = False

  override def predecessor: Nat = n

  override def +(that: Nat): Nat = new Successor(predecessor + that)

  override def -(that: Nat): Nat = if (that.isZero == True) this else this.predecessor - that.predecessor
}