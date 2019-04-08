package models

case class Product(id: Option[Int],
                   sku: String,
                   name: String,
                   description: String,
                   updated: java.time.LocalDateTime)
