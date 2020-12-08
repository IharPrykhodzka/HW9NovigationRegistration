package com.example.hw9navigationregistration.viewModel

import com.example.hw9navigationregistration.model.PostModel


sealed class UiState {

    data class Update(val posts: List<PostModel>) : UiState()

    object EmptyProgress : UiState()

    object InternetAccessError : UiState()

    object AuthError : UiState()

    data class Success(val posts: List<PostModel>) : UiState()
}