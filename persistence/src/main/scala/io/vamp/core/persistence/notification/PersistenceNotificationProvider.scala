package io.vamp.core.persistence.notification

import io.vamp.common.notification.{ DefaultPackageMessageResolverProvider, LoggingNotificationProvider }

trait PersistenceNotificationProvider extends LoggingNotificationProvider with DefaultPackageMessageResolverProvider