package com.example.bbcnews.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.bbcnews.Controller.WeatherItemAPI
import com.example.bbcnews.R

class Adapter(val items: ArrayList<WeatherItemAPI>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent!!.context)

        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        return view

    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}

