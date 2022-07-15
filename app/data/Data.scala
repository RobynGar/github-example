package data
import models.User


object Data {


  val users: Seq[User] = Seq(
    User(
      "Meep",
      "6-10-1998",
      None,
      0,
      1000),
    User(
      "Ape-Paul",
      "20-04-1995",
      Some("Rainforest"),
      10,
      5
    )
  )
}
