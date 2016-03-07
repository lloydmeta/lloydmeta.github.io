object FaceDetector {

  /**
   * Builds a FaceDetector with the default Haar Cascade classifier in the resource directory
   */
  def defaultCascadeFile(
    dimensions: Dimensions,
    scaleFactor: Double = 1.3,
    minNeighbours: Int = 3,
    detectorFlag: HaarDetectorFlag = HaarDetectorFlag.DoCannyPruning,
    minSize: Dimensions = Dimensions(width = 30, height = 30),
    maxSize: Option[Dimensions] = None
  ): FaceDetector = {
    val classLoader = this.getClass.getClassLoader
    val faceXml = classLoader.getResource("haarcascade_frontalface_alt.xml").getPath
    new FaceDetector(
      dimensions = dimensions,
      classifierPath = faceXml,
      scaleFactor = scaleFactor,
      minNeighbours = minNeighbours,
      detectorFlag = detectorFlag,
      minSize = minSize,
      maxSize = maxSize
    )
  }
}

class FaceDetector(
    val dimensions: Dimensions,
    classifierPath: String,
    scaleFactor: Double = 1.3,
    minNeighbours: Int = 3,
    detectorFlag: HaarDetectorFlag = HaarDetectorFlag.ScaleImage,
    minSize: Dimensions = Dimensions(width = 30, height = 30),
    maxSize: Option[Dimensions] = None
) {

  private val faceCascade = new CascadeClassifier(classifierPath)

  private val minSizeOpenCV = new Size(minSize.width, minSize.height)
  private val maxSizeOpenCV = maxSize.map(d => new Size(d.width, d.height)).getOrElse(new Size())

  /**
   * Given a frame matrix, a series of detected faces
   */
  def detect(frameMatWithGrey: WithGrey): (WithGrey, Seq[Face]) = {
    val currentGreyMat = frameMatWithGrey.grey
    val faceRects = findFaces(currentGreyMat)
    val faces = for {
      i <- 0L until faceRects.size()
      faceRect = faceRects.get(i)
    } yield Face(i, faceRect)
    (frameMatWithGrey, faces)
  }

  private def findFaces(greyMat: Mat): RectVector = {
    val faceRects = new RectVector()
    faceCascade.detectMultiScale(greyMat, faceRects, scaleFactor, minNeighbours, detectorFlag.flag, minSizeOpenCV, maxSizeOpenCV)
    faceRects
  }

}
