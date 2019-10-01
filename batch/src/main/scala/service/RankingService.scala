package service

// import domain.repository.UsesRankingRepository
import domain.model.{Ranking, ShopClickLog}
import domain.repository.UsesRankingRepository
import infra.doobie.repository.MixinRankingRepositoryImpl

trait RankingService extends UsesRankingRepository

object RankingService extends RankingService with MixinRankingRepositoryImpl{
  def calculateRanking = {
    // mysqlからデータを抜く
    val ranking: Seq[Ranking] = rankingRepository.fetchClickLogs
    println(ranking)
    // TODO: redisに詰める
  }
}
