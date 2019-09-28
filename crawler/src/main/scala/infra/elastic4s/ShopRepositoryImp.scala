package infra.elastic4s

import domain.model.ShopInfo
import domain.repository.shop.ShopRepository
import zio.ZIO

// 実際に実装する
// TODO ZIO Serializableがよくわからんので、調べる
class ShopRepositoryImp extends ShopRepository {
  //
  override val shopRepository: ShopRepository.Service[Any] = new ShopRepository.Service[Any] {
    override def insertShopInfo(shops: Seq[ShopInfo]): ZIO[Any, Any, Int] = {
      ZIO.fromOption(Some(222222))
    }

    override def deleteShopInfo: ZIO[Any, Any, Int] = {
      ZIO.fromOption(Some(222222))
    }
  }
}
