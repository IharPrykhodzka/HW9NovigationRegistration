package com.example.hw9navigationregistration

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.hw9navigationregistration.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var log: Int = Log.d(MY_LOG, "Start авторизации")
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isAuthenticated()) {
            startFeedActivity()
        } else {

            btn_login.setOnClickListener {
                case_password.error = null

                progressDialog = ProgressDialog(this@MainActivity)
                progressDialog!!.setTitle(getString(R.string.titlePD))
                progressDialog!!.setMessage(getString(R.string.massagePD))
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()

                launch {
                    val response = Repository.authenticate(
                        edit_login.text.toString(),
                        edit_password.text.toString()
                    )
                    log = Log.d(
                        MY_LOG, "${edit_login.text.toString()} and ${edit_password.text.toString()}"
                    )
                    if (response.isSuccessful) {
                        if (progressDialog != null) {
                            progressDialog!!.cancel()
                        }
                        log = Log.d(
                            MY_LOG, response.body().toString()
                        )
                        easyToastRes(this@MainActivity, R.string.success)
                        setUserAuth(response.body().toString())

                        startFeedActivity()
                    } else {
                        if (progressDialog != null) {
                            progressDialog!!.cancel()
                        }
                        case_password.error = getString(R.string.authentication_failed)
                    }
                }
            }

            btn_registration.setOnClickListener {
                val regIntent = Intent(this@MainActivity, RegistrationActivity::class.java)
                startActivity(regIntent)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        // Cancell authorization
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
        cancel()
    }

    override fun onStart() {
        super.onStart()
        if (isAuthenticated()) {
            startFeedActivity()
        }
    }

    private fun isAuthenticated() =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )?.isNotEmpty() ?: false

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .commit()

    private fun startFeedActivity() {
        val feedIntent = Intent(this@MainActivity, FeedActivity::class.java)
        startActivity(feedIntent)
        finish()
    }


}

