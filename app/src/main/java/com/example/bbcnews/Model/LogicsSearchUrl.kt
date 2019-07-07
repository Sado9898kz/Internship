package com.example.bbcnews.Model

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.bbcnews.*
import com.example.bbcnews.Controller.*
import com.example.bbcnews.View.RecAdapter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList

class LogicsSearchUrl : Activity() {

    lateinit var vRecView: RecyclerView
    var request: Disposable? = null
    val day: String = "5day"
    val city: String = "2155957"
    val apiKey: String = "jGUV6V6BSzwPAp26Q8KeufouhhlBEhNU"
    val language: String = "ru-ru"
    val metric: String = "true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        //val city =intent.getStringExtra("tag1")
        //startActivityForResult(intent, 0)
        vRecView = findViewById<RecyclerView>(R.id.act1_recView)

        val vObject =
            createRequest("http://dataservice.accuweather.com/forecasts/v1/daily/${day}/${city}?apikey=${apiKey}&language=${language}&metric=${metric}")
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

            Log.e("test", "Good")

            Realm.getDefaultInstance().executeTransaction { realm ->

                val oldList = realm.where(FeedAccuweather::class.java).findAll()
                if (oldList.size > 0)
                    for (item in oldList)
                        item.deleteFromRealm()

                realm.copyToRealm(feed)

            }

            showRecView()

        }, {
            Log.e("test", "Error", it)
            showRecView()

        })
    }

 /*   override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)

        if (data != null) {
            val str = data.getStringExtra("tag2")

            city = str
        }
    }*/

    fun showRecView() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            val feed = realm.where(FeedAccuweather::class.java).findAll()
            if (feed.size > 0) {
                vRecView.adapter = RecAdapter(feed[0]!!.DailyForecasts)
                vRecView.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }
}