package com.example.hw9navigationregistration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.hw9navigationregistration.model.PostModel
import com.example.hw9navigationregistration.repository.PostRepository
import com.example.hw9navigationregistration.repository.PostRepositoryNetworkImpl
import com.example.hw9navigationregistration.utils.API_SHARED_FILE
import com.example.hw9navigationregistration.utils.AUTHENTICATED_SHARED_KEY
import com.example.hw9navigationregistration.utils.easyToastRes
import com.example.hw9navigationregistration.viewModel.FeedViewModel
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.Timer
import kotlin.concurrent.schedule

class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedViewModel by viewModels()


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



        viewModel.loadData()
//        Toast.makeText(this, getAll(), Toast.LENGTH_SHORT).show()
//        Log.d("LOG", ss.toString())


    }


    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .apply()
}