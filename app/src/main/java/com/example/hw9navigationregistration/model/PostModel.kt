package com.example.hw9navigationregistration.model

data class PostModel(
    val id: Int,
    val author: String,
    val content: String = "",
    val created: Long = System.currentTimeMillis(),
    var likesCount: Int = 0,
    val repostCount: Int = 0,
    val shareCount: Int = 0,
    var likedByMe: MutableSet<Int> = mutableSetOf(),
    val repostByMe: MutableSet<Int> = mutableSetOf(),
    val sharedByMe: Boolean = false,
    val address: String? = null,
    val location: Location? = null,
    val video: Video? = null,
    val advertising: Advertising? = null,
    val source: PostModel? = null,
    val postType: PostType = PostType.SIMPLE_POST,
    val timesShown: Long = 0
) {

    var likeActionPerforming = false

    fun updateLikes(updatedModel: PostModel) {
        if (id != updatedModel.id) throw IllegalAccessException("Ids are different")
        likesCount = updatedModel.likesCount
        likedByMe = updatedModel.likedByMe
    }
}