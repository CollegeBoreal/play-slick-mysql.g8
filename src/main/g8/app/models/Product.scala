package models

import java.time.LocalDateTime

case class Product(sku: String,
                   name: String,
                   description: String,
                   updated: LocalDateTime,
                   product: Long = 0L)
