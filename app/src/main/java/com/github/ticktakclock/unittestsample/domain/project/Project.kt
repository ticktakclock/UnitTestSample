package com.github.ticktakclock.unittestsample.domain.project

import com.github.ticktakclock.unittestsample.domain.user.User


data class Project(
    val name: String = "",
    val owner: User? = null,
    val git_url: String? = null,
    val language: String? = null
)