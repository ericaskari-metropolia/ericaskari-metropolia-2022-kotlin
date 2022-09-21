package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.ParliamentMemberDetailsBinding
import com.example.exercise_5.ui.components.RateAndCommentComponent
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberViewModel
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModel
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModelFactory
import com.example.exercise_5.ui.parliamentMemberRating.ParliamentMemberRatingViewModel
import com.example.exercise_5.ui.parliamentMemberRating.ParliamentMemberRatingViewModelFactory

class ParliamentMemberDetailsFragment : Fragment(), RateAndCommentComponent {
    lateinit var binding: ParliamentMemberDetailsBinding

    private val args: ParliamentMemberDetailsFragmentArgs by navArgs()

    override var commentValue: String = ""
    override var ratingValue: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ParliamentMemberDetailsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commentAndRate.clickHandlers = this

        memberViewModel().getAll.observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }
        memberInfoViewModel().getAll.observe(viewLifecycleOwner) { memberInfos ->
            binding.memberInfo = memberInfos[args.userId]
        }

    }

    private fun memberViewModel(): ParliamentMemberViewModel {
        val viewModel: ParliamentMemberViewModel by viewModels { ParliamentMembersViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRepository) }
        return viewModel
    }

    private fun memberInfoViewModel(): ParliamentMemberInfoViewModel {
        val viewModel: ParliamentMemberInfoViewModel by viewModels { ParliamentMemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberInfoRepository) }
        return viewModel
    }

    private fun ratingViewModel(): ParliamentMemberRatingViewModel {
        val viewModel: ParliamentMemberRatingViewModel by viewModels { ParliamentMemberRatingViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRatingRepository) }
        return viewModel
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadDetailsPageImage")
        fun loadDetailsPageImage(view: ImageView, profileImage: String?) {
            Glide.with(view.context)
                .load("https://avoindata.eduskunta.fi/$profileImage")
                .placeholder(R.drawable.white)
                .fallback(R.drawable.user)
                .error(R.drawable.user)
                .into(view)
        }
    }

    override fun onRateButtonClick(v: View?, index: Int) {
        if (index < 0 || index > 4) {
            return

        }

        val stars = listOf(
            binding.commentAndRate.rateOne,
            binding.commentAndRate.rateTwo,
            binding.commentAndRate.rateThree,
            binding.commentAndRate.rateFour,
            binding.commentAndRate.rateFive,
        )

        for (i in 0..4) {
            stars[i].setImageResource(if (i <= index) R.drawable.start_filled else R.drawable.star_empty)
        }
    }

    override fun onCreateRatingButtonClick(v: View?) {
        println("onCreateRatingButtonClick")
//        val stars = listOf(
//            binding.commentAndRate.rateOne,
//            binding.commentAndRate.rateTwo,
//            binding.commentAndRate.rateThree,
//            binding.commentAndRate.rateFour,
//            binding.commentAndRate.rateFive,
//        ).filter { it.drawable.constantState == R.drawable.start_filled }
//
//        ratingViewModel().createNewRating(args.userId)

    }
}
