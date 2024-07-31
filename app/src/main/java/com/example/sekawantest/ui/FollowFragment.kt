package com.example.sekawantest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sekawantest.data.response.ItemsItem
import com.example.sekawantest.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private var position = 0
    private var username = ""
    private lateinit var bindingFollow: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreate(savedInstanceState)
        bindingFollow = FragmentFollowBinding.inflate(inflater, container, false)
        bindingFollow.rvFoll.layoutManager = LinearLayoutManager(requireActivity())
        return bindingFollow.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        mainViewModel.followers.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }

        mainViewModel.following.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }

        if (position == 1) {
            mainViewModel.showFollower(username)
        } else {
            mainViewModel.showFollowing(username)
        }

    }

    private fun setUserData(user: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        bindingFollow.rvFoll.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingFollow.progressBar.visibility = View.VISIBLE
        } else {
            bindingFollow.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"

    }
}





