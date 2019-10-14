package presentation.controllers

import domain.Errors
import play.api.data.{Form, Mapping}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request, Result}
import zio._
import play.api.libs.circe.Circe
import io.circe.syntax._
import io.circe.generic.auto._

// Circeのtraitをmixinすることで、Ok BadRequest などのResult型をJsonにすることができる
abstract class ControllerBase(cc: ControllerComponents) extends AbstractController(cc) with Circe {

  // 型渡し
  // ZIOでラップしている戻り値を
  // ここでは、Error型と引数の型で返す
  // IO[E, A]はZIO[Any, E, A]と同じ
  // fromEitherで、Leftの場合はEに詰める Rightの場合は、Aに詰める処理ができる
  def bindRequestForm[A](mapping: Mapping[A])(implicit request: Request[AnyContent]): IO[Errors, A]  = {
    //
    ZIO.fromEither {
      val params = Form(mapping).bindFromRequest.fold(
        // Leftの場合の処理
        hasErrors => Left(Errors(hasErrors.errors.map(e => e.key -> e.messages).toMap)),
        // rightの場合の処理
        Right(_)
      )
      // Either[Errors, A]になっている
      // これをZIO.fromEitherでZIOでラップしている。
      params
    }
  }

  implicit class PlayZio(task: ZIO[Any, Any, Result]) {
    def toResult: Result = {
      val errorHandled = errorHandle(task)
      // ZIOはruntimeを含んでいるので、そのruntimeを作成する。
      // このruntimeは、effectsを実行することができる
      new DefaultRuntime {}.unsafeRun(errorHandled)
    }
  }

  private[this] def errorHandle(task: ZIO[Any, Any, Result]): ZIO[Any, Any, Result]  = {
    task.catchAll {
      // TODO: errorをjsonにパースする!
      case error: Errors => ZIO.succeed(BadRequest(error.asJson))
      case _: Unit => ZIO.succeed(NotFound)
      case e: Throwable => ZIO.succeed(InternalServerError)
      case anyError => ZIO.succeed(InternalServerError)
      // Nothingの時はキャッチされない！！！！！！
    }
  }
}
