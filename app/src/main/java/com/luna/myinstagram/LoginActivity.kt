package com.luna.myinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check if user is logged in
        //if user is logged in take them to main activity

        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        val user = ParseUser()
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                //user succesfully created new acc

                //todo navigate user to main activity
                //todo or show a toast to indicated signed up for acc
            } else {
                //todo show toast to indicate error signing up
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                // Hooray!  The user is logged in.
                Log.i(TAG, "Successfully logged in as a user")
                goToMainActivity()
            } else {
                // Signup failed.  Look at the ParseException to see what happened.
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity,  MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}