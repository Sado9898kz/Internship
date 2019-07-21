package com.example.bbcnews.Model

import com.example.bbcnews.Controller.SearchModel
import com.example.bbcnews.MainActivity
import org.json.JSONArray
import java.io.InputStream
import java.io.InputStreamReader

val items = ArrayList<SearchModel>()
var jsonArr: JSONArray? = null
val country = arrayListOf<String>()
val coord = arrayListOf<String>()

object objKey

val key: ArrayList<String> = arrayListOf<String>()

object objLocalName

val localizedName = arrayListOf<String>()


fun Data(json: String) {

    val englishName = arrayListOf<String>()

    jsonArr = JSONArray(json)

    for (i in 0 until jsonArr!!.length()) {
        val jsonObj = jsonArr!!.getJSONObject(i)
        key.add(jsonObj.getString("Key"))
        localizedName.add(jsonObj.getString("LocalizedName"))
        englishName.add(jsonObj.getString("EnglishName"))
        country.add(jsonObj.getString("Country"))
        coord.add(jsonObj.getString("GeoPosition"))
    }

    localizedName.zip(country).forEachIndexed { i, (n, c) -> search(n, c, i) }
}

fun search(name: String, country: String, index: Int) {

    val newCountry = country.split("\"")

    items.add(SearchModel("$name, ${newCountry.get(7)}"))

}

