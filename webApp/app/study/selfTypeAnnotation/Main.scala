package study.selfTypeAnnotation

class Main {
  def main(args: Array[String]): Unit = {
    val useCase = new SearchUseCase with JobRepositoryImple
    useCase.search
  }
}

// クライアント
// こいつは、具象クラスを見ないようにする。
// メリット
// JobRepositoryだけを見ているので、テストがしやすい
class SearchUseCase {
  self: JobRepository =>

  def search: Seq[String] = {
    getJobs
  }
}

// interface
trait JobRepository {
  def getJobs: Seq[String]
}

// 実装クラス
trait JobRepositoryImple extends JobRepository{
  override def getJobs: Seq[String] = Seq("job1", "job2")
}