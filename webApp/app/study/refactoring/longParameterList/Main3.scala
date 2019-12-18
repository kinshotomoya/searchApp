package study.refactoring.longParameterList


class Main3 {

  def main(args: Array[String]): Unit = {
    val order = Order()
    rushDeliverDate(order)
  }

  def rushDeliverDate(order: Order): Unit = {

  }
}

case class Order()


class Main4 {

  def main(args: Array[String]): Unit = {
    val order = Order()
    deliverDate(order, false)
  }
  def deliverDate(order: Any, bool: Boolean): Unit = ???

}