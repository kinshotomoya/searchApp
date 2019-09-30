package service

// import domain.repository.UsesRankingRepository
import domain.repository.UsesRankingRepository
import infra.mysql.repository.MixinRankingRepositoryImpl

trait RankingService extends UsesRankingRepository

object RankingService extends RankingService with MixinRankingRepositoryImpl{
  def calculateRanking = {
    // mysqlからデータを抜く
    val logs = rankingRepository.fetchClockLogs
    println(logs)
    // TODO: 計算

    // TODO: redisに詰める

  }
}
