import domain.model.ShopInfo
import domain.repository.shop.ShopRepository
import zio.ZIO

package object shop extends ShopRepository.Service[ShopRepository] {
  override def insertShopInfo(shops: Seq[ShopInfo]): ZIO[ShopRepository, Any, Int] =
    ZIO.accessM(_.shopRepository.insertShopInfo(shops))

  override def deleteShopInfo: ZIO[ShopRepository, Any, Int] =
    ZIO.accessM(_.shopRepository.deleteShopInfo)
}