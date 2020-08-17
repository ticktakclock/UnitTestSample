package com.github.ticktakclock.unittestsample.services

import com.github.ticktakclock.unittestsample.services.models.Project
import com.github.ticktakclock.unittestsample.services.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("/users/{userId}")
    suspend fun getUser(@Path(value = "userId") userId: String): User

    @GET("/users/{userId}/repos")
    suspend fun getProjects(@Path(value = "userId") userId: String): List<Project>
}