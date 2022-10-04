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
import com.example.exercise_5.databinding.MemberDetailsBinding
import com.example.exercise_5.databinding.MemberListBinding
import com.example.exercise_5.databinding.MemberListItemBinding
import com.example.exercise_5.network.ImageApiClient
import com.example.exercise_5.ui.member.*
import com.example.exercise_5.ui.membercomment.MemberCommentViewModel
import com.example.exercise_5.ui.membercomment.MemberCommentViewModelFactory
import com.example.exercise_5.ui.membergrade.MemberGradeViewModel
import com.example.exercise_5.ui.membergrade.MemberGradeViewModelFactory
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory
import com.example.exercise_5.ui.newcomment.NewCommentViewModel
import com.example.exercise_5.ui.newcomment.NewCommentViewModelFactory
import com.example.exercise_5.ui.newgrade.NewGradeViewModel
import com.example.exercise_5.ui.newgrade.NewGradingViewModelFactory

/**
 * @author Mohammad Askari
 * List of members page
 * Party can be selected by passing the hetekaId from args while navigating.
 */
class MemberListPageFragment : Fragment(), MemberViewHolder.Companion.OnParliamentMemberClickListener {
    //  ViewBinding
    lateinit var binding: MemberListBinding

    //  Navigation Arguments
    private val args: MemberListPageFragmentArgs by navArgs()

    //  Application
    private val application by lazy { requireActivity().application as ExerciseApplication }

    //  ViewModels
    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory(application.memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory(application.memberInfoRepository) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemberListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Listen to arg party members and update the adapter
        memberViewModel.getAllByPartyName(args.partyName).distinctUntilChanged().observe(viewLifecycleOwner) { members ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.listRecycleView.adapter = MembersAdapter(members, this)
        }

        //  Get necessary data.
        memberViewModel.populate()
        memberInfoViewModel.populate()
    }

    override fun onParliamentMemberClick(v: View?, member: Member) {
        val action = MemberListPageFragmentDirections.toParliamentMemberDetailsFragmentAction(member.hetekaId)
        findNavController().navigate(action)
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
