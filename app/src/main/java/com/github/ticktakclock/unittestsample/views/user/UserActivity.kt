package com.github.ticktakclock.unittestsample.views.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ticktakclock.unittestsample.R

class UserActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, userId: String) =
            Intent(context, UserActivity::class.java).apply {
                putExtra("userId", userId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val userId = intent?.getStringExtra("userId")
        userId?.let {
            moveToUser(it)
        }

    }

    private fun moveToUser(userName: String) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        val fragment = UserFragment.newInstance(userName)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}