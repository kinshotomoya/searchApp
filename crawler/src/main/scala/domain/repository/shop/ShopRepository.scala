package domain.repository.shop

import domain.model.ShopInfo
import zio.ZIO

// ここはインターフェース
// 実際の実装は、このクラスを継承する

trait ShopRepository extends Serializable {
  val shopRepository: ShopRepository.Service[Any]
}

object ShopRepository {

  trait Service[R] extends Serializable {
    def insertShopInfo(shops: Seq[ShopInfo]): ZIO[R, Any, Int]

    def deleteShopInfo: ZIO[R, Any, Int]
  }

}
