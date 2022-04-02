package com.luna.myinstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luna.myinstagram.fragments.ComposeFragment
import com.luna.myinstagram.fragments.FeedFragment
import com.luna.myinstagram.fragments.ProfileFragment
import com.parse.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null

            when(item.itemId) {
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                    Toast.makeText(this, "Home", LENGTH_SHORT).show()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                    Toast.makeText(this, "Profile", LENGTH_SHORT).show()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                    Toast.makeText(this, "Compose", LENGTH_SHORT).show()
                }
            }

            if(fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home
    }

    // action toolbar stuff
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            ParseUser.logOut()
            Toast.makeText(this, "Successfully logged out", LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,  LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
    companion object {
        const val TAG = "MainActivity"
    }
}