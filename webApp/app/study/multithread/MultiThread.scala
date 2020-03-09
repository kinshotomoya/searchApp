package study.multithread

import java.util.concurrent.{ExecutorService, Executors}

import scala.concurrent.ExecutionContext

class MultiThread extends App {
  val threadPool = 10
  override def main(args: Array[String]): Unit = {
    val service: ExecutorService = Executors.newFixedThreadPool(threadPool)
    implicit val ex = ExecutionContext.fromExecutorService(service)

    try {
      service.execute(new Handler)
    } finally {
      service.shutdown()
    }
  }
}

class Handler extends Runnable{
  override def run(): Unit = {
    println(Thread.currentThread.getName)
  }
}
