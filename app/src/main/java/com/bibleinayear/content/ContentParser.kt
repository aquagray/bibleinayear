package com.bibleinayear.content

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun generateContent(context: Context): List<Schedule> {

    val list = ArrayList<Schedule>()
    val input = context.getResources().openRawResource(com.bibleinayear.R.raw.schedule)
    val br = BufferedReader(InputStreamReader(input))

    try {
        var week: String = ""
        br.lines().forEach {
            if (it.isBlank()) {
                return@forEach
            }

            if (it.startsWith("Week")) {
                week = it.substringAfter("Week ")
                return@forEach
            }

            list.add(Schedule(week, it, false))


        }

    } catch (e: Throwable) {
        e.printStackTrace() //create exception output
    }
    return list
}