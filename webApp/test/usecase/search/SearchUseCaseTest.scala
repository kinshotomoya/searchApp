package usecase.search

import domain.gym.{Gym, GymRepository}
import domain.search.{MetaData, SearchResult}
import org.scalatest.FunSpec
import org.scalatest.mockito.MockitoSugar
import useCase.search.{SearchCondition, SearchUseCase, SearchUseCaseResult}
import org.mockito.Mockito._
import zio.{DefaultRuntime, Task, ZIO}

class SearchUseCaseTest extends FunSpec with MockitoSugar with DefaultRuntime{
  // 引数:searchCondition: SearchCondition
  // 戻り値:ZIO[Any, Throwable, SearchUseCaseResult]
  // をテストする
  describe("searchUseCaseのテスト") {
    // given
    // 実際にrepositoryのメソッドを叩かないように、mockで用意する
    val gymRepositoryMock = mock[GymRepository]
    val searchResultMock = mock[SearchResult]
    // 実際に、gymRepositoryをinjectしているので、それを表現する
    val searchUseCase = new SearchUseCase(
      gymRepositoryMock
    )
    val searchCondition = SearchCondition(
      shopname = Some("サウス東京アネックス"),
      phoneNunber = Some(11L),
      ua = Some("web"),
      sessionId = Some("sessionId")
    )

    val gym = Gym(
      shopname = "サウス東京アネックス",
      phoneNumber = Some(11L),
      link = Some("http://localhost")
    )

    val metaData = MetaData(
      took = 10L,
      totalHits = 10L
    )

    val searchResult = SearchResult(
      gyms = IndexedSeq(gym),
      metaData = metaData
    )

    val searchUseCaseResult = SearchUseCaseResult(
      searchResult = searchResult
    )

    // gymRepository.getGymsをモックで叩く
    when(gymRepositoryMock.getGyms(searchCondition))
      // 返り値を指定する
      .thenReturn(Task.succeed(searchResult))

    // searchResult.toSearchUseCaseResultを叩く
    // whenの中は、mockじゃないとあかん
    when(searchResultMock.toSearchUseCaseResult)
      // 返り値を指定する
      .thenReturn(Task.succeed(searchUseCaseResult))


    it("should get SearchUseCaseResult") {
      // when
      // ZIOを実際に実行して、中身を評価する
      val actual = unsafeRun(
        searchUseCase.search(searchCondition)
      )

      // then
      val expected = SearchUseCaseResult(
          searchResult = searchResult
      )

      assert(actual === expected)
    }

  }

}
