package com.example.memeshare


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        val imageView = findViewById<ImageView>(R.id.memeImageView)

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme#"

        val jsonObjectRequest = JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, { response ->

            val url = response.getString("url")
            Glide.with(this).load(url).into(imageView)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
        })

         queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

    }
    fun nextMeme(view: View) {

    }
}