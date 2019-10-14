package useCase.search

case class SearchCondition(
                          shopname: Option[String],
                          phoneNunber: Option[Long],
                          ua: Option[String],
                          sessionId: Option[String]
                          )
