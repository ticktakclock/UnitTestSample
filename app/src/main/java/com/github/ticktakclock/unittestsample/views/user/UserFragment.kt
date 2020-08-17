package com.github.ticktakclock.unittestsample.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ticktakclock.unittestsample.R
import com.github.ticktakclock.unittestsample.databinding.FragmentUserBinding
import org.koin.android.ext.android.inject

class UserFragment : Fragment() {
    companion object {
        fun newInstance(userId: String) = UserFragment().apply {
            arguments = Bundle().apply {
                putString("userId", userId)
            }
        }
    }

    private val viewModel: UserViewModel by inject()
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        val userId = arguments?.getString("userId")
        userId?.let {
            viewModel.fetch(it)
                .observe(viewLifecycleOwner, Observer { user ->
                    binding.user = user
                })
        }

        viewModel.userImage.observe(viewLifecycleOwner, Observer { drawable ->
            binding.userImage = drawable
        })
    }
}