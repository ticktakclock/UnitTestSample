package com.github.ticktakclock.unittestsample.services.repositories

import com.github.ticktakclock.unittestsample.domain.user.User
import com.github.ticktakclock.unittestsample.domain.user.UserRepository
import com.github.ticktakclock.unittestsample.services.GitHubService


class UserRepositoryImpl(private val gitHubService: GitHubService) : UserRepository {

    override suspend fun getUser(userId: String): User =
        gitHubService.getUser(userId).transform()

}