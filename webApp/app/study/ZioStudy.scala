package study
import java.io.IOException

import org.checkerframework.checker.units.qual.A
import views.html.defaultpages.error
import zio.{IO, Task, UIO, ZIO}
import zio.blocking._

import scala.concurrent.Future
import scala.io.{Codec, Source, StdIn}

class ZioStudy {

  def test() = {

    val value: Task[Int] = Task.effect(42)
    value
    // ZIO[Any, Throwable, Int]

    val value2: Task[A] = ZIO.fail(new Exception)
    value2
    // ZIO[Any, Exception, A]

    val valueOpt: IO[Unit, Int] = ZIO.fromOption(Some(2))
    // ZIO[Any, E, A]
    // OptionからZIOに変換できる
    // この場合は、IO[]になる。なぜなら、Noneの時はどんなエラーかわからないから

    val valueOptError: IO[Exception, Int] = valueOpt.mapError(_ =>  new Exception)
    // ZIO[Any, Exception, Int]
    // Eに特定のエラタイプを入れることができる

    val valueEither: IO[Nothing, String] = ZIO.fromEither(Right("Success"))
    // ZIO[Any, Nothing, String]
    // IO[E, A]になるのは、Leftの場合にEにはどんなエラータイプも格納されるから。

    val valueFunc: ZIO[Int, Nothing, Int] = ZIO.fromFunction((i: Int) => i * i)
    // Rは、Aの型（関数の引数の型）になる。なぜなら、その関数を実行すると必ず引数と同じ型が返ってくる関数であるから。


    lazy val future = Future.successful("Hello!!!")

    val valueFuture: Task[String] = ZIO.fromFuture { implicit ec =>
      future.map(_ => "Goodbye!!!")
    }
    valueFuture
    // ZIO[Any, Throwable, String]
    // Futureは必ずThrowableを投げるからTaskになる

    // 副作用をZIOにラップする場合
    val valueSideEffect: Task[String] = ZIO.effect(StdIn.readLine())
    valueSideEffect
    // ZIO[Any, Throwable, String]

    val valueTotal: UIO[Unit] = ZIO.effectTotal(println("s"))
    valueTotal
    // ZIO[Any, Nothing, Unit]
    // 副作用として、何もエラーを返さないとわかっているなら、effectTotalを使う。
    //今回の場合には、printlnなのでエラーはない

    val function: IO[IOException, String] = ZIO.effect(StdIn.readLine()).refineToOrDie[IOException]
    function
    // ZIO[Any, IOException, String]
    // エラーを致命的なエラーとして扱いたい場合には、このメソッドを使う


    val valueBlock: ZIO[Blocking, Throwable, Unit] = effectBlocking(Thread.sleep(Long.MaxValue))
    valueBlock
    // blockingの副作用もZIOでラップできる



    def download(url: String): Task[String] = {
      val value = Task.effect {
        Source.fromURL(url)(Codec.UTF8).mkString
      }
      value
    }

    def safeDownload(url: String): ZIO[Blocking, Throwable, String] = {
      blocking(download(url = "http://localhost"))
    }

    // 副作用がすでにZIOでラップされていると、そのラップされたものをさらに
    // Blockingでラップすることで、安全にできる

    val valueMap: UIO[Int] = IO.succeed(21).map(_ * 2)
    valueMap
    // mapで、valueの値を処理できる

    val valuea: IO[Exception, Unit] = IO.fail("No no!").mapError(msg => new Exception(msg))
    // errorの値を処理したい場合は、mapErrorを使う

   val zioValueForfor: ZIO[Any, Throwable, String] = Task.effect("I am tomoya kinsho")
    for {
      // for式で、mapすると、valueの値を処理できる
      // for式で書くと書くと読みやすくなる
      introduction <- zioValueForfor
    } yield {
      introduction + "!!!!!!"
    }

    val valueZip: UIO[(String, Int)] = ZIO.succeed("hello").zip(ZIO.succeed(10))
    valueZip
    // zipを使うと、valueをタプルで返せる

    val valueRight: ZIO[Any, Throwable, String] = ZIO.succeed(2).zipRight(Task("ss"))
    val valueLeft: ZIO[Any, Throwable, Int] = ZIO.succeed(2).zipLeft(Task("ss"))

    // zipRightをすると、まず(2, "ss")のタプルにして、右側のもの、つまり"ss"を返す
    // zipLeftをすると、まず(2, "ss")のタプルにして、左側のもの、つまり2を返す

    val either: UIO[Either[String, Nothing]] = IO.fail("uh no!").either
    either
    // valueをEitherにすることができる

    def sqrt(io: UIO[Double]): IO[String, Double] = {
      //absolveで、EitherをZIO[R, E, A]にすることができる
      ZIO.absolve(
        io.map(value => {
          if(value < 0.0) Left("s")
          else Right(Math.sqrt(value))
        })
      )
    }

    val valueError: ZIO[Any, Exception, A] = ZIO.fail(new Exception("oh no!!"))

    // ZIO[R, E, A]のEの処理をしている。
    // catchAllなので、Eがなんであれ、ここの処理を通る。
    valueError.catchAll {
      // EがIOExceptionの場合に、Aにエラーメッセージを詰めている
      case errors: IOException => ZIO.succeed(errors.getMessage)
      // EがThrowableの場合に、Aにerrorメッセージを詰めている
      case e: Throwable => ZIO.succeed("Throwable")
      // 最終的にフロントに渡す値は、Aになるので、エラーにせよ正常な値にせよ
      // Aに詰める！！！！！！！
    }


    // 特定のエラーの時だけキャッチする
    valueError.catchSome {
      case e: IOException => ZIO.succeed("only IOException")
    }

//    val eitherright: Either[IOException, String] = Right("success")
//    val values = eitherright.fold(
//      l => IOException,
//      r => 2
//    )
//    values


    val valuew: ZIO[Any, IOException, A] = ZIO.fail(new IOException())
    // ZIO.foldは、２つの引数を取り。
    // １つ目：Eの場合の処理
    // ２つ目；Aの場合の処理
    // どちらにしても、戻り値はUIO[A]なので、Aに値を詰める
    val values: UIO[String] = valuew.fold(
      failure => "fail",
      success => "success"
    )
    values


  }

}
