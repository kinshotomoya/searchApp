package infra.elastic4s

import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}

object EsConf {
  val url = "http://localhost:9200"
  val client = ElasticClient(ElasticProperties(url))
  val indexName = "shop_index"
}
