package infrastructure.repositories.gym


import cats.instances.future
import com.sksamuel.elastic4s.{ElasticsearchClientUri, HitReader}
import com.sksamuel.elastic4s.http.ElasticDsl._
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.sksamuel.elastic4s.searches.queries.term._
import domain.gym.{Gym, GymRepository}
import domain.search.{MetaData, SearchResult}
import useCase.search.SearchCondition
import zio.{Task, UIO, ZIO}

import scala.concurrent.{ExecutionContext, Future}


class GymRespositoryImpl extends GymRepository{

  implicit lazy val hitReader: HitReader[Gym] = {
    import com.sksamuel.elastic4s.circe._
    import io.circe.generic.auto._
    hitReaderWithCirce[Gym]
  }

  override def getGyms(searchCondition: SearchCondition): ZIO[Any, Throwable, SearchResult] = {
    // 概要：conditionからESを返す
    // conditionからQueryを作る
    // ESからSearchResponseからSearchResult(metadata, indexSeq(Gym))を作る
    //　ZIOでラップしてuseCaseに返す
    for {
      esResponse <- executeEsSearch(searchCondition)
      searchResult <- toSearchResult(esResponse)
    } yield {
      searchResult
    }
  }

  private[this] def executeEsSearch(searchCondition: SearchCondition): Task[SearchResponse] = {
    val url = "elasticsearch://localhost:9200"
    val client = HttpClient(ElasticsearchClientUri(url))
    val indexName = "shop_index"
    val query = TermQueryDefinition("name", searchCondition.shopname.getOrElse(""))
    // Futureで返ってくるので、ZIO[Any, Throwable, A] == Task[A]に変換して返す
    ZIO.fromFuture { _: ExecutionContext =>
      val body = search(indexName).query(query)
      client.execute(body)
    }
  }

  private[this] def toSearchResult(esResponse: SearchResponse): Task[SearchResult] = {
    Task.effect(
      SearchResult(
        esResponse.to[Gym].toIndexedSeq,
        MetaData(
          esResponse.took,
          esResponse.totalHits
        )
      )
    )
  }
}
