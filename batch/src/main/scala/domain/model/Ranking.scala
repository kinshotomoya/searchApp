package domain.model

// 以下のデータをredisに詰める
case class Ranking(
                  nameList: Seq[String],
                  created_at: String
                  )

