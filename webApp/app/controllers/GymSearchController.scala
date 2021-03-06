package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject._
import play.api.http.HeaderNames
import play.api.mvc._
import presentation.controllers.ControllerBase
import presentation.controllers.gym.GymSearchRequestForm
import presentation.converter.SearchResponseConverter
import useCase.search.{SearchCondition, SearchUseCase}

// TODO: presentation.controllers配下では、routingができなかったので、とりあえず、controllers/配下に持ってきている
// TODO: リクエストの日本語対応！
@Singleton
class GymSearchController @Inject()(cc: ControllerComponents, searchUseCase: SearchUseCase) extends ControllerBase(cc) {

  def gymSearch: Action[AnyContent] = Action.apply { implicit request: Request[AnyContent] =>
    // for yield(map)でIO[Errors, A]のAを処理をする
    val task = for {
      params <- bindRequestForm(GymSearchRequestForm.getMapping)
      searchCondition = createSearchCondition(params, request)
      searchUseCaseResult <- searchUseCase.search(searchCondition)
    } yield {
      Ok(
        SearchResponseConverter.convert(
          searchUseCaseResult,
          searchCondition
        ).asJson
      )
    }
    task.toResult
  }

  private[this] def createSearchCondition(params: GymSearchRequestForm, request: Request[AnyContent]): SearchCondition = {
    params.toSearchCondition(
      request.session.get("key"),
      request.headers.get(HeaderNames.USER_AGENT)
    )
  }


}