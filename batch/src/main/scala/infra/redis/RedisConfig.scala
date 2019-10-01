package infra.redis

import com.redis.RedisClient

trait RedisConfig {
  val redisClient = new RedisClient("localhost", 6379)

}
