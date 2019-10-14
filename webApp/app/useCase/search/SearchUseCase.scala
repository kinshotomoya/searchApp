package useCase.search

import domain.gym.GymRepository
import javax.inject.Inject
import zio.ZIO

class SearchUseCase @Inject()(gymRepository: GymRepository) {

  // controllerのfor式の中でこのメソッドを読んでおり、forの最初の型がZIO[]なので、
  // このメソッドもZIO[]で返さないといけない
  def search(searchCondition: SearchCondition): ZIO[Any, Throwable, SearchUseCaseResult] = {
    // 実際にelasticseachを叩くメソッド（repository層）を呼ぶ
    for {
      searchResult <- gymRepository.getGyms(searchCondition)
      searchUseCaseResult <- searchResult.toSearchUseCaseResult
    } yield {
      searchUseCaseResult
    }
  }
}
