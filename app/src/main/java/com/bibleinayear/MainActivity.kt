package com.bibleinayear

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleinayear.content.Schedule
import android.content.Intent
import android.net.Uri
import android.view.View
import com.bibleinayear.content.SHARED_PREF_KEY
import android.view.Menu


class MainActivity : AppCompatActivity(), ScheduleFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: Schedule?) {
        item?.let {
            if (!item.isLink) {
                return@let
            }

            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(item.url))
            startActivity(browserIntent)
            item.read = true

            var max = 10
            var result = false
            while (!result && max > 0) {
                result = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                    .edit().putBoolean(item.content, true).commit()
                max--
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bible_menu, menu)
        return true
    }

    fun menuClicked(view: View) {

    }
}
