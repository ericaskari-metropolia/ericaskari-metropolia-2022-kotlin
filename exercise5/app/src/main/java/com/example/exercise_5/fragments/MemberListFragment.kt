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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.ui.member.MemberViewHolder
import com.example.exercise_5.ui.member.MembersAdapter
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberListBinding
import com.example.exercise_5.databinding.MemberListItemBinding
import com.example.exercise_5.ui.member.MemberViewModel
import com.example.exercise_5.ui.member.MemberViewModelFactory
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory

class MemberListPageFragment : Fragment(), MemberViewHolder.Companion.OnParliamentMemberClickListener {
    private lateinit var binding: MemberListBinding
    private val args: MemberListPageFragmentArgs by navArgs()

    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory((requireActivity().application as ExerciseApplication).memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).memberInfoRepository) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemberListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memberViewModel.getAll.observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.listRecycleView.adapter = MembersAdapter(members.filter { it.party == args.partyName }, this)
        }

        memberViewModel.populate()
        memberInfoViewModel.populate()
    }

    override fun onParliamentMemberClick(v: View?, index: Number) {
        val action = MemberListPageFragmentDirections.toParliamentMemberDetailsFragmentAction(index.toInt())
        findNavController().navigate(action)
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
