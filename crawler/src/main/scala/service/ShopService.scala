package service

import domain.model.ShopInfo
import domain.repository.shop._
import infra.elastic4s.EsShopRepositoryImp
import zio.ZIO

trait AppEnv extends ShopRepository

// shop情報をinsertするために、applicationとrepositoryの中間的な役割を担う
// case classにするのは、インスタンスを簡単に作れるから
// ZIOを使わなかったら、ここで、ShopRepository（インターフェース）を継承して、実装しなければいけなくなる

// それら直接実装したクラスを呼び出さないといけない。
// 直接呼び出すのは、関心ごとのoverなので、だめ！
// service層は、内部の実装まで知らなくていい
case class ShopService[R <: AppEnv](){

  def insert(shops: Seq[ShopInfo]) = {
    // repositoryのメソッドを呼び出す
    // ZIOは何事もラップしてくれる
    // 今回の場合は、ShopRepositoryをラップしている
    // trait ShopRepositoryをラップし、その中のval shopRepositoryを呼び出して、メソッドを呼び出している
//    for {
//      test <- ZIO.accessM[ShopRepository](_.shopRepository.insertShopInfo(shops))
//    } yield test

    // shop.insertShopInfo(shops)
    // TODO: infraの実装がserviceに現れている。。。
    // どうやって、インターフェースであるShopRepository経由でEsShopRepositoryImpを呼び出すのかわからない
    EsShopRepositoryImp().shopRepository.insertShopInfo(shops)

  }
}
