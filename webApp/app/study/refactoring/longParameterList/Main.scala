package study.refactoring.longParameterList


class Main {
  def main(args: Array[String]): Unit = {
    val plan = HeatingPlan(10, 20)
    val daysTemp = DaysRoomTemp(2)
    val room = Room(plan)

    // dead code
    // val bottom = room.heatingPlan.bottom
    // val top = room.heatingPlan.top

    if(!daysTemp.withRange(room.heatingPlan)) {
      println("室温が設定値を超えました。")
    }
  }
}

case class Room(heatingPlan: HeatingPlan)

case class HeatingPlan(bottom: Int, top: Int)


case class DaysRoomTemp(temperature: Int) {
  def withRange(heatingPlan: HeatingPlan): Boolean = {
    this.temperature < heatingPlan.bottom &&  this.temperature > heatingPlan.top
  }
}
