package com.example.hw9navigationregistration.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hw9navigationregistration.R
import com.example.hw9navigationregistration.model.PostModel
import com.example.hw9navigationregistration.utils.getUserId
import kotlinx.android.synthetic.main.item_standard_post.view.*
import splitties.toast.toast


class PostAdapter(val list: List<PostModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var likeBtnClickListener: OnLikeBtnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_standard_post, parent, false)
        return PostViewHolder(this, view)
    }


    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.v("LOG", "position is $position")
        with(holder as PostViewHolder) {
            bind(list[position])
        }
    }


    interface OnLikeBtnClickListener {
        fun onLikeBtnClicked(item: PostModel, position: Int)
    }
}


class PostViewHolder(private val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
    init {

        with(itemView) {
            btnLike.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[currentPosition]

                    if (item.likeActionPerforming) {
                        context.toast(context.getString(R.string.like_in_progress))
                    } else {
                        adapter.likeBtnClickListener?.onLikeBtnClicked(item, currentPosition)
                    }
                }
            }


//            btnShare.setOnClickListener {
//                val currentPosition = adapterPosition
//                if (currentPosition != RecyclerView.NO_POSITION) {
//                    val item = adapter.list[currentPosition]
//
//                    if (item.sharedByMe) {
//                        val intent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(
//                                Intent.EXTRA_TEXT,
//                                """${item.author}
//                                   ${item.content}
//                                """.trimIndent()
//                            )
//                            type = "text/plain"
//                        }
//                        val context = author.context
//                        context.startActivity(intent)
//
//                    } else {
//                        context.toast(context.getString(R.string.like_in_progress))
//                    }
//                }
//            }
        }
    }


    fun bind(post: PostModel) {

        with(itemView) {
            date.text = publishedAgoInMillisToTimeInWords(post.created)
            author.text = post.author
            text.text = post.content

//            when (post.likedByMe) {
//                true -> btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24)
//                false -> btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
//            }

            if (post.likesCount == 0) {
                forBtnLike.visibility = View.GONE
            } else {
                forBtnLike.visibility = View.VISIBLE
                forBtnLike.text = post.likesCount.toString()
            }


//            when (post.commentedByMe) {
//                true -> btnComment.setBackgroundResource(R.drawable.ic_baseline_mode_comment_red_24)
//                false -> btnComment.setBackgroundResource(R.drawable.ic_baseline_mode_comment_24)
//            }
//
//            if (post.commentsCount == 0) {
//                forBtnComment.visibility = View.GONE
//            } else {
//                forBtnComment.visibility = View.VISIBLE
//                forBtnComment.text = post.commentsCount.toString()
//            }
//
//            when (post.sharedByMe) {
//                true -> btnShare.setBackgroundResource(R.drawable.ic_baseline_share_red_24)
//                false -> btnShare.setBackgroundResource(R.drawable.ic_baseline_share_24)
//            }
//
//            if (post.shareCount == 0) {
//                forBtnShare.visibility = View.GONE
//            } else {
//                forBtnShare.visibility = View.VISIBLE
//                forBtnShare.text = post.shareCount.toString()
//            }

            when {
                post.likeActionPerforming -> {
                    btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_blue_24)
                    forBtnLike.setTextColor(ContextCompat.getColor(context, R.color.blue))
                }
                post.likedByMe.contains(context.getUserId()) -> {
                    btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24)
                    forBtnLike.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                else -> {
                    btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    forBtnLike.setTextColor(ContextCompat.getColor(context, R.color.grey))

                }
            }
        }
    }
}

private fun publishedAgoInMillisToTimeInWords(publishedAgo: Long): String {
    return when (publishedAgo / 1000) {
        in 0..30 -> "менее минуты назад"
        in 31..90 -> "минуту назад"
        in 91..360 -> "6 минут назад"
        in 361..3600 -> "час назад"
        in 3601..7200 -> "2 часа назад"
        in 7201..86_400 -> "несколько часов назад"
        in 86_401..172_800 -> "день назад"
        in 172_801..604_800 -> "несколько дней назад"
        in 604_801..1_209_600 -> "неделю назад"
        in 1_209_601..2_678_400 -> "несколько недель назад"
        in 2_678_401..5_356_800 -> "месяц назад"
        in 5_356_801..32_140_800 -> "несколько месяцев назад"
        in 32_140_801..64_281_600 -> "год назад"
        else -> "несколько лет назад"
    }
}
