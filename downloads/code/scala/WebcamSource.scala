object Webcam {

  /**
   * Builds a Frame [[Source]]
   *
   * @param deviceId device ID for the webcam
   * @param dimensions
   * @param bitsPerPixel
   * @param imageMode
   * @param system ActorSystem
   *
   * @return a Source of [[Frame]]s
   */
  def source(
    deviceId: Int,
    dimensions: Dimensions,
    bitsPerPixel: Int = CV_8U,
    imageMode: ImageMode = ImageMode.COLOR
  )(implicit system: ActorSystem): Source[Frame, Unit] = {
    val props = Props(
      new WebcamFramePublisher(
        deviceId = deviceId,
        imageWidth = dimensions.width,
        imageHeight = dimensions.height,
        bitsPerPixel = bitsPerPixel,
        imageMode = imageMode
      )
    )
    val webcamActorRef = system.actorOf(props)
    val webcamActorPublisher = ActorPublisher[Frame](webcamActorRef)

    Source.fromPublisher(webcamActorPublisher)
  }

  /**
   * Actor that backs the Akka Stream source
   */
  private class WebcamFramePublisher(
      deviceId: Int,
      imageWidth: Int,
      imageHeight: Int,
      bitsPerPixel: Int,
      imageMode: ImageMode
  ) extends ActorPublisher[Frame] with ActorLogging {

    private implicit val ec = context.dispatcher

    // Lazy so that nothing happens until the flow begins
    private lazy val grabber = {
      val g = new OpenCVFrameGrabber(deviceId)
      g.setImageWidth(imageWidth)
      g.setImageHeight(imageHeight)
      g.setBitsPerPixel(bitsPerPixel)
      g.setImageMode(imageMode)
      g.start()
      g
    }

    def receive: Receive = {
      case _: Request => sendFramesAsDemanded()
      case Cancel => onCompleteThenStop()
      case unexpectedMsg => log.warning(s"Unexpected message: $unexpectedMsg")
    }

    private def sendFramesAsDemanded(): Unit = {
      while (totalDemand > 0) {
        Option(grabber.grab()).foreach(onNext)
      }
    }
  }

}
