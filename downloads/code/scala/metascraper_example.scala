import akka.actor.ActorSystem
import com.beachape.metascraper.Messages._
import com.beachape.metascraper.ScraperActor
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

implicit val timeout = Timeout(30 seconds)

implicit val system = ActorSystem("actorSystem")
implicit val dispatcher = system.dispatcher

val scraperActor = system.actorOf(ScraperActor())

for {
  future <- ask(scraperActor, ScrapeUrl("https://bbc.co.uk")).mapTo[Either[FailedToScrapeUrl,ScrapedData]]
} {
  future match {
    case Left(failed) => {
      println("Failed: ")
      println(failed.message)
    }
    case Right(data) => {
      println("Image urls")
      data.imageUrls.foreach(println)
    }
  }
}

/*
 #=>
  Image URLs:
  http://www.bbc.co.uk/img/iphone.png
  http://sa.bbc.co.uk/bbc/bbc/s?name=SET-COUNTER&pal_route=index&ml_name=barlesque&app_type=web&language=en-GB&ml_version=0.16.1&pal_webapp=wwhp&blq_s=3.5&blq_r=3.5&blq_v=default-worldwide
  http://static.bbci.co.uk/frameworks/barlesque/2.51.2/desktop/3.5/img/blq-blocks_grey_alpha.png
  http://static.bbci.co.uk/frameworks/barlesque/2.51.2/desktop/3.5/img/blq-search_grey_alpha.png
  http://news.bbcimg.co.uk/media/images/69612000/jpg/_69612953_69612952.jpg
*/