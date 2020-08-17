package com.github.ticktakclock.unittestsample.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ticktakclock.unittestsample.R
import com.github.ticktakclock.unittestsample.databinding.FragmentRepoListBinding
import com.github.ticktakclock.unittestsample.domain.project.Project
import org.koin.android.ext.android.inject

class ProjectListFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectListFragment()
    }

    private val viewModel: ProjectListViewModel by inject()
    private lateinit var binding: FragmentRepoListBinding

    private var _fragmentInteractionListener: FragmentInteractionListener? = null

    var fragmentInteractionListener: FragmentInteractionListener?
        get() = _fragmentInteractionListener
        set(value) {
            _fragmentInteractionListener = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val adapter = object : ProjectsAdapter(emptyList()) {
            override fun onProjectClicked(project: Project) {
                super.onProjectClicked(project)
                val userName = project.owner?.login
                if (userName != null) {
                    fragmentInteractionListener?.moveToUser(userName)
                }
            }
        }

        binding.recyclerView.adapter = adapter
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        viewModel.projects.observe(this, Observer {
            if (it.isEmpty()) {
                Toast.makeText(activity, "データがありません", Toast.LENGTH_LONG).show()
            }
            val adapter = binding.recyclerView.adapter as? ProjectsAdapter
            adapter?.update(it)
        })
    }

    interface FragmentInteractionListener {
        fun moveToUser(userName: String)
    }
}