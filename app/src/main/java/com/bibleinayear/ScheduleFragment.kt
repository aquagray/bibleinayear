package com.bibleinayear

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.app.AlertDialog
import android.text.InputType
import android.view.*
import android.widget.EditText
import com.bibleinayear.content.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ScheduleFragment.OnListFragmentInteractionListener] interface.
 */
class ScheduleFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private var mAdapter: MyScheduleRecyclerViewAdapter? = null
    private var mView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule_list, container, false)
        mView = view as RecyclerView

        with(mView!!) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyScheduleRecyclerViewAdapter(generateContent(context), listener)
        }

        mAdapter = mView!!.adapter as MyScheduleRecyclerViewAdapter
        with(mView!!.layoutManager as LinearLayoutManager) {
            scrollToPositionWithOffset(mAdapter!!.getLastUnreadPosition(), 20)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Schedule?)

        fun onTitleChanged()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    fun handleMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_all -> {

                clearAll()
                true
            }
            R.id.mark_read -> {
                markReadUntil()
                true
            }
            R.id.niv -> {
                BIBLE_VERSION = "NIV"
                saveVersion()
                true
            }
            R.id.esv -> {
                BIBLE_VERSION = "ESV"
                saveVersion()
                true
            }
            R.id.kjv -> {
                BIBLE_VERSION = "KJV"
                saveVersion()
                true
            }
            R.id.msg -> {
                BIBLE_VERSION = "MSG"
                saveVersion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveVersion() {
        context!!.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .edit().putString(VERSION_KEY, BIBLE_VERSION).commit()
        listener!!.onTitleChanged()
    }

    private fun clearAll() {
        launchAlertClearAll {
            mAdapter!!.clear(context!!.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)!!)
            updateView()
        }
    }

    private fun markReadUntil() {
       launchAlert {
           val sf = context!!.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
           mAdapter!!.markReadUntil(sf, it)
           updateView()
       }
    }

    fun updateView() {
        mView!!.swapAdapter(mAdapter, false)
        mAdapter!!.notifyDataSetChanged()
    }

    private fun launchAlert(okListener: (Int) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Mark read until")
        builder.setMessage("Type the week to which you'd like to mark it as read. If you type 3, everything until week 3 will be marked as read.")
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.gravity = Gravity.CENTER
        builder.setView(input)

        builder.setPositiveButton("OK", { dialog, which ->
            okListener(input.text.toString().toInt())
        })
        builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
        builder.show()
    }


    private fun launchAlertClearAll(okListener: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Clearing Progress")
        builder.setMessage("Are you sure you want to clear everything? Click \"ok\" to proceed. Click \"Cancel\" to stop.")

        builder.setPositiveButton("OK", { dialog, which ->
            okListener.invoke()
        })
        builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
        builder.show()
    }
}
