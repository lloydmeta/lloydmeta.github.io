def incrementMaybeList(maybeList: Option[List[Int]], increment: Int = 1): Option[List[Int]] = {
  maybeList.map{list =>
    list.map(_ + increment)
  }
}

def incrementMaybeListFor(maybeList: Option[List[Int]], increment: Int = 1): Option[List[Int]] = {
  for {
    list <- maybeList
  } yield {
    list map (_ + 1)
  }
}