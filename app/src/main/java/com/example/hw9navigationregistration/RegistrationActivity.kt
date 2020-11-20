package com.example.hw9navigationregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw9navigationregistration.utils.easyToastRes
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btn_confirm_reg.setOnClickListener {
            if (edit_password_reg.text.toString() == edit_password_reg_repeat.text.toString()) {
                case_password_reg_repeat.error = null

                launch {
                    val response = Repository.registration(
                        edit_login_reg.text.toString(),
                        edit_password_reg.text.toString()
                    )
                    if (response.isSuccessful) {
                        easyToastRes(this@RegistrationActivity, R.string.good_reg)
                        finish()
                    } else {
                        case_password_reg_repeat.error = getString(R.string.registration_failed)
                    }
                }
            } else {
                case_password_reg_repeat.error = getString(R.string.error_password_not_same)
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        cancel()
    }
}