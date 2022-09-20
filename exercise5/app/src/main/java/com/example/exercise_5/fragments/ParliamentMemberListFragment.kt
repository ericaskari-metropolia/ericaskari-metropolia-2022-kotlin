package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise_5.adapters.ParliamentMembersAdapter
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.ParliamentMemberListBinding
import com.example.exercise_5.ui.parliamentMember.OnParliamentMemberClickListener
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberViewModel
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModel
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModelFactory

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

        parliamentMemberViewModel().getAll.observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
//            this.binding.listRecycleView.setHasFixedSize(true)
            this.binding.listRecycleView.adapter = ParliamentMembersAdapter(members, this)
        }

        parliamentMemberViewModel().populate()
        parliamentMemberInfoViewModel().populate()
    }

    override fun onParliamentMemberClick(v: View?, index: Number) {
        val action =
            ParliamentMemberListFragmentDirections.toParliamentMemberDetailsFragmentAction(index.toInt())
        findNavController().navigate(action)
    }

    private fun parliamentMemberViewModel(): ParliamentMemberViewModel {
        val viewModel: ParliamentMemberViewModel by viewModels { ParliamentMembersViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRepository) }
        return viewModel
    }

    private fun parliamentMemberInfoViewModel(): ParliamentMemberInfoViewModel {
        val viewModel: ParliamentMemberInfoViewModel by viewModels { ParliamentMemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberInfoRepository) }
        return viewModel
    }
}
