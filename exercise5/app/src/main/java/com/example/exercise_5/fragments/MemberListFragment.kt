package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberListBinding
import com.example.exercise_5.databinding.MemberListItemBinding
import com.example.exercise_5.network.ImageApiClient
import com.example.exercise_5.ui.member.*
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory

/**
 * @author Mohammad Askari
 */
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

        memberViewModel.getAllByPartyName(args.partyName).distinctUntilChanged().observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.listRecycleView.adapter = MembersAdapter(members, this)
        }

        memberViewModel.populate()
        memberInfoViewModel.populate()
    }

    override fun onParliamentMemberClick(v: View?, member: Member) {
        val action = MemberListPageFragmentDirections.toParliamentMemberDetailsFragmentAction(member.hetekaId)
        findNavController().navigate(action)
    }
}

class MemberListItemFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MemberListItemBinding.inflate(inflater, container, false).root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadListItemImage")
        fun loadListItemImage(view: ImageView, imageId: String?) {
            Glide.with(view.context)
                .load(ImageApiClient.imageUrlBuilder(imageId))
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
