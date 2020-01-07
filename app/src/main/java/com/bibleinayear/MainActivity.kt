package com.bibleinayear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleinayear.content.Schedule
import android.content.Intent
import android.net.Uri


class MainActivity : AppCompatActivity(), ScheduleFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: Schedule?) {
        item?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse(item.url))
            startActivity(browserIntent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
