package study.executionContext

import java.util.concurrent.{ExecutorService, Executors}

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

// 自前のExecutionContextを作る
class Execution @Inject()(mongoRepository: MongoRepository) {
  mongoRepository.find
}

// DIするモジュールを作っている
// ここで、自前のExecutionContextを作成するので、いろいろ自前の設定を定義できる
class MongoExecutionContext(threadCount: Int) extends ExecutionContext {

  // JavaのExecutorServiceを使うことで、スレッドプールを作成できる
  // newFixedThreadPoolで固定値で、プールするスレッド数を定義している。
  private val executorService: ExecutorService =
    Executors.newFixedThreadPool(threadCount)

  // Futureは、内部的に引数のブロックをRannableでラップして、ExecutionContextのexecuteメソッドを実行している。
  override def execute(runnable: Runnable): Unit =
    executorService.execute(runnable)

  override def reportFailure(cause: Throwable): Unit = throw cause
}

// 自前のモジュールを作成
// google guiceの仕様
// application.confのmoduleに定義することで、google guiceでDIできるようになる
class MongoExecutionModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ExecutionContext])
      .annotatedWith(Names.named("MongoExecutionContext"))
      .toInstance(new MongoExecutionContext(50))
  }
}

// このインスタンスは、一個しか作られないようにする。
@Singleton
class MongoRepository @Inject()(
  implicit @Named("MongoExecutionContext") ec: ExecutionContext
) {

  // このfindなどで、implicitのexecutionContextを利用するので、
  // ここでデフォルトのExecutionContextを使うのではなく、mongoとの接続に適したExecutionContextを使ってあげる。
  // このように、ミドルウェア毎に利用するExecutionContextを変更することができる。
  // この例では、Futureのapplyに渡された処理が、非同期で処理される。
  // その際に、上記でinjectしているExecutionContextが使われるので、thread数が最大50まで並行処理する。
  def find = {
    Future {
      for (i <- 1 to 50) {
        println(i * 2)
      }
    }
  }
}
