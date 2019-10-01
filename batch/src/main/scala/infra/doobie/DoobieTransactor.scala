package infra.doobie

import cats.effect.{Blocker, IO}
import com.typesafe.config.ConfigFactory
import doobie.free.driver
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import infra.doobie.model.DbConfig

trait DoobieTransactor {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    DbConfig.driver,
    DbConfig.url,
    DbConfig.user,
    DbConfig.password,
    // 同期的にしている
    Blocker.liftExecutionContext(ExecutionContexts.synchronous)
  )
}