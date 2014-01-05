package framboos.async

import rx.lang.scala.Observable
import rx.lang.scala.Subject
import rx.lang.scala.subjects._
import scala.concurrent.duration._
import framboos._

object ObservableInPin {

  def apply(pinNumber: Int): Observable[Boolean] = {
    val inPin = ReverseInPin(pinNumber)
    var lastValue = inPin.value
    val subject = BehaviorSubject(lastValue)
    val intervals = Observable.interval(50 milliseconds)
    intervals.subscribe(next => {
      val currentValue = inPin.value
      if (currentValue != lastValue) {
        // TODO access Akka logging?
        // log.debug(s"value of in#$pinNumber changed to $currentValue")
        subject.onNext(currentValue)
      }
      lastValue = currentValue
    })
    subject
  }
}