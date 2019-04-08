package object controllers {

  import play.api.libs.json._

  implicit object timestampFormat extends Format[java.sql.Timestamp] {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    def reads(json: JsValue) =
      JsSuccess(new java.sql.Timestamp(format.parse(json.as[String]).getTime))
    def writes(ts: java.sql.Timestamp) = JsString(format.format(ts))
  }

}
