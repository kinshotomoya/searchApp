package domain.repository

import domain.model.ShopClickLog

// 実装部分に依存させる
trait RankingRepository {
  def fetchClockLogs: Seq[ShopClickLog]
}

// service（呼び出し側）に依存させる
trait UsesRankingRepository {
  val rankingRepository: RankingRepository
}
