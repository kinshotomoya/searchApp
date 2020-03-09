package study.lazyclass

class Main {
  def main(args: Array[String]): Unit = {
    val amount = 100
//    paymentRepository.save(amount)
  }
}

trait JobSearchResult {
  def meta: Int
}

final case class OrganicSearchResult(meta: Int) extends JobSearchResult
