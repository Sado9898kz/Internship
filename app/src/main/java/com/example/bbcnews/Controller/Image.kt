package com.example.bbcnews.Controller

import android.widget.ImageView
import com.example.bbcnews.R

fun ImageView.format(value: Int){

 when(value){
        1-> setImageResource(R.drawable.sunny_1)
        2-> setImageResource(R.drawable.mostly_sunny_2)
        3-> setImageResource(R.drawable.partly_sunny_3)
        4-> setImageResource(R.drawable.intermittent_clouds_4)
        5-> setImageResource(R.drawable.hazy_sunshine_5)
        6-> setImageResource(R.drawable.mostly_cloudy_6)
        7-> setImageResource(R.drawable.cloudy_7)
        8-> setImageResource(R.drawable.dreary__overcast__8)
        11-> setImageResource(R.drawable.fog_11)
        12-> setImageResource(R.drawable.showers_12)
        13-> setImageResource(R.drawable.mostly_cloudy_showers_13)
        14-> setImageResource(R.drawable.partly_sunny_showers_14)
        15-> setImageResource(R.drawable.t_storms_15)
        16-> setImageResource(R.drawable.mostly_cloudy_t_storms_16)
        17-> setImageResource(R.drawable.partly_sunny_t_storms_17)
        18-> setImageResource(R.drawable.rain_18)
        19-> setImageResource(R.drawable.flurries_19)
        20-> setImageResource(R.drawable.mostly_cloudy_flurries_20)
        21-> setImageResource(R.drawable.partly_sunny_flurries_21)
        22-> setImageResource(R.drawable.snow_22)
        23-> setImageResource(R.drawable.mostly_cloudy_snow_23)
        24-> setImageResource(R.drawable.ice_24)
        25-> setImageResource(R.drawable.sleet_25)
        26-> setImageResource(R.drawable.freezing_rain_26)
        29-> setImageResource(R.drawable.rain_and_snow_29)
        30-> setImageResource(R.drawable.hot_30)
        31-> setImageResource(R.drawable.cold_31)
        32-> setImageResource(R.drawable.windy_32)
        33-> setImageResource(R.drawable.clear_33)
        34-> setImageResource(R.drawable.mostly_clear_34)
        35-> setImageResource(R.drawable.partly_cloudy_35)
        36-> setImageResource(R.drawable.intermittent_clouds_36)
        37-> setImageResource(R.drawable.hazy_moonlight_37)
        38-> setImageResource(R.drawable.mostly_cloudy_38)
        39-> setImageResource(R.drawable.partly_cloudy_showers_39)
        40-> setImageResource(R.drawable.mostly_cloudy_showers_40)
        41-> setImageResource(R.drawable.partly_cloudy_t_storms_41)
        42-> setImageResource(R.drawable.mostly_cloudy_t_storms_42)
        43-> setImageResource(R.drawable.mostly_cloudy_flurries_43)
        44-> setImageResource(R.drawable.mostly_cloudy_snow_44)
    }
}