package com.example.hw9navigationregistration.dto

import com.example.hw9navigationregistration.model.*

data class PostRequestDto(
    val id: Int,
    val author: String,
    val content: String,
    val created: Long,
    val likesCount: Int,
    val repostCount: Int,
    val shareCount: Int,
    val likedByMe: MutableSet<Int>,
    val repostByMe: MutableSet<Int>,
    val sharedByMe: Boolean,
    val address: String?,
    val location: Location?,
    val video: Video?,
    val advertising: Advertising?,
    val source: PostModel?,
    val postType: PostType,
    val timesShown: Long
) {
    companion object {
        fun toModel(dto: PostRequestDto) = PostModel(
            id = dto.id,
            author = dto.author,
            content = dto.content,
            created = dto.created,
            likesCount = dto.likesCount,
            repostCount = dto.repostCount,
            shareCount = dto.shareCount,
            likedByMe = dto.likedByMe,
            repostByMe = dto.repostByMe,
            sharedByMe = dto.sharedByMe,
            address = dto.address,
            location = dto.location,
            video = dto.video,
            advertising = dto.advertising,
            source = dto.source,
            postType = dto.postType,
            timesShown = dto.timesShown
        )

        fun fromModel(model: PostModel) = PostRequestDto(
            id = model.id,
            author = model.author,
            content = model.content,
            created = model.created,
            likesCount = model.likesCount,
            repostCount = model.repostCount,
            shareCount = model.shareCount,
            likedByMe = model.likedByMe,
            repostByMe = model.repostByMe,
            sharedByMe = model.sharedByMe,
            address = model.address,
            location = model.location,
            video = model.video,
            advertising = model.advertising,
            source = model.source,
            postType = model.postType,
            timesShown = model.timesShown,
        )
    }
}
