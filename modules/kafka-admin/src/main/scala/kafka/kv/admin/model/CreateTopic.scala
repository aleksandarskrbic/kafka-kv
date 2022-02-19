package kafka.kv.admin.model

import java.util.{Collections => Java}
import org.apache.kafka.clients.admin.NewTopic

final case class CreateTopic(name: String) {
  def compacted: NewTopic = {
    val topic = new NewTopic(name, 1, short2Short(1))
    val config = Java.singletonMap("cleanup.policy", "compact")
    topic.configs(config)
    topic
  }
}
