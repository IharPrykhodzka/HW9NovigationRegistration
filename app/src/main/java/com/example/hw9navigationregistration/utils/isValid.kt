package com.example.hw9navigationregistration.utils

import java.util.regex.Pattern

fun isValid(password: String) = Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()