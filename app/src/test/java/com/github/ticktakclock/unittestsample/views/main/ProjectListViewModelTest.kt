package com.github.ticktakclock.unittestsample.views.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.ticktakclock.unittestsample.CoroutinesTestRule
import com.github.ticktakclock.unittestsample.TestDispatcherProvider
import com.github.ticktakclock.unittestsample.domain.project.Project
import com.github.ticktakclock.unittestsample.domain.project.ProjectRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
@Suppress("TestFunctionName", "NonAsciiCharacters")
class ProjectListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule(testDispatcher)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private fun test(block: suspend TestCoroutineScope.() -> Unit) =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            block()
        }

    @Test
    fun onResume_repositoryのAPIが叩ける() = test {
        val repository = mockk<ProjectRepository>()
        coEvery { repository.getProjects(any()) } returns emptyList()

        val viewModel = ProjectListViewModel(repository, testDispatcherProvider)
        viewModel.onResume()

        coVerify(exactly = 1) { repository.getProjects(any()) }
    }

    @Test
    fun onResume_projectsが更新される() = test {
        val repository = mockk<ProjectRepository>()
        coEvery { repository.getProjects(any()) } returns emptyList()

        val observer = mockk<Observer<List<Project>>>(relaxed = true)
        val viewModel = ProjectListViewModel(repository, testDispatcherProvider)
        viewModel.projects.observeForever(observer)

        viewModel.onResume()

        verify {
            observer.onChanged(emptyList())
        }
    }
}