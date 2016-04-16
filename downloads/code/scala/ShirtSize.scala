import enumeratum._

sealed trait ShirtSize extends EnumEntry

case object ShirtSize extends CirceEnum[ShirtSize] with Enum[ShirtSize] {

  case object Small extends ShirtSize
  case object Medium extends ShirtSize
  case object Large extends ShirtSize

  val values = findValues

}

import io.circe.Json
import io.circe.syntax._

ShirtSize.values.foreach { size =>
    assert(size.asJson == Json.fromString(size.entryName))
}
