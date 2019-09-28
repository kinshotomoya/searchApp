package infra.elastic4s

import com.sksamuel.elastic4s.http.ElasticDsl.{bulk, indexInto}
import com.sksamuel.elastic4s.{ElasticsearchClientUri, RefreshPolicy}
import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.http.ElasticDsl._
import domain.model.ShopInfo
import domain.repository.shop.ShopRepository
import zio.ZIO

// 実際に実装する
// TODO ZIO Serializableがよくわからんので、調べる
case class EsShopRepositoryImp() extends ShopRepository {

  override val shopRepository = new ShopRepository.Service[Any] {

    override def insertShopInfo(shops: Seq[ShopInfo]): ZIO[Any, Any, Int] = {
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

      ZIO.fromOption(Some(222222))
    }

    override def deleteShopInfo: ZIO[Any, Any, Int] = {
      ZIO.fromOption(Some(222222))
    }

  }
}
