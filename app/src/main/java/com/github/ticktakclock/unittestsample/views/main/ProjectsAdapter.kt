package com.github.ticktakclock.unittestsample.views.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ticktakclock.unittestsample.R
import com.github.ticktakclock.unittestsample.databinding.ItemProjectBinding
import com.github.ticktakclock.unittestsample.domain.project.Project

open class ProjectsAdapter(private var projects: List<Project>) :
    RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemProjectBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_project, parent, false)
        return ProjectsViewHolder(binding)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        if (position >= projects.size) return
        val project = projects[position]
        holder.bind(project)
        holder.item.setOnClickListener {
            onProjectClicked(project)
        }
    }

    open fun onProjectClicked(project: Project) {

    }

    fun update(items: List<Project>) {
        projects = items
        notifyDataSetChanged()
    }

    class ProjectsViewHolder(private val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val item = binding.item

        fun bind(item: Project) {
            binding.project = item
        }
    }
}