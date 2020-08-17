package com.github.ticktakclock.unittestsample.services.repositories

import com.github.ticktakclock.unittestsample.domain.project.Project
import com.github.ticktakclock.unittestsample.domain.project.ProjectRepository
import com.github.ticktakclock.unittestsample.services.GitHubService


class ProjectRepositoryImpl(private val gitHubService: GitHubService) : ProjectRepository {

    override suspend fun getProjects(userId: String): List<Project> =
        gitHubService.getProjects(userId)
            .map { it.transform() }
}