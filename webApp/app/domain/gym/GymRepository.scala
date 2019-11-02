package domain.gym

import useCase.search.SearchCondition
import com.google.inject.ImplementedBy
import domain.search.SearchResult
import infrastructure.repositories.gym.GymRespositoryImpl
import zio.ZIO


// インターフェース
@ImplementedBy(classOf[GymRespositoryImpl])
trait GymRepository {

  def getGyms(searchCondition: SearchCondition): ZIO[Any, Throwable, SearchResult]
  def test: ZIO[Any, Throwable, String]
}
