package com.example.bbcnews.Model

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.example.bbcnews.*
import com.example.bbcnews.Controller.*
import com.example.bbcnews.View.RecAdapter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.recycler_view.*
import kotlinx.android.synthetic.main.recycler_view.view.*
import kotlinx.android.synthetic.main.recycler_view.view.tv_day_temperature

class LogicsSearchUrl : Activity() {

    lateinit var vRecView: RecyclerView

    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        val day = "5day"
        val apiKey = "HVeWbVnn7UnefbVNGrRTZwAEVI2VmHzS"
        val language = "ru-ru"
        val metric = "true"
        val city = intent.extras?.get("Key").toString()

        tv_city.text = intent.extras?.get("City").toString()
        vRecView = findViewById(R.id.act1_recView)

        val vObject =
                createRequest("http://dataservice.accuweather.com/forecasts/v1/daily/$day/$city?apikey=$apiKey&language=$language&metric=$metric")
                        .map { Gson().fromJson(it, FeedAccuweatherAPI::class.java) }
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = vObject.subscribe({
            val feed = FeedAccuweather(
                    it.DailyForecasts.mapTo(
                            RealmList<WeatherItem>(),
                            { feed ->
                                WeatherItem(
                                        feed.Date,
                                        feed.Temperature.Minimum.Value,
                                        feed.Temperature.Maximum.Value,
                                        feed.Day.Icon,
                                        feed.Night.Icon,
                                        feed.Day.IconPhrase,
                                        feed.Night.IconPhrase,
                                        feed.MobileLink
                                )
                            }
                    )
            )
            Log.d("M_LogicsSearchUrl", "It is work")

            Realm.getDefaultInstance().executeTransaction { realm ->
                val oldList = realm.where(FeedAccuweather::class.java).findAll()
                if (oldList.size > 0)
                    for (item in oldList)
                        item.deleteFromRealm()
                realm.copyToRealm(feed)

            }

            showRecView()

            tv_day_temperature.text = it.DailyForecasts.elementAt(0).Temperature.Maximum.Value
            tv_day_huge.text = it.DailyForecasts.elementAt(0).Day.IconPhrase
        }, {

            Log.e("M_LogicsSearchUrl", "Error", it)
            showRecView()
        })
    }

    fun showRecView() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            val feed = realm.where(FeedAccuweather::class.java).findAll()
            tv_day_temperature.text = feed[0]?.DailyForecasts.toString()
            if (feed.size > 0) {
                vRecView.adapter = RecAdapter(feed[0]!!.DailyForecasts)
                vRecView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                vRecView.itemAnimator = DefaultItemAnimator()
            }
        }
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }
}