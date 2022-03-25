package com.luna.myinstagram

import android.app.Application
import com.parse.Parse;
import com.parse.ParseObject

class ParstagramApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Register your parse models
        ParseObject.registerSubclass(Post::class.java)
        // Add your initialization code here
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}