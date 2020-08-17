package com.github.ticktakclock.unittestsample.views.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.ticktakclock.unittestsample.CoroutinesTestRule
import com.github.ticktakclock.unittestsample.TestDispatcherProvider
import com.github.ticktakclock.unittestsample.domain.user.User
import com.github.ticktakclock.unittestsample.domain.user.UserRepository
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@Suppress("TestFunctionName", "NonAsciiCharacters")
@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {
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
    fun fetch_userが更新される() = test {
        val user = mockk<User>(relaxed = true)
        val repository = mockk<UserRepository>()
        coEvery { repository.getUser(any()) } returns user

        val observer = mockk<Observer<User>>(relaxed = true)
        val viewModel = UserViewModel(repository, testDispatcherProvider)
        viewModel.user.observeForever(observer)

        viewModel.fetch("ticktakclock")

        verify(exactly = 1) {
            observer.onChanged(user)
        }
    }

    @Test
    fun fetch_repositoryのAPIが叩ける() {
        val user = mockk<User>(relaxed = true)
        val repository = mockk<UserRepository>()
        coEvery { repository.getUser(any()) } returns user

        val viewModel = UserViewModel(repository, testDispatcherProvider)

        viewModel.fetch("")

        coVerify(exactly = 1) { repository.getUser(any()) }
    }
}