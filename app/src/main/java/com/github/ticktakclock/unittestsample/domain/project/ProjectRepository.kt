package com.github.ticktakclock.unittestsample.domain.project

interface ProjectRepository {
    suspend fun getProjects(userId: String): List<Project>
}