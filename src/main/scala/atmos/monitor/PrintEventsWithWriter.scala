/* PrintEventsWithWriter.scala
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
package atmos.monitor

import java.io.PrintWriter

/**
  * An event monitor that prints information about retry events to a writer.
  *
  * @param writer                    The writer that this event monitor prints to.
  * @param retryingAction            The action that is performed by default when a retrying event is received.
  * @param interruptedAction         The action that is performed by default when an interrupted event is received.
  * @param abortedAction             The action that is performed by default when an aborted event is received.
  * @param retryingActionSelector    The strategy used to select an action to perform for a retrying event, defaulting to
  *                                  `retryingAction`.
  * @param interruptedActionSelector The strategy used to select an action to perform for an interrupted event,
  *                                  defaulting to `interruptedAction`.
  * @param abortedActionSelector     The strategy used to select an action to perform for an aborted event, defaulting to
  *                                  `abortedAction`.
  */
case class PrintEventsWithWriter(
    writer: PrintWriter,
    retryingAction: PrintAction = PrintEventsWithWriter.defaultRetryingAction,
    interruptedAction: PrintAction = PrintEventsWithWriter.defaultInterruptedAction,
    abortedAction: PrintAction = PrintEventsWithWriter.defaultAbortedAction,
    retryingActionSelector: EventClassifier[PrintAction] = EventClassifier.empty,
    interruptedActionSelector: EventClassifier[PrintAction] = EventClassifier.empty,
    abortedActionSelector: EventClassifier[PrintAction] = EventClassifier.empty
) extends PrintEvents {

  /* Pass the message to the underlying writer. */
  override def printMessage(message: String) = writer.println(message)

  /* Pass the message and throwable to the underlying writer. */
  override def printMessageAndStackTrace(message: String, thrown: Throwable) = writer synchronized {
    writer.println(message)
    thrown.printStackTrace(writer)
  }

}

/**
  * Definitions associated with event monitors that print information about retry events as text.
  */
object PrintEventsWithWriter {

  import PrintAction._

  /** The action that is performed by default when a retrying event is received. */
  val defaultRetryingAction: PrintAction = PrintMessage

  /** The action that is performed by default when an interrupted event is received. */
  val defaultInterruptedAction: PrintAction = PrintMessageAndStackTrace

  /** The action that is performed by default when an aborted event is received. */
  val defaultAbortedAction: PrintAction = PrintMessageAndStackTrace

}
