sealed abstract class HaarDetectorFlag(val flag: Int)

case object HaarDetectorFlag {

  case object DoCannyPruning extends HaarDetectorFlag(CV_HAAR_DO_CANNY_PRUNING)
  case object ScaleImage extends HaarDetectorFlag(CV_HAAR_SCALE_IMAGE)
  case object FindBiggestObject extends HaarDetectorFlag(CV_HAAR_FIND_BIGGEST_OBJECT)
  case object DoRoughSearch extends HaarDetectorFlag(CV_HAAR_DO_ROUGH_SEARCH)

}
