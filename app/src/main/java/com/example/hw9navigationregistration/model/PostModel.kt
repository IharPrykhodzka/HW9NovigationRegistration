package com.example.hw9navigationregistration.model

data class PostModel(
    val id: Int,
    val author: String,
    val content: String = "",
    val created: Long = System.currentTimeMillis(),
    var likesCount: Int = 0,
    val commentsCount: Int = 0,
    val shareCount: Int = 0,
    var likedByMe: Boolean = false,
    val commentedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val address: String? = null,
    val location: Location? = null,
    val video: Video? = null,
    val advertising: Advertising? = null,
    val source: PostModel? = null,
    val postType: PostType = PostType.SIMPLE_POST,
    val timesShown: Long = 0
) {
    // Добавляем флаг, который показывает выполняется ли
// операция на данный момент
    var likeActionPerforming = false
    // Обновление лайков с модели, полученной по сети
    fun updateLikes(updatedModel: PostModel) {
// Если айди не совпадают, значит что-то пошло не так
        if (id != updatedModel.id)
            throw IllegalAccessException("Ids are different")
        likesCount = updatedModel.likesCount
        likedByMe = updatedModel.likedByMe
    }
}