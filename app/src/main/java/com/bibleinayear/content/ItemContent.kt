package com.bibleinayear.content

import java.lang.StringBuilder

val VERSION = "NIV"

/**
 * Schedule item.
 */
data class Schedule(
    val week: String,
    val bible: String,
    var read: Boolean) {

    var url: String

    init {
        val list = bible.split(";")
        val searchBuilder = StringBuilder()

        list.forEachIndexed{ index, string ->
            searchBuilder.append(string.trim())
            if (index < list.size - 1) {
                searchBuilder.append("%3B+")
            }
        }
        url = "https://www.biblegateway.com/passage/?search=$searchBuilder&version=$VERSION"
    }
}


