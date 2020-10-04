package com.richaagrawal314.fetchbookdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var mtitle: TextView
    private lateinit var mauthor: TextView
    private lateinit var mdescription: TextView
    private lateinit var mprice: TextView
    private lateinit var imgBook: ImageView
    private lateinit var progressLayout: RelativeLayout
    private var mbookinput: String? = "SampleText"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mtitle = findViewById(R.id.txttitle)
        mauthor = findViewById(R.id.txtauthor)
        mprice = findViewById((R.id.txtPrice))
        mdescription = findViewById(R.id.txtdescription)
        imgBook = findViewById(R.id.imgDetailbookcover)
        progressLayout = findViewById(R.id.progressLayout)


        if (intent != null) {
            mbookinput = intent.getStringExtra("bookName")
        } else {
            finish()
            Toast.makeText(this@DetailActivity, "Some Error Occurred", Toast.LENGTH_SHORT)
                .show()
        }

        if (mbookinput == "SampleText") {
            finish()
            Toast.makeText(this@DetailActivity, "Some Error Occurred", Toast.LENGTH_SHORT)
                .show()
        }

        progressLayout.visibility = View.VISIBLE
        val queryString = mbookinput
        var imgurl = ""

        //Log.i("TAG", "query String $queryString")
        val queue = Volley.newRequestQueue(this@DetailActivity)
        val url =
            "https://www.googleapis.com/books/v1/volumes?q={$queryString}&maxResults=1"


        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                Log.i("TAG", "response is: $it")
                // val jsonObject = JSONObject(it)
                val itemArray = it.getJSONArray("items")
                progressLayout.visibility = View.GONE
                for (i in 0 until itemArray.length()) {
                    val book = itemArray.getJSONObject(i)
                    val volumeInfo = book.getJSONObject("volumeInfo")
                    try {
                        mtitle.text = volumeInfo.getString("title")

                        if (volumeInfo.has("authors")) {
                            val authorArray = volumeInfo.getJSONArray("authors")
                            mauthor.text = authorArray[0].toString()
                        } else
                            mauthor.text = "Author Not Known"

                        if (volumeInfo.has("description"))
                            mdescription.text = volumeInfo.getString("description")
                        else
                            mdescription.text = "Description Not Found"

                        val listPrice = book.getJSONObject("saleInfo")
                        mprice.text = if (listPrice.getString("saleability") == "FOR_SALE") {
                            listPrice.getJSONObject("listPrice").getDouble("amount").toString() +
                                    listPrice.getJSONObject("listPrice").getString("currencyCode")
                        } else
                            "Price Not Available"

                        imgurl = volumeInfo.getJSONObject("imageLinks")
                            .getString("thumbnail")


//                        Log.i("TAG", "title is: ${mtitle.text}")
//                        Log.i("TAG", "author is: ${mauthor.text}")
//                        Log.i("TAG", "description is: ${mdescription.text}")
//                        Log.i("TAG", "imgurl is: $imgurl")

                    } catch (e: Exception) {
                        Log.e("TAG", "exception is: ${e.message}")
                        // e.printStackTrace()
                    }
                    Picasso.get()
                        .load(imgurl).placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(imgBook, object : Callback {
                            override fun onSuccess() {
                                Log.i("TAG", "on success callback")
                            }

                            override fun onError(e: Exception) {
                                Log.e("TAG", "onError callback ${e.message}")
                            }
                        })
                }

            },
            {
                Log.i("TAG", "error is : $it")
            })


        queue.add(jsonRequest)

    }


}