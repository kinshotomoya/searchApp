import com.redis.RedisClient
import cats._
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import doobie.util.ExecutionContexts
import fs2.Stream
import doobie.util.transactor.Transactor.fromDriverManager
// ConfigFactoryを使うと、.confで書いた設定ファイルをアプリケーションに読み込むことができる。
import com.typesafe.config.ConfigFactory



object Test {
  def main(args: Array[String]): Unit = {
    // redisの使い方
//    val redisClient = new RedisClient("localhost", 6379)
//    redisClient.set("ranking", Seq("tomoya", "nana", "ikuya", "mimi"))
//    val maybeString = redisClient.get("ranking")
//    println(maybeString)

    // doobieの使い方
    case class Person(name: String, age: Int)
    val nel = NonEmptyList.of(Person("tomoya", 24), Person("nana", 22))

    val program1 = 42.pure[ConnectionIO]
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    val config = ConfigFactory.load("database")
    val driver = config.getString("database.driver")
    val url =  config.getString("database.url")
    val user =  config.getString("database.user")
    val password =  config.getString("database.password")
    val xa = Transactor.fromDriverManager[IO](
      driver,
      url,
      user,
      password,
      // 同期的にしている
      Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
    val io = program1.transact(xa)
    println(io.unsafeRunSync)

    // stremaを指定することで、全件総なめすることを防いでいる。
    // 例えば、今回はtake(5)なので、五件取得した後にDBとのコネクションを切ってくれる
    val result = sql"select code, name, populatio, gnp from country"
      .query[Country].stream.take(5).compile.toList.transact(xa).unsafeRunSync

    println(result)

  }
}

case class Country(
                  code: String,
                  name: String,
                  pop: Int,
                  gnp: Option[Double]
                  )
