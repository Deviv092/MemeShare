package com.example.memeshare


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
   private var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val imageView = findViewById<ImageView>(R.id.memeImageView)

//        val queue = Volley.newRequestQueue(this)  >>implemented using singleton class
        val url = "https://meme-api.herokuapp.com/gimme#"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            currentImageUrl = response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                 progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                 progressBar.visibility = View.GONE
                    return false
                }

            }).into(imageView)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
        })
     MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
//         queue.add(jsonObjectRequest)  >>implemented using singleton class
    }

    fun shareMeme(view: View) {
      val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this cool meme I got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
      loadMeme()
    }
}