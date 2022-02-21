package kafka.kv.common

import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{Executors, ThreadFactory}

/** Unbounded thread-pool that is used for blocking operations.
  */
object Blocker {
  def default(): ExecutionContext =
    ExecutionContext.fromExecutor(
      Executors.newCachedThreadPool(
        new ThreadFactory {
          private val counter = new AtomicLong(0L)

          def newThread(runnable: Runnable): Thread = {
            val thread = new Thread(runnable)
            thread.setName("kafka-client-tp" + counter.getAndIncrement.toString)
            thread.setDaemon(true)
            thread
          }
        }
      )
    )
}
