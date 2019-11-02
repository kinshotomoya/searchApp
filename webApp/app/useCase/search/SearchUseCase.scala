package useCase.search

import domain.gym.GymRepository
import domain.search.SearchResult
import javax.inject.{Inject, Singleton}
import zio.ZIO

@Singleton
class SearchUseCase @Inject()(gymRepository: GymRepository
) {
  val ~ : Tuple2.type = Tuple2
  // controllerのfor式の中でこのメソッドを読んでおり、forの最初の型がZIO[]なので、
  // このメソッドもZIO[]で返さないといけない
  def search(searchCondition: SearchCondition): ZIO[Any, Throwable, SearchUseCaseResult] = {
    // 実際にelasticseachを叩くメソッド（repository層）を呼ぶ
    for {
      // (SearchResult, String)
      // zipParを非同期で、処理をし、非同期で処理して帰ってきた値(ZIO)を１つにまとめることができる。（tupleに）
      // ３つ以上繋げるとと、((SearchResult, String), String)みたいになるので取り出す時に注意が必要
      tupleSearchResult <- gymRepository.getGyms(searchCondition).zipPar(gymRepository.test)
    } yield {
      val searchResult = tupleSearchResult._1
      SearchUseCaseResult(searchResult)
    }
  }
}
