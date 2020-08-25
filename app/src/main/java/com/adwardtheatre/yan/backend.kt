package com.adwardtheatre.yan

import com.squareup.moshi.Json

data class Astros(
    @field:Json(name = "message")
    var message: String,
    @field:Json(name = "number")
    var number: Int,
    @field:Json(name = "people")
    var peopleList: MutableList<Person>
)

data class ISSLocationNow(
    @field:Json(name = "timestamp")
    var timestamp: String,
    @field:Json(name = "message")
    var message: String,
    @field:Json(name = "iss_position")
    var whereAbouts: WhereAbouts
)

data class Person(
    @field:Json(name = "craft")
    var craft: String,
    @field:Json(name = "name")
    var name: String
)

data class WhereAbouts(
    @field:Json(name = "longitude")
    var longitude: String,
    @field:Json(name = "latitude")
    var latitude: String
)