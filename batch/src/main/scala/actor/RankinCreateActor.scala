package actor

import akka.actor.Actor.Receive
import akka.actor.{Actor, Props}
import service.RankingService

// mysqlからclicklogを取得して、ランキングを計算。redisに保存する処理を行う
object RankinCreateMasterActor {
  def props: Props = akka.actor.Props(new RankinCreateMasterActor)
}

class RankinCreateMasterActor extends Actor{

  // schedulerからmessageを取得
  override def receive: Receive = {
    // RankingService -> interface -> mysqlRepository -> mysql
    case _ => {
      RankingService.calculateRanking
    }
  }
}
