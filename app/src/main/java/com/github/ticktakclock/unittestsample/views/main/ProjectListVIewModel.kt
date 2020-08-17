package com.github.ticktakclock.unittestsample.views.main

import androidx.lifecycle.*
import com.github.ticktakclock.unittestsample.DispatcherProvider
import com.github.ticktakclock.unittestsample.domain.project.Project
import com.github.ticktakclock.unittestsample.domain.project.ProjectRepository
import kotlinx.coroutines.launch

class ProjectListViewModel(
    private val repository: ProjectRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), LifecycleObserver {
    private val _projects: MutableLiveData<List<Project>> = MutableLiveData()
    val projects: LiveData<List<Project>>
        get() = _projects

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        viewModelScope.launch(dispatcherProvider.io()) {
            try {
                val response = repository.getProjects("ticktakclock")
                _projects.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _projects.value = emptyList()
            }
        }
    }
}