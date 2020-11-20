package com.example.hw9navigationregistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw9navigationregistration.utils.easyToastRes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            case_password.error = null

            launch {
                val response = Repository.authenticate(
                    edit_login.text.toString(),
                    edit_password.text.toString()
                )
                if (response.isSuccessful) {
                    easyToastRes(this@MainActivity, R.string.success)
                    val feedIntent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(feedIntent)
                    finish()
                } else {
                    case_password.error = getString(R.string.authentication_failed)
                }
            }
        }

        btn_registration.setOnClickListener {
            val regIntent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(regIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        // Cancell authorization
        cancel()
    }


}