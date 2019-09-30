package infra.mysql.repository

import domain.model.ShopClickLog
import domain.repository.{RankingRepository, UsesRankingRepository}

case class RankingRepositoryImpl() extends RankingRepository {
  override def fetchClockLogs: Seq[ShopClickLog] = {
    // doobieでmysqlからのデータを取得する
    // 一旦モックデータを返す
    Seq(ShopClickLog("sauth tokyo annex", "2019-10-23"), ShopClickLog("sauth tokyo annex", "2019-10-23"), ShopClickLog("sauth tokyo annex", "2019-10-23"))
  }
}

// このmixinは、RankingRepositoryの実装を提供するただ１つのフィールドを持つモジュール
// この実装を外部に提供するモジュール
trait MixinRankingRepositoryImpl extends UsesRankingRepository{
  override val rankingRepository: RankingRepository = RankingRepositoryImpl()
}