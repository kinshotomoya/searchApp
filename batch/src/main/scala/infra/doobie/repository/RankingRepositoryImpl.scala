package infra.doobie.repository

import domain.model.{Ranking, ShopClickLog}
import domain.repository.{RankingRepository, UsesRankingRepository}
import doobie.implicits._
import infra.doobie.DoobieTransactor

case class RankingRepositoryImpl() extends RankingRepository with DoobieTransactor{
  override def fetchClickLogs: Seq[Ranking] = {
    // doobieでmysqlからのデータを取得する
    queryForGettingAllClickLog
      .to[List]
      .transact(xa)
      .unsafeRunSync()
  }

  private[this] def queryForGettingAllClickLog = {
    sql"""
         SELECT shopname, COUNT(shopname)
         FROM shopClickLog
         GROUP BY shopname
      """.query[Ranking]
  }
}

// このmixinは、RankingRepositoryの実装を提供するただ１つのフィールドを持つモジュール
// この実装を外部に提供するモジュール
trait MixinRankingRepositoryImpl extends UsesRankingRepository{
  override val rankingRepository: RankingRepository = RankingRepositoryImpl()
}
