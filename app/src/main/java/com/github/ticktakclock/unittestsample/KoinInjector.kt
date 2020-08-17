package com.github.ticktakclock.unittestsample

import com.github.ticktakclock.unittestsample.domain.project.ProjectRepository
import com.github.ticktakclock.unittestsample.domain.user.UserRepository
import com.github.ticktakclock.unittestsample.services.GitHubService
import com.github.ticktakclock.unittestsample.services.repositories.ProjectRepositoryImpl
import com.github.ticktakclock.unittestsample.services.repositories.UserRepositoryImpl
import com.github.ticktakclock.unittestsample.views.main.ProjectListViewModel
import com.github.ticktakclock.unittestsample.views.user.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val myModule = module {
    single<GitHubService> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GitHubService::class.java)
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
    single<DispatcherProvider> { DispatcherProviderImpl() }
    viewModel { UserViewModel(get(), get()) }
    viewModel { ProjectListViewModel(get(), get()) }
}