package com.bibleinayear

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bibleinayear.content.SHARED_PREF_KEY
import com.bibleinayear.content.Schedule

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), ScheduleFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: Schedule?) {
        item?.let {
            if (!item.isLink) {
                return@let
            }

            if (item.read) {
                item.read = false
                getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                    .edit().remove(item.content).apply()
            } else {
                item.read = true
                getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
                    .edit().putBoolean(item.content, true).apply()
            }
            fragment.updateView()
        }
    }

    override fun onTitleChanged() {
        setTitle("EDIT MODE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)
        setTitle("EDIT MODE")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Done Editing", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
        }
    }

    val fragment: ScheduleFragment
        get() = supportFragmentManager.findFragmentById(R.id.headlines_fragment) as ScheduleFragment
}