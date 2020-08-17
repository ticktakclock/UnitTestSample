package com.github.ticktakclock.unittestsample.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ticktakclock.unittestsample.R
import com.github.ticktakclock.unittestsample.views.user.UserActivity

class MainActivity : AppCompatActivity(), ProjectListFragment.FragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToProjects()
    }

    override fun moveToUser(userName: String) {
        startActivity(UserActivity.createIntent(this, userName))
    }

    private fun moveToProjects() {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        val fragment = ProjectListFragment.newInstance()
        fragment.fragmentInteractionListener = this
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}