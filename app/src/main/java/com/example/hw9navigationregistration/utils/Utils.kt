package com.example.hw9navigationregistration.utils

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

fun Context.getUserId(): Int = this.getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getInt(
    AUTHENTICATED_ID, 0)

fun isValid(password: String) = Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()

fun Context.toast(res: Int) = Toast.makeText(this, this.resources.getString(res), Toast.LENGTH_SHORT).show()