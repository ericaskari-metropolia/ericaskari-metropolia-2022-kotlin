package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise_5.utilities.InjectorUtils
import com.example.exercise_5.adapters.ParliamentMembersAdapter
import com.example.exercise_5.databinding.ParliamentMemberListBinding
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.ui.parliamentMember.OnParliamentMemberClickListener
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModel

class ParliamentMemberListFragment : Fragment(), OnParliamentMemberClickListener {
    private lateinit var binding: ParliamentMemberListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ParliamentMemberListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel().getParliamentMembers().observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.listRecycleView.setHasFixedSize(true)
            this.binding.listRecycleView.adapter = ParliamentMembersAdapter(members, this)
        }

        ParliamentMembersData.members.forEach {
            viewModel().addParliamentMember(it)
        }
    }

    override fun onParliamentMemberClick(v: View?, index: Number) {
        val action =
            ParliamentMemberListFragmentDirections.toParliamentMemberDetailsFragmentAction(index.toInt())
        findNavController().navigate(action)
    }

    private fun viewModel(): ParliamentMembersViewModel {
        val factory = InjectorUtils.provideParliamentMembersViewModelFactory()
        // Get a reference to the ViewModel scoped to this Fragment
        val viewModel: ParliamentMembersViewModel by viewModels { factory }
        return viewModel
    }
}
