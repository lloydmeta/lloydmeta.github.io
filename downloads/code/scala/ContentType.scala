import enumeratum.values._

sealed abstract class ContentType(val value: Long, name: String) extends LongEnumEntry

case object ContentType extends LongPlayEnum[ContentType] {

  val values = findValues

  case object Text extends ContentType(value = 1L, name = "text")
  case object Image extends ContentType(value = 2L, name = "image")
  case object Video extends ContentType(value = 3L, name = "video")
  case object Audio extends ContentType(value = 4L, name = "audio")
  /* case object Sticker extends ContentType(value = 4L, name = "audio")
  *   => Fails at compile time because 4L is already used with the following error
  *   It does not look like you have unique values. Found the following values correspond to more than one members: Map(4 -> List(object Audio, object Sticker))
  */

}

assert(ContentType.withValue(1) == ContentType.Text)

ContentType.withValue(10) // => java.util.NoSuchElementException:

// Use with Play-JSON
import play.api.libs.json.{ JsNumber, JsString, Json => PlayJson }
ContentType.values.foreach { item =>
    assert(PlayJson.toJson(item) == JsNumber(item.value))
}

