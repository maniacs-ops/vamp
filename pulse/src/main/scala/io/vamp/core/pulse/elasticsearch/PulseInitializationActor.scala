package io.vamp.core.pulse.elasticsearch

import io.vamp.core.pulse.PulseActor
import io.vamp.core.pulse.notification.PulseNotificationProvider

import scala.io.Source

class PulseInitializationActor extends ElasticsearchInitializationActor with PulseNotificationProvider {

  lazy val timeout = PulseActor.timeout

  lazy val elasticsearchUrl = PulseActor.elasticsearchUrl

  lazy val templates = {
    def load(name: String) = Source.fromInputStream(getClass.getResourceAsStream(s"$name.json")).mkString.replace("$NAME", PulseActor.indexName)
    List("template", "template-event").map(template ⇒ s"${PulseActor.indexName}-$template" -> load(template)).toMap
  }
}