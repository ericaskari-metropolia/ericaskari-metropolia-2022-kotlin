package com.example.exercise_5.fragments

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
import com.example.exercise_5.ui.components.*
import com.example.exercise_5.ui.member.MemberViewModel
import com.example.exercise_5.ui.member.MemberViewModelFactory
import com.example.exercise_5.ui.membergrade.MemberGradeViewModel
import com.example.exercise_5.ui.membergrade.MemberGradeViewModelFactory
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModel
import com.example.exercise_5.ui.memberinfo.MemberInfoViewModelFactory
import java.math.RoundingMode

class MemberDetailsFragment : Fragment(), NewRateClickListener, NewCommentClickListener {
    lateinit var binding: MemberDetailsBinding
    private val args: MemberDetailsFragmentArgs by navArgs()

    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory((requireActivity().application as ExerciseApplication).memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).memberInfoRepository) }
    private val gradeViewModel: MemberGradeViewModel by viewModels { MemberGradeViewModelFactory((requireActivity().application as ExerciseApplication).memberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewRatingViewModelFactory() }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemberDetailsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newRate.clickListener = this
        binding.newComment.clickListener = this

        memberViewModel.getAll.observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }

        memberInfoViewModel.getAll.observe(viewLifecycleOwner) { memberInfos ->
            binding.memberInfo = memberInfos[args.userId]
        }

        newCommentViewModel.commentValue.observe(viewLifecycleOwner) { commentValue ->
            if (binding.newComment.commentText.text.toString() != commentValue) {
                binding.newComment.commentText.setText(commentValue)
            }
        }

        gradeViewModel.getAll.observe(viewLifecycleOwner) { allRatings ->
            val all = allRatings.filter { it.hetekaId == args.userId }.map { it.grade }
            val sum = all.sum()
            val average: Float = if (all.isEmpty()) 0F else (sum.toFloat() / all.count())

            val stars = listOf(
                binding.currentRating.rateOne,
                binding.currentRating.rateTwo,
                binding.currentRating.rateThree,
                binding.currentRating.rateFour,
                binding.currentRating.rateFive,
            )
            val rounded = average.toBigDecimal().setScale(0, RoundingMode.HALF_UP).toInt()

            println("average: $average")
            println("rounded: $rounded")

            for (i in 0..4) {
                stars[i].setImageResource(
                    if ((i + 1) <= rounded) R.drawable.start_filled else R.drawable.star_empty
                )
            }
        }

        newGradeViewModel.ratingValue.observe(viewLifecycleOwner) { ratingValue ->
            val stars = listOf(
                binding.newRate.rateOne,
                binding.newRate.rateTwo,
                binding.newRate.rateThree,
                binding.newRate.rateFour,
                binding.newRate.rateFive,
            )

            for (i in 0 until stars.count()) {
                stars[i].setImageResource(if (i <= (ratingValue - 1)) R.drawable.start_filled else R.drawable.star_empty)
            }
        }

    }

    override fun onRateButtonClick(v: View?, index: Int) {
        newGradeViewModel.updateRatingValue(index)
        gradeViewModel.createNewRating(args.userId, index)
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
