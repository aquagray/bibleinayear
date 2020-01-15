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
            getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                .edit().putBoolean(item.content, true).apply()
        }
    }

    override fun onTitleChanged() {
        setTitle("Bible in 1 Year ($BIBLE_VERSION)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        BIBLE_VERSION = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                .getString(VERSION_KEY, "ESV").toString()
        setTitle("Bible in 1 Year ($BIBLE_VERSION)")

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
        return when(item.itemId) {
            R.id.edit -> {
                val intent = Intent(this, EditActivity::class.java)
                startActivity(intent)
                true
            }
            else -> fragment.handleMenu(item)
        }
    }

    val fragment: ScheduleFragment
    get() = supportFragmentManager.findFragmentById(R.id.headlines_fragment) as ScheduleFragment
}
