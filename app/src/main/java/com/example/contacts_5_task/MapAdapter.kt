package com.example.contacts_5_task

import android.widget.BaseAdapter
import android.widget.TextView

import android.R
import android.os.Parcel
import android.os.Parcelable

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import com.example.contacts_5_task.model.ContactModel

import java.util.ArrayList
import java.util.HashMap


class MapAdapter() : BaseAdapter(), Parcelable {
    private var mData: ArrayList<*>? = null

    constructor(parcel: Parcel) : this() {

    }

    constructor(map: Map<String?, String?>) : this() {
        fun MyAdapter(map: Map<String?, String?>) {
            mData = ArrayList<Any?>()
            (mData as ArrayList<Any?>).addAll(map.entries)
        }
    }

    constructor(contactMap: HashMap<Long, ContactModel>) : this(){

    }


    override fun getCount(): Int {
        return mData!!.size
    }

    override fun getItem(position: Int): Map.Entry<*, *> {
        return mData!![position] as Map.Entry<*, *>
    }

    override fun getItemId(position: Int): Long {
        // TODO implement you own logic with ID
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val result: View
        if (convertView == null) {
            result =
                LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item, parent, false)
        } else {
            result = convertView
        }
        val item = getItem(position)

        (result.findViewById<View>(R.id.text1) as TextView).text = item.key as CharSequence?
        (result.findViewById<View>(R.id.text2) as TextView).text = item.value as CharSequence?
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapAdapter> {
        override fun createFromParcel(parcel: Parcel): MapAdapter {
            return MapAdapter(parcel)
        }

        override fun newArray(size: Int): Array<MapAdapter?> {
            return arrayOfNulls(size)
        }
    }
}