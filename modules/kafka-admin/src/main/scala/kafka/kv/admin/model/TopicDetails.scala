package kafka.kv.admin.model

import org.apache.kafka.clients.admin.TopicListing

final case class TopicDetails(name: String, internal: Boolean)

object TopicDetails {
  def fromTopicListing(topicListing: TopicListing) =
    new TopicDetails(topicListing.name, topicListing.isInternal)
}
