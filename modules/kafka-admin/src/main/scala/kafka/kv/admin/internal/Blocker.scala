package kafka.kv.admin.internal

import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{Executors, ThreadFactory}

/**
 * Unbounded thread-pool that is used for blocking operations.
 */
object Blocker {
  def default(name: String = "kafka-admin-tp"): ExecutionContext =
    ExecutionContext.fromExecutor(
    Executors.newCachedThreadPool(
      new ThreadFactory {
        private val counter = new AtomicLong(0L)

        def newThread(r: Runnable): Thread = {
          val thread = new Thread(r)
          thread.setName("kafka-admin-tp-" + counter.getAndIncrement.toString)
          thread.setDaemon(true)
          thread
        }
      }
    )
  )
}
