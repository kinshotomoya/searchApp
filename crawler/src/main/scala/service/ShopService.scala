package service

import domain.model.ShopInfo
import domain.repository.shop.ShopRepository
import zio.ZIO

trait appEnv extends ShopRepository

// shop情報をinsertするために、applicationとrepositoryの中間的な役割を担う
// case classにするのは、インスタンスを簡単に作れるから
// ZIOを使わなかったら、ここで、ShopRepository（インターフェース）を継承して、実装しなければいけなくなる

// それら直接実装したクラスを呼び出さないといけない。
// 直接呼び出すのは、関心ごとのoverなので、だめ！
// service層は、内部の実装まで知らなくていい
final case class ShopService[appEnv](){

  def insertShopInfo(shops: Seq[ShopInfo]) = {
    // repositoryのメソッドを呼び出す
    // ZIOは何事もラップしてくれる
    // 今回の場合は、ShopRepositoryをラップしている
    // trait ShopRepositoryをラップし、その中のval shopRepositoryを呼び出して、メソッドを呼び出している
    ZIO.accessM[ShopRepository](_.shopRepository.insertShopInfo(shops))
  }
}
