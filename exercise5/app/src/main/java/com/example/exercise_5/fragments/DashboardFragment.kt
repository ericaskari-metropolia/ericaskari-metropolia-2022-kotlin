package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise_5.databinding.DashboardBinding

class DashboardFragment : Fragment() {
    lateinit var binding: DashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DashboardBinding.inflate(inflater, container, false)
        binding.viewParties.setOnClickListener { this.onViewPartiesButtonClick(it) }
        binding.viewAllMembers.setOnClickListener { this.onViewAllMembersButtonClick(it) }
        return binding.root;
    }

    private fun onViewPartiesButtonClick(v0: View?) {
        val action = DashboardFragmentDirections.toPartiesPageFragmentAction()
        findNavController().navigate(action)
    }

    private fun onViewAllMembersButtonClick(v0: View?) {
        val action = DashboardFragmentDirections.toParliamentMemberListFragmentAction()
        findNavController().navigate(action)
    }
}
