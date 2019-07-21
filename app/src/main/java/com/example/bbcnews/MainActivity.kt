package com.example.bbcnews

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.example.bbcnews.Controller.KeyGPS
import com.example.bbcnews.Controller.SearchModel
import com.example.bbcnews.Model.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.second_search.*
import java.io.InputStream

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    var GPS: Disposable? = null

    //Const
    val PERMISSON_REQUEST_CODE = 1001
    val PLAY_SERVICE_RESOLUTION_REQUEST = 1000

    //Variable
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_search)

        requestPermission()
        if (checkPlayService()) {
            buildGoogleApiClient()
        }

        btnSearch.setOnClickListener {
            SimpleSearchDialogCompat(this@MainActivity, "Поиск города", "Введите местоположение", null,
                    initData(), SearchResultListener { dialog, item, position ->
                sendKey(key[position], localizedName[position])
            }).show()
        }
    }

    fun funGPS(latitude: String, longitude: String) {
        val apiKey = "HVeWbVnn7UnefbVNGrRTZwAEVI2VmHzS"
        val language = "ru-ru"
        val lat = latitude
        val lon = longitude

        btnGPS.setOnClickListener {
            val o =
                    createRequest("http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=$apiKey&q=$lat%2C$lon&language=$language")
                            .map { Gson().fromJson(it, KeyGPS::class.java) }
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            GPS = o.subscribe({
                val sendKey = it?.Key
                val sendCity = it?.LocalizedName

                val intend = Intent(this, LogicsSearchUrl::class.java)
                intend.putExtra("Key", sendKey)
                intend.putExtra("City", sendCity)
                startActivity(intend)

                Log.d("M_test", "$it Connect GPS")
            }, {
                Log.d("M_test", "Error connect GPS", it)
            })
        }
    }

    fun initData(): ArrayList<SearchModel> {

        items.clear()
        val json: String?

        val inputStream: InputStream = assets.open("city.list.my2.json")
        json = inputStream.bufferedReader().use { it.readText() }

        Data(json)

        return items
    }


    fun sendKey(coordinate: String, city: String) {

        val intent = Intent(this, LogicsSearchUrl::class.java)
        intent.putExtra("City", city)
        intent.putExtra("Key", coordinate)
        startActivity(intent)
    }

    override fun onConnected(p0: Bundle?) {
        createLocationRequest()
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("M_ERROR", "Connection failed: " + p0.errorCode)
    }

    override fun onLocationChanged(p0: Location?) {
        funGPS(p0?.latitude.toString(),p0?.longitude.toString())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSON_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayService()) {
                        buildGoogleApiClient()
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                    arrayOf(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), PERMISSON_REQUEST_CODE
            )
        }
    }

    fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
    }

    fun checkPlayService(): Boolean {
        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RESOLUTION_REQUEST).show()
            } else {
                Toast.makeText(this.applicationContext, "Это устройство не поддерживается", Toast.LENGTH_SHORT).show()
                finish()
            }
            return false
        }
        return true
    }

    fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000 // 10 second
        mLocationRequest!!.fastestInterval = 5000 // 5 second
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null)
            mGoogleApiClient!!.connect()
    }

    override fun onResume() {
        super.onResume()
        checkPlayService()
    }

    override fun onDestroy() {
        mGoogleApiClient!!.disconnect()
        GPS?.dispose()
        super.onDestroy()
    }
}
