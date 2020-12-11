package com.example.hw9navigationregistration.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(res: Int) = Toast.makeText(this, this.resources.getString(res), Toast.LENGTH_SHORT).show()