package com.composenhilt.data.room_database.rockets

import androidx.room.*
import com.composenhilt.data.room_database.type_converter.ConverterArray

@Entity
data class RocketsModel(
    @PrimaryKey
    var id: String,
    var name: String?,
    var type: String?,
    var active: Boolean?,
    var boosters: Int?,
    var stages: Int?,
    var country: String?,
    var wikipedia: String?,
    var description: String?,
    @TypeConverters(ConverterArray::class)
    var flickr_images: List<String>?,
) {

    constructor() : this("",  null,null,  null,  null,null,  null,null,  null, null)
}