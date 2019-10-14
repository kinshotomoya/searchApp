package crawler

import domain.model.ShopInfo
import org.jsoup.Jsoup
import service.ShopService

import scala.collection.JavaConverters._

object WebCrawler {
  def main(args: Array[String]): Unit = {
    // 店舗名・リンク先を取得する
    // case classに詰めて、elasticseachに処理を投げる
    val urlStr = "http://www.goldsgym.jp"
    try {
      val siteHtml = Jsoup.connect(urlStr).get
//      siteHtml.select(".hogeclass#hogeid")

      val shopItems = siteHtml.getElementsByClass("shop-list-item-container").select("[title]").asScala

      val shops: Seq[ShopInfo] = shopItems.map { shop =>
        ShopInfo(
          name = Option(shop.attr("title").changeEmptyStrIntoNull),
          siteLink = Option(urlStr + shop.attr("href").changeEmptyStrIntoNull),
          phoneNumber = Option(shop.attr("phoneNumber").changeEmptyStrIntoNull)
        )
      }
      ShopService().insert(shops)

    } catch {
      case e:Exception => println(e.getMessage)
    }
  }

  implicit class StrSchanger(str: String) {
    // ""はNoneにするために実装
    def changeEmptyStrIntoNull = {
      if(str == "") null else str
    }
  }
}




