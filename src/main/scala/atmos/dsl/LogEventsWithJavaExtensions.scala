/* LogEventsWithJavaExtensions.scala
 *
 * Copyright (c) 2013-2014 linkedin.com
 * Copyright (c) 2013-2015 zman.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package atmos.dsl

import atmos.monitor.{EventClassifier, LogAction, LogEventsWithJava}

/**
  * Exposes extensions on any instance of `monitor.LogEventsWithJava`.
  */
case class LogEventsWithJavaExtensions(self: LogEventsWithJava) extends AbstractLogEventsExtensions {

  /* Set the level to Java logging levels. */
  override type Level = java.util.logging.Level

  /* Set the self type to the wrapped type. */
  override type Self = LogEventsWithJava

  /* Set the default retrying action. */
  override def onRetrying(action: LogAction[Level]) = self.copy(retryingAction = action)

  /* Set the default interrupted action. */
  override def onInterrupted(action: LogAction[Level]) = self.copy(interruptedAction = action)

  /* Set the default aborted action. */
  override def onAborted(action: LogAction[Level]) = self.copy(abortedAction = action)

  /* Set a custom retrying action. */
  override def onRetryingWhere(classifier: EventClassifier[LogAction[Level]]) =
    self.copy(retryingActionSelector = classifier)

  /* Set a custom interrupted action. */
  override def onInterruptedWhere(classifier: EventClassifier[LogAction[Level]]) =
    self.copy(interruptedActionSelector = classifier)

  /* Set a custom aborted action. */
  override def onAbortedWhere(classifier: EventClassifier[LogAction[Level]]) =
    self.copy(abortedActionSelector = classifier)

}
