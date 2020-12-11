package com.example.hw9navigationregistration

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw9navigationregistration.adapter.PostAdapter
import com.example.hw9navigationregistration.dto.PostResponseDto
import com.example.hw9navigationregistration.model.PostModel
import com.example.hw9navigationregistration.utils.API_SHARED_FILE
import com.example.hw9navigationregistration.utils.AUTHENTICATED_SHARED_KEY
import com.example.hw9navigationregistration.utils.MY_LOG
import com.example.hw9navigationregistration.utils.getUserId
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_standard_post.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.toast.toast

class FeedActivity : AppCompatActivity(), PostAdapter.OnLikeBtnClickListener {
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        rvItemPost.layoutManager = LinearLayoutManager(this)

        supportActionBar?.hide()

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_logOut -> {
                    setUserAuth("")
                    val feedIntent = Intent(this@FeedActivity, MainActivity::class.java)
                    startActivity(feedIntent)
                    finish()
                    true
                }
                else -> false
            }
        }


//        val progressDialog = ProgressDialog(this@FeedActivity).apply {
//            setTitle(getString(R.string.titlePD))
//            setMessage(getString(R.string.massagePD))
//            setCancelable(false)
//            show()
//        }
//
//
//
//        lifecycleScope.launch {
//            val result = Repository.getAllPosts()
//            if (result.isSuccessful) {
//                Toast.makeText(this@FeedActivity, result.toString(), Toast.LENGTH_SHORT).show()
//                progressDialog.dismiss()
//
//                val postResponseDtoList: List<PostResponseDto> = requireNotNull(result.body())
//                rvItemPost.adapter =
//                    PostAdapter(postResponseDtoList.map(PostResponseDto.Companion::toModel))
//
//            } else {
//                Toast.makeText(this@FeedActivity, result.toString(), Toast.LENGTH_SHORT).show()
//                progressDialog.dismiss()
//            }
//        }


    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {

            dialog = ProgressDialog(this@FeedActivity).apply {
                setMessage(this@FeedActivity.getString(R.string.massagePD))
                setTitle(R.string.titlePD)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            val result = Repository.getAllPosts()
            dialog?.dismiss()
            if (result.isSuccessful) {
                with(rvItemPost) {
                    layoutManager = LinearLayoutManager(this@FeedActivity)
                    val postResponseDtoList: List<PostResponseDto> = requireNotNull(result.body())
                    adapter = PostAdapter(
                        postResponseDtoList.map(PostResponseDto.Companion::toModel)
                    ).apply {
                        likeBtnClickListener = this@FeedActivity
                    }
                }
            } else {
                toast(R.string.error_download_posts)
            }
        }
    }

    override fun onLikeBtnClicked(item: PostModel, position: Int) {
        lifecycleScope.launch {
            item.likeActionPerforming = true
            with(rvItemPost) {
                adapter?.notifyItemChanged(position)

                val response = if (item.likedByMe.contains(context.getUserId())) {
                    Repository.cancelMyLike(item.id)

                } else {
                    Repository.likedByMe(item.id)
                }
                item.likeActionPerforming = false
                if (response.isSuccessful) {
                    Log.d(MY_LOG, response.toString())
                    item.updateLikes(response.body()!!)
                }
                adapter?.notifyItemChanged(position)
            }
        }
    }


    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .apply()
}
