package presentation.controllers.gym

import play.api.data.Mapping
import play.api.data.Forms._
import useCase.search.SearchCondition

case class GymSearchRequestForm(
                               shopname: Option[String],
                               phoneNumber: Option[Long]
                               ) {

  def toSearchCondition(sessionId: Option[String], ua: Option[String]): SearchCondition = {
    SearchCondition(
      shopname,
      phoneNumber,
      sessionId,
      ua
    )
  }
}

object GymSearchRequestForm {
  val getMapping: Mapping[GymSearchRequestForm] = mapping(
    "shopname" -> optional(text),
    "phoneNumber" -> optional(longNumber)
  )(GymSearchRequestForm.apply)(GymSearchRequestForm.unapply)
}
