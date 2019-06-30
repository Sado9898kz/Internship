package com.example.bbcnews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.bbcnews.Model.LogicsSearchUrl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_search)

        val vEdit = findViewById<EditText>(R.id.act2_edit)
        val vBut = findViewById<Button>(R.id.act3_button)
        vBut.setOnClickListener {
            val i = Intent(this, LogicsSearchUrl::class.java)
            //val newStr=vEdit.text.toString()
            //val int=Intent()
            //int.putExtra("tag2",newStr)
            //setResult(0,int)
            startActivity(i)
        }
    }
}


