package kafka.kv.admin

import org.apache.kafka.clients.admin.NewTopic
import java.util.{List => JavaList, Collections => Java}

case class Topic(name: String) {
  def toJava: JavaList[NewTopic] = {
    val topic = new NewTopic(name, 1, short2Short(1))
    topic.configs(Java.singletonMap("cleanup.policy", "compact"))
    Java.singletonList(topic)
  }
}
