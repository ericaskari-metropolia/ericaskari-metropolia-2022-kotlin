package com.example.exercise_5.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberDetailsBinding
import com.example.exercise_5.ui.member.MemberViewModel
import com.example.exercise_5.ui.member.MemberViewModelFactory
import com.example.exercise_5.ui.membergrade.MemberGradeViewModel
import com.example.exercise_5.ui.membergrade.MemberGradeViewModelFactory
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory
import com.example.exercise_5.ui.newcomment.NewCommentClickListener
import com.example.exercise_5.ui.newcomment.NewCommentViewModel
import com.example.exercise_5.ui.newcomment.NewCommentViewModelFactory
import com.example.exercise_5.ui.newgrade.NewGradeViewModel
import com.example.exercise_5.ui.newgrade.NewGradeClickListener
import com.example.exercise_5.ui.newgrade.NewRatingViewModelFactory

class MemberDetailsFragment : Fragment(), NewGradeClickListener, NewCommentClickListener {
    lateinit var binding: MemberDetailsBinding
    private val args: MemberDetailsFragmentArgs by navArgs()

    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory((requireActivity().application as ExerciseApplication).memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).memberInfoRepository) }
    private val memberGradeViewModel: MemberGradeViewModel by viewModels { MemberGradeViewModelFactory((requireActivity().application as ExerciseApplication).memberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewRatingViewModelFactory() }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemberDetailsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  View bindings
        binding.newRating.clickListener = this
        binding.newComment.clickListener = this

        memberViewModel.getAll.observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }

        memberInfoViewModel.getAll.observe(viewLifecycleOwner) { memberInfoList ->
            binding.memberInfo = memberInfoList[args.userId]
        }

        newCommentViewModel.commentValue.observe(viewLifecycleOwner) { commentValue ->
            if (binding.newComment.commentText.text.toString() != commentValue) {
                binding.newComment.commentText.setText(commentValue)
            }
        }

        val currentRating = listOf(
            binding.currentRating.rateOne,
            binding.currentRating.rateTwo,
            binding.currentRating.rateThree,
            binding.currentRating.rateFour,
            binding.currentRating.rateFive,
        )

        memberGradeViewModel.getGradeValue(args.userId).observe(viewLifecycleOwner) { gradeValue ->
            binding.currentRating.currentGrade.text = "$gradeValue"
        }

        memberGradeViewModel.getGradeCount(args.userId).observe(viewLifecycleOwner) { count ->
            @SuppressLint("SetTextI18n")
            binding.currentRating.gradeCount.text = "($count)"
        }

        memberGradeViewModel.getRoundedGrade(args.userId).observe(viewLifecycleOwner) { rounded ->
            for (i in 0..4) {
                val resource = if ((i + 1) <= rounded) R.drawable.start_filled else R.drawable.star_empty
                currentRating[i].setImageResource(resource)
            }
        }

        val newRating = listOf(
            binding.newRating.rateOne,
            binding.newRating.rateTwo,
            binding.newRating.rateThree,
            binding.newRating.rateFour,
            binding.newRating.rateFive,
        )

        newGradeViewModel.gradingValue.observe(viewLifecycleOwner) { gradeValue ->
            for (i in 0 until newRating.count()) {
                newRating[i].setImageResource(if (i <= (gradeValue - 1)) R.drawable.start_filled else R.drawable.star_empty)
            }
        }
    }

    override fun onRateButtonClick(v: View?, index: Int) {
        newGradeViewModel.updateGradingValue(index)
        memberGradeViewModel.createNewRating(args.userId, index)
        Toast.makeText(requireContext(), "Graded!", Toast.LENGTH_SHORT).show()
    }

    override fun onCommentValueTextChange(value: String) {
        newCommentViewModel.updateCommentValue(value)
    }

    override fun onCreateCommentButtonClicked(v: View?) {
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
}
