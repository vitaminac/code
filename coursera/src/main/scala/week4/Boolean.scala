package week4

trait Boolean {
  def &&(x: => Boolean): Boolean

  def ||(x: => Boolean): Boolean

  def unary_! : Boolean
}

object True extends Boolean {
  override def &&(x: => Boolean): Boolean = x

  override def ||(x: => Boolean): Boolean = True

  override def unary_! : Boolean = False
}

object False extends Boolean {
  override def &&(x: => Boolean): Boolean = False

  override def ||(x: => Boolean): Boolean = x

  override def unary_! : Boolean = True
}