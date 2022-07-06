package data
import models.User


object Data {


  val users: Seq[User] = Seq(
    User(
      "meeptot",
      "6-10-1998",
      None,
      0,
      1000),
    User(
      "ape-paul",
      "20-04-1995",
      Some("rainforest"),
      10,
      5
    )
  )
}
