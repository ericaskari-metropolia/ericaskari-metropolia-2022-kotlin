package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise_5.databinding.HomePageBinding

class HomePageFragment : Fragment() {
    lateinit var binding: HomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(inflater, container, false)
        binding.viewParties.setOnClickListener { this.onViewPartiesButtonClick(it) }
        binding.viewAllMembers.setOnClickListener { this.onViewAllMembersButtonClick(it) }
        return binding.root;
    }

    private fun onViewPartiesButtonClick(v0: View?) {
        val action = HomePageFragmentDirections.toPartiesPageFragmentAction()
        findNavController().navigate(action)
    }

    private fun onViewAllMembersButtonClick(v0: View?) {
        val action = HomePageFragmentDirections.toParliamentMemberListFragmentAction()
        findNavController().navigate(action)
    }


}
