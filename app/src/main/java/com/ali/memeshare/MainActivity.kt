package com.ali.memeshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private val url="https://meme-api.com/gimme"
     lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById<ImageView>(R.id.imageView)

            LoadImageFromApi();
    }

    private fun LoadImageFromApi() {
// get api
        // volley
        val queue=Volley.newRequestQueue(this);

        val request = JsonObjectRequest(Request.Method.GET,this.url,null, { response->
            Log.d("Result", response.toString())

            Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loader).into(imageView)


                    },
            {
                Log.d("Error", it.toString())
                Toast.makeText(applicationContext, "Error in loading meme from server", Toast.LENGTH_SHORT).show()

            }
            )
        queue.add(request);
    }

    fun changeimage(view: View) {
        this.LoadImageFromApi()
    }

    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey.. check out this amazing meme $url" )
     val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
}