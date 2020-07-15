package week4

trait Function[ParameterType, ReturnType] {
  def apply(x: ParameterType): ReturnType
}
