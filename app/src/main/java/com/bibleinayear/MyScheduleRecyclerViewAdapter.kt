package com.bibleinayear

import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bibleinayear.ScheduleFragment.OnListFragmentInteractionListener
import com.bibleinayear.content.Schedule

import kotlinx.android.synthetic.main.fragment_schedule_link.view.*

/**
 * [RecyclerView.Adapter] that can display a [Schedule] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyScheduleRecyclerViewAdapter(
    private val mValues: List<Schedule>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as Schedule
        mListener?.onListFragmentInteraction(item)
    }

    fun getLastUnreadPosition(): Int {
        var unread = 0
        run lit@ {
            mValues.forEachIndexed { index, schedule ->
                if (schedule.isLink && !schedule.read) {
                    unread = index
                    return@lit
                }
            }
        }
        return Math.min(unread, Math.abs(unread-1))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_schedule_link, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.setData(item)
    }

    fun markReadUntil(sf: SharedPreferences, week: Int) {
        val toCompare = week + 1 // inclusive
        val edit = sf.edit()
        mValues.forEach {
            if (it.week == toCompare) {
                edit.commit()
                return
            }
            it.read = true
            edit.putBoolean(it.content, true)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun clear(sf: SharedPreferences) {
        sf.edit().clear().commit()
        mValues.forEach {
            it.read = false
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var mItem: Schedule? = null

        override fun toString(): String {
            return super.toString() + " '" + mView.item_content_txt.text + "'"
        }

        fun setData(item: Schedule) {
            mItem = item
            if (!item.isLink) {
                applyTitleTheme()
            } else {
                applyLinkTheme(item)
            }

            if ((item.week % 2) == 0) {
                applyTheme1()
            } else {
                applyTheme2()
            }

            mView.item_content_txt.text = item.content
            mView.tag = item
            mView.setOnClickListener(mOnClickListener)
            mView.invalidate()
        }

        private fun applyTitleTheme() {
            mView.item_checkbox.isChecked = false
            mView.item_checkbox.visibility = View.INVISIBLE
            mView.item_content_txt.setTypeface(null, Typeface.BOLD)
        }

        private fun applyLinkTheme(item: Schedule) {
            mView.item_checkbox.isChecked = item.read
            mView.item_checkbox.visibility = View.VISIBLE
            mView.item_content_txt.setTypeface(null, Typeface.NORMAL)
        }

        private fun applyRead(read: Boolean) {
            mView.item_checkbox.isChecked = read
        }

        private fun applyTheme1() {
            applyColors(R.color.colorBackground1, R.color.colorText1)
        }

        private fun applyTheme2() {
            applyColors(R.color.colorBackground2, R.color.colorText2)
        }

        private fun applyColors(bgColor: Int, txtColor: Int) {
            val bgColor = mView.resources.getColor(bgColor)
            val txtColor = mView.resources.getColor(txtColor)

            mView.item_content_bg.setBackgroundColor(bgColor)
            mView.item_content_txt.setBackgroundColor(bgColor)
            mView.item_content_txt.setTextColor(txtColor)
        }
    }
}
