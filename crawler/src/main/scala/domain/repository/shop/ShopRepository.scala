package domain.repository.shop

import domain.model.ShopInfo
import zio.ZIO

// ここはインターフェース
// 実際の実装は、このクラスを継承する

trait ShopRepository {
  def insertShopInfo(shops: Seq[ShopInfo]): Unit
}

trait UsesShopRepository {
  val shopRepository: ShopRepository
}
