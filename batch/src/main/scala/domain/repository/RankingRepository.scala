package domain.repository

import domain.model.{Ranking, ShopClickLog}

// 実装部分に依存させる
trait RankingRepository {
  def calculateClickLogs: Seq[Ranking]
}

// service（呼び出し側）に依存させる
trait UsesRankingRepository {
  val rankingRepository: RankingRepository
}
