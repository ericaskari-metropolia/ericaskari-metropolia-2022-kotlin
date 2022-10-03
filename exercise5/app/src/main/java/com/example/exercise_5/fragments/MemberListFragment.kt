package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.adapters.MemberViewHolder
import com.example.exercise_5.adapters.MembersAdapter
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberListBinding
import com.example.exercise_5.databinding.MemberListItemBinding
import com.example.exercise_5.ui.member.MemberViewModel
import com.example.exercise_5.ui.member.MemberViewModelFactory
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory

class MemberListPageFragment : Fragment(), MemberViewHolder.Companion.OnParliamentMemberClickListener {
    private lateinit var binding: MemberListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MemberListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parliamentMemberViewModel().getAll.observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
//            this.binding.listRecycleView.setHasFixedSize(true)
            this.binding.listRecycleView.adapter = MembersAdapter(members, this)
        }

        parliamentMemberViewModel().populate()
        parliamentMemberInfoViewModel().populate()
    }

    override fun onParliamentMemberClick(v: View?, index: Number) {
        val action = MemberListPageFragmentDirections.toParliamentMemberDetailsFragmentAction(index.toInt())
        findNavController().navigate(action)
    }

    private fun parliamentMemberViewModel(): MemberViewModel {
        val viewModel: MemberViewModel by viewModels { MemberViewModelFactory((requireActivity().application as ExerciseApplication).memberRepository) }
        return viewModel
    }

    private fun parliamentMemberInfoViewModel(): MemberInfoViewModel {
        val viewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).memberInfoRepository) }
        return viewModel
    }
}

class MemberListItemFragment() : Fragment() {
    private lateinit var binding: MemberListItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = MemberListItemBinding.inflate(inflater, container, false)
        return binding.root;
    }


    companion object {
        @JvmStatic
        @BindingAdapter("loadListItemImage")
        fun loadListItemImage(view: ImageView, profileImage: String?) {
            Glide.with(view.context)
                .load("https://avoindata.eduskunta.fi/$profileImage")
                .override(300, 300)
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.white)
                .fallback(R.drawable.user)
                .error(R.drawable.user)
                .into(view)
        }
    }
}
