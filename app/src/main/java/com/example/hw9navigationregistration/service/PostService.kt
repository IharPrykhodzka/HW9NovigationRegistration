package com.example.hw9navigationregistration.service

import com.example.hw9navigationregistration.App
import com.example.hw9navigationregistration.model.PostModel


class PostService {
    private val repo = App.postRepository

    suspend fun likeById(id: Int): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            likesCount = oldPost.likesCount.inc(),
            likedByMe = true, //TODO добавляем авторство пользователя и проверку лайкал ли автор
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }

    suspend fun dislikeById(id: Int): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            likesCount = oldPost.likesCount.dec(),
            likedByMe = false,
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }
}