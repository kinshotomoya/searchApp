package study.refactoring.longParameterList

class Main2 {

  def main(args: Array[String]): Unit = {
    val house = new House()
     val heatingPlan = HeatingPlan(10, 20)

    // 10 20オブジェクトに格納する
    if(house.exceedSettingHeatingPlan(heatingPlan)) {
      println("過去の室温で設定外になっていると場合がありました。")
    }
  }
}


class House {

  def exceedSettingHeatingPlan(heatingPlan: HeatingPlan): Boolean = {
    getHistoryTempList.exists(temp => {
      heatingPlan.contains(temp)
    })
  }

  private def getHistoryTempList: List[Int] = List(5, 6, 7, 8)
}

case class HeatingPlan(bottom: Int, top: Int) {
  def contains(temp: Int): Boolean = {
    temp < bottom || temp > top
  }

}