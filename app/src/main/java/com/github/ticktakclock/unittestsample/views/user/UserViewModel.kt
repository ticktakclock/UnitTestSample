package com.github.ticktakclock.unittestsample.views.user

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.api.get
import com.github.ticktakclock.unittestsample.DispatcherProvider
import com.github.ticktakclock.unittestsample.domain.user.User
import com.github.ticktakclock.unittestsample.domain.user.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user

    private var _userImage: MutableLiveData<Drawable> = MutableLiveData()
    val userImage: LiveData<Drawable>
        get() = _userImage

    fun fetch(userId: String): LiveData<User> {
        viewModelScope.launch(dispatcherProvider.io()) {
            try {
                val user = userRepository.getUser(userId)
                _user.value = user
                imageResource(user.avatar_url)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return user
    }

    suspend fun imageResource(url: String?) {
        url ?: return
        val drawable = Coil.get(url)
        _userImage.value = drawable
    }
}