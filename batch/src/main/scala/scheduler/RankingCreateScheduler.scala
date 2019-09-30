package scheduler

import actor.RankinCreateMasterActor
import akka.actor.{ActorRef, ActorSystem}
import scala.concurrent.duration._


// ここでランキングバッチを定期実行する処理を行う
// Appを使うと、def mainを定義したことになる
object RankingCreateScheduler extends App {

  // dispacherを明示的にimportしないとactorを実行できない
  import actorSystem.dispatcher

  private val actorSystem = ActorSystem("RankinCreateActorSystem")
  private val rankinCreateMasterActor: ActorRef = actorSystem.actorOf(RankinCreateMasterActor.props, "RankinCreateMasterActor")

  actorSystem.scheduler.schedule(0 seconds, 1 seconds, rankinCreateMasterActor, "s")
}
