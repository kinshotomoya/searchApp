package study.abstractFactoryPattern

class Main {
  def main(args: Array[String]): Unit = {
    val searchUseCase = new SearchUseCase
    searchUseCase.search
  }
}


// 使用側は、具象クラスについて知る必要がなくなる。
// 特徴
// アプリケーション側でオブジェクトの生成に関する制御を行う

// メリット
// 1. factory.createJobRepositoryで、オブジェクト生成部分を実行処理を分離することで、関心ごとの分離を実現できている。
// 2. 以下の例のように、永続化層を呼び出す側は、具象クラスを知る必要がなくなる。
  // repository.getJobsなので、呼び出し側は具象クラスが変更されても（例えば、JobRepositoryImpleWithMysql -> JobRepositoryImpleWithElasticSearch）
  // 影響を受けない。


class SearchUseCase {
  def search = {
    val repository: JobRepository = JobRepositoryFactory.createJobRepository
    repository.getJobs
  }
}

// abstract factory
trait AbstractFactory {
  def createJobRepository: JobRepository
}

// abstract product
trait JobRepository {
  def getJobs: Seq[String]
}

// concrete factory
// シングルトンオブジェクトでいい。
// JobRepositoryFactoryはシステムの中で、１つしか存在しなくていいので、classにする必要はない
object JobRepositoryFactory extends AbstractFactory {
  override def createJobRepository: JobRepository = new JobRepositoryImpleWithMysql
}

// concrete product
class JobRepositoryImpleWithMysql extends JobRepository {
  override def getJobs: Seq[String] = Seq("job1", "job2")
}
