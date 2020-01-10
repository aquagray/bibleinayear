package com.bibleinayear.content

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun generateContent(context: Context): List<Schedule> {

    val list = ArrayList<Schedule>()
    val input = context.getResources().openRawResource(com.bibleinayear.R.raw.schedule)
    val br = BufferedReader(InputStreamReader(input))

    try {
        var week = 0
        br.lines().forEach {
            if (it.isBlank()) {
                return@forEach
            }

            if (it.startsWith("Week")) {
                week = it.substringAfter("Week ").toInt()
                list.add(Schedule(week, it, false))
                return@forEach
            }

            val read = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE).getBoolean(it, false)
            list.add(Schedule(week, it, read))
        }

    } catch (e: Throwable) {
        e.printStackTrace() //create exception output
    }
    return list
}