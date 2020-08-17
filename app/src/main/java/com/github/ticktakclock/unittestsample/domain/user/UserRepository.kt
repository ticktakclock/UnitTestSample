package com.github.ticktakclock.unittestsample.domain.user

interface UserRepository {
    suspend fun getUser(userId: String): User
}