package study.ConstractPattern

class Main {

  def main(args: Array[String]): Unit = {
    val useCase = new SearchUseCase(new JobRepositoryImpleWithMysql)
    useCase.search
  }
}

class SearchUseCase(jobRepositoryImpleWithMysql: JobRepositoryImpleWithMysql) {
  // コンストラクタ引数で、SearchUseCaseにJobRepositoryImpleを委譲している。
  // searchUseCaseないでnew JobRepositoryImpleして、オブジェクトを注入するのではなく、
  // SearchUseCaseオブジェクトを宣言している箇所から、注入することによって
  // 外部から依存性注入をすることができている。
  // 外部から、これ使えって！オブジェクトを注入している。

  // メリット
  // 1. オブジェクトの構成ロジックと、通常の実行処理を分離することによって、単一責任の法則を守れている。
  // 2. なので、テストがしやすくなる

  // デメリット
  // 結局、アプリケーション実行タイミングで、newしてオブジェクトを作成しないといけないので、そこに責務が集中する。
  // 具象クラスがuseCase側に見えてしまっている（直接、具象クラスを呼び出している）ので、具象クラスの実装が変わると、useCase側にも影響が及んでしまう。
  // ex) jobRepositoryImpleWithMysqlが、jobRepositoryImpleWithElasticSearchに変わると、ごっそり変更を加えないといけなくなる。


  val jobRepositoryImpleInstance = jobRepositoryImpleWithMysql

  def search: Seq[String] = {
    val repositoryImpleWithMysql = new JobRepositoryImpleWithMysql
    jobRepositoryImpleInstance.getJobs
  }
}

trait JobRepository {
  def getJobs: Seq[String]
}

class JobRepositoryImpleWithMysql extends JobRepository {
  override def getJobs: Seq[String] = Seq("job1", "job2")
}

class jobRepositoryImpleWithElasticSearch extends JobRepository {
  override def getJobs: Seq[String] = Seq("job1", "job2")
}
