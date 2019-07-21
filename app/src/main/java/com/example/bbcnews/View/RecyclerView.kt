package com.example.bbcnews.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bbcnews.Controller.*
import com.example.bbcnews.R
import io.realm.RealmList
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.recycler_view.view.*
import java.util.*

class RecAdapter(val items: RealmList<WeatherItem>) : RecyclerView.Adapter<RecHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecHolder {

        val inflater = LayoutInflater.from(p0!!.context)
        val view = inflater.inflate(R.layout.list_item, p0, false)

        return RecHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p: RecHolder, p1: Int) {

        val item = items[p1]!!
        p?.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}

class RecHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(item: WeatherItem) {

        val begin = Date().time(18, 0)
        val end = Date().time(6, 0)

        itemView.tv_date.text = "День: ${if (item.Date < Date().add(1, TimeUnits.DAY)) {
            item.Date.humanizeDate()
        } else item.Date.format()}"
        itemView.tv_temperature.text = "Температура    ${item.ValMax}C/${item.ValMin}C"
        itemView.tv_morning.text = "Утром: ${item.DayIconPhrase}"
        itemView.tv_evening.text = "Вечером: ${item.NightIconPhrase}"

        itemView.tv_browser.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(item.MobileLink)
            itemView.tv_browser.context.startActivity(i)
        }

        if (Date() > begin || Date() < end) {
            itemView.iv_image.format(item.IconNight)
        } else {
            itemView.iv_image.format(item.IconDay)
        }
    }
}

