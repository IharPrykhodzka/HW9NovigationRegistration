package com.example.hw9navigationregistration

import android.app.Application
import com.example.hw9navigationregistration.repository.PostRepository
import com.example.hw9navigationregistration.repository.PostRepositoryNetworkImpl

class App : Application() {
    companion object {
        lateinit var postRepository: PostRepository
    }

    override fun onCreate() {
        super.onCreate()

        postRepository = PostRepositoryNetworkImpl()
    }

}
