package com.example.hw9navigationregistration

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hw9navigationregistration.api.Token
import com.example.hw9navigationregistration.utils.API_SHARED_FILE
import com.example.hw9navigationregistration.utils.AUTHENTICATED_SHARED_KEY
import com.example.hw9navigationregistration.utils.easyToastRes
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        supportActionBar?.hide()

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
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

        val progressDialog = ProgressDialog(this@FeedActivity).apply {
            setTitle(getString(R.string.titlePD))
            setMessage(getString(R.string.massagePD))
            setCancelable(false)
            show()
        }

        lifecycleScope.launch {
            val result = Repository.getAllPosts()
            if (result.isSuccessful) {
                Toast.makeText(this@FeedActivity, result.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

            } else {
                Toast.makeText(this@FeedActivity, result.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }

    }


    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .apply()
}