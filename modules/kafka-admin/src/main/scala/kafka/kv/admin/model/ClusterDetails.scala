package kafka.kv.admin.model

import org.apache.kafka.common.Node

final case class ClusterDetails(clusterId: String, controller: Node, nodes: List[Node])
