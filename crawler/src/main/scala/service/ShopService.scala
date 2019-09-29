package service

import domain.model.ShopInfo
import domain.repository.shop._
import infra.elastic4s.MixInShopRepository


// shop情報をinsertするために、applicationとrepositoryの中間的な役割を担う
// case classにするのは、インスタンスを簡単に作れるから
// それら直接実装したクラスを呼び出さないといけない。
// 直接呼び出すのは、関心ごとのoverなので、だめ！
// service層は、内部の実装まで知らなくていい


trait ShopServiceInterface extends UsesShopRepository

case class ShopService() extends ShopServiceInterface with MixInShopRepository {

  def insert(shops: Seq[ShopInfo]) = {
    shopRepository.insertShopInfo(shops)
  }
}
