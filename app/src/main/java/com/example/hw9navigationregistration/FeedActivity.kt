package com.example.hw9navigationregistration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw9navigationregistration.utils.API_SHARED_FILE
import com.example.hw9navigationregistration.utils.AUTHENTICATED_SHARED_KEY
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        supportActionBar?.hide()

        topAppBar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.btn_logOut -> {
                    setUserAuth("")
                    val feedIntent = Intent(this@FeedActivity, MainActivity::class.java)
                    startActivity(feedIntent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .apply()
}