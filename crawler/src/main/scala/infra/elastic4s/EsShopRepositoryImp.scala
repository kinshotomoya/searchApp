package infra.elastic4s

import com.sksamuel.elastic4s.http.ElasticDsl.indexInto
import com.sksamuel.elastic4s.RefreshPolicy
import com.sksamuel.elastic4s.http.ElasticDsl._
import domain.model.ShopInfo
import domain.repository.shop.ShopRepository

// 実際に実装する
case class EsShopRepositoryImp() extends ShopRepository {

  override def insertShopInfo(shops: Seq[ShopInfo]): Unit = {
    val client = EsConf.client
    val indexName = EsConf.indexName

    val shopMaps = shops.map(shopInfo =>
      Map(
        "name" -> shopInfo.name.fold("非表示"){identity},
        "siteLink" -> shopInfo.siteLink.fold("非表示"){identity},
        "phoneNumber" -> shopInfo.phoneNumber.fold("非表示"){identity}
      )
    )

    shopMaps.map { map =>
      client.execute {
        indexInto(indexName / "shop_type").fields(map).refresh(RefreshPolicy.Immediate)
      }
    }
  }
}

// service層にmixinするために実装
// これを定義することで、Esなどとinfra層の関心ごとを隠すことができる
// EsShopRepositoryImpはShopRepository を継承していることで、下の型はShopRepositoryにすることができる
trait MixInShopRepository {
  val shopRepository: ShopRepository = EsShopRepositoryImp()
}
