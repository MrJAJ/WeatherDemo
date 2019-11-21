package com.example.weatherdemo.util

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import java.text.DateFormat
import java.util.*
import kotlin.NoSuchElementException

fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any>) -> T): List<T> =
    parseList(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any>): T = parser(columns)
    })

fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any>) -> T): T? =
    parseOpt(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any>): T = parser(columns)
    })

fun SQLiteDatabase.clear(tableName:String){
    execSQL("delete from $tableName")
}
inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?) : R {
    for (element in this){
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate was found.")
}
fun <K, V : Any> MutableMap<K, V?>.toVarargArray():
        Array<out Pair<K, V>> = map({ Pair(it.key, it.value!!) }).toTypedArray()

