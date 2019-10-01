package infra.doobie.repository

import domain.model.{Ranking, ShopClickLog}
import domain.repository.{RankingRepository, UsesRankingRepository}
import doobie.implicits._
import infra.doobie.DoobieTransactor
import infra.redis.RedisConfig

case class RankingRepositoryImpl() extends RankingRepository with DoobieTransactor with RedisConfig {
  override def calculateClickLogs: Seq[Ranking] = {
    // doobieでmysqlからのデータを取得する
    val ranking = queryForGettingAllClickLog
      .to[List]
      .transact(xa)
      .unsafeRunSync()
    setRankingDataToCache(ranking)
    ranking
  }

  private[this] def queryForGettingAllClickLog = {
    sql"""
         SELECT shopname, COUNT(shopname)
         FROM shopClickLog
         GROUP BY shopname
      """.query[Ranking]
  }

  // TODO: redisの処理はここに書いてもいいのか？責務的に
  private [this] def setRankingDataToCache(ranking: Seq[Ranking]) = {
    redisClient.set("ranking", ranking)
  }
}

// このmixinは、RankingRepositoryの実装を提供するただ１つのフィールドを持つモジュール
// この実装を外部に提供するモジュール
trait MixinRankingRepositoryImpl extends UsesRankingRepository{
  override val rankingRepository: RankingRepository = RankingRepositoryImpl()
}
