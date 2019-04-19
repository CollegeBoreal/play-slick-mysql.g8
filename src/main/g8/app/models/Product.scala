package models

import java.time.LocalDateTime

case class Product(sku: String,
                   name: String,
                   description: String,
                   updated: LocalDateTime,
                   id: Long = 0L)
