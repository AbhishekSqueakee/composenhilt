package com.composenhilt.data.room_database.type_converter

import androidx.room.TypeConverter


class ConverterArray {
    @TypeConverter
    fun fromListStringToString(stringList: List<String>): String = stringList.toString()
    @TypeConverter
    fun toListStringFromString(stringList: String): List<String> {
        val result = ArrayList<String>()
        val split =stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n)
            } catch (e: Exception) {

            }
        }
        return result
    }
}


