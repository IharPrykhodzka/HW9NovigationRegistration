package com.example.hw9navigationregistration.repository


import com.example.hw9navigationregistration.api.Token
import com.example.hw9navigationregistration.model.PostModel
import com.example.hw9navigationregistration.model.User
import retrofit2.Response

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Int): PostModel?
    suspend fun save(item: PostModel): PostModel
    suspend fun deleteById(id: Int)
    fun createRetrofitWithAuth(token: String, userAuth: User)
    suspend fun authenticate(login: String, password: String): Token
    suspend fun register(login: String, password: String): Response<Token>
    suspend fun getUserAuth(): User
    suspend fun repost(repostedId: Int, content: String): PostModel
    suspend fun getPostsCreatedBefore(idCurPost: Int, countUploadedPosts: Int): List<PostModel>
    suspend fun getPostsAfter(idFirstPost: Int): List<PostModel>
    suspend fun getRecentPosts(countPosts: Int): List<PostModel>
}