/**
  * Holds an id and an OpenCV Rect defining the corners of a rectangle.
  *
  * There is nothing *face* specific in this class per say; it can hold ids and Rects for any detected
  * object
  */
case class Face(id: Long, faceRect: Rect)
