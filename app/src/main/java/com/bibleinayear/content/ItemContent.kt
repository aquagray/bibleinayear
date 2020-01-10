package com.bibleinayear.content

import com.bibleinayear.BIBLE_VERSION
import java.lang.StringBuilder

val SHARED_PREF_KEY = "Schedule"

/**
 * Schedule item.
 */
data class Schedule(
    val week: Int,
    val content: String,
    var read: Boolean) {

    val isLink: Boolean get() = mIsLink

    val url: String
        get() {
            return "$mUrl$BIBLE_VERSION"
        }

    private var mUrl: String = ""
    private var mIsLink: Boolean = false

    init {
        if (content.startsWith("Week")) {
            mUrl = ""
            mIsLink = false
        } else {
            mIsLink = true
            val list = content.split(";")
            val searchBuilder = StringBuilder()

            list.forEachIndexed{ index, string ->
                searchBuilder.append(string.trim())
                if (index < list.size - 1) {
                    searchBuilder.append("%3B+")
                }
                // TODO REGEX to see if I can find ones that might not work.
            }
            mUrl = "https://www.biblegateway.com/passage/?search=$searchBuilder&version="
        }
    }
}

