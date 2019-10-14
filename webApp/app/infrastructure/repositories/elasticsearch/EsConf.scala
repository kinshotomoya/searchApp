package infrastructure.repositories.elasticsearch

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient

object EsConf {
  val url = "elasticsearch://localhost:9200"
  val client = HttpClient(ElasticsearchClientUri(url))
  val indexName = "shop_index"
}
