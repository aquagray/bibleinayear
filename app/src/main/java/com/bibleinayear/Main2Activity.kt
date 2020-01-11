package com.bibleinayear

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main2.*
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import com.bibleinayear.content.SHARED_PREF_KEY
import com.bibleinayear.content.Schedule

val VERSION_KEY = "BibleVersion"
var BIBLE_VERSION: String = ""

class Main2Activity : AppCompatActivity(), ScheduleFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: Schedule?) {
        item?.let {
            if (!item.isLink) {
                return@let
            }

            item.read = true
            fragment.updateView()

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            startActivity(browserIntent)

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
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        BIBLE_VERSION = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                .getString(VERSION_KEY, "ESV").toString()

<<<<<<< HEAD
        setTitle("Bible in 1 Year ($BIBLE_VERSION)")

=======
>>>>>>> 98514c2e805254f4ebc802685df9cf8f3f81ed3c
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Launching utmost.org", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://utmost.org"))
            startActivity(browserIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bible_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return fragment.handleMenu(item)
    }

    val fragment: ScheduleFragment
    get() = supportFragmentManager.findFragmentById(R.id.headlines_fragment) as ScheduleFragment
}
