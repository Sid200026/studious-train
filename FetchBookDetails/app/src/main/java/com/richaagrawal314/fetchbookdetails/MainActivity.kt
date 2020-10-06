package com.richaagrawal314.fetchbookdetails

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val mTAG = "MainActivity"
    private lateinit var mbookinput: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Log.i(TAG, "on create called :")
        mbookinput = findViewById(R.id.txtBookInput)
        button = findViewById(R.id.btnfetch)


        button.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("bookName", mbookinput.text.toString())
            this.startActivity(intent)
        }

    }
}