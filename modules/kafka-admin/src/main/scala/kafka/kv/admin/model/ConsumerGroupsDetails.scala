package kafka.kv.admin.model

import org.apache.kafka.clients.admin.ConsumerGroupDescription

final case class ConsumerGroupsDetails(value: Map[String, ConsumerGroupDescription])
