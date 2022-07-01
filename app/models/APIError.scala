package models

class APIError(
                val httpResponseStatus: Int,
                val reason: String
              )
object APIError {

  case class BadAPIResponse(status: Int, message: String) extends APIError(
    status, message
    )

}
