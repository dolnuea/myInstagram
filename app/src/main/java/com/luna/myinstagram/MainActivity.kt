package com.luna.myinstagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// let user create a post by taking a photo with their camera
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}