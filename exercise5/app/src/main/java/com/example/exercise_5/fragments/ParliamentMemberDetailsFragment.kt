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
import com.example.exercise_5.databinding.ParliamentMemberDetailsBinding
import com.example.exercise_5.ui.components.*
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberViewModel
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory
import com.example.exercise_5.ui.parliamentMemberGrade.ParliamentMemberGradeViewModel
import com.example.exercise_5.ui.parliamentMemberGrade.ParliamentMemberGradeViewModelFactory
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModel
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModelFactory
import java.math.RoundingMode

class ParliamentMemberDetailsFragment : Fragment(), NewRateClickListener, NewCommentClickListener {
    lateinit var binding: ParliamentMemberDetailsBinding
    private val args: ParliamentMemberDetailsFragmentArgs by navArgs()

    private val memberViewModel: ParliamentMemberViewModel by viewModels { ParliamentMembersViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRepository) }
    private val memberInfoViewModel: ParliamentMemberInfoViewModel by viewModels { ParliamentMemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberInfoRepository) }
    private val gradeViewModel: ParliamentMemberGradeViewModel by viewModels { ParliamentMemberGradeViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewRatingViewModelFactory() }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory() }


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

        // Connecting interfaces to view
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
        //  TODO: Check if User already rated and update the current value if it exists.
        newGradeViewModel.updateRatingValue(index)
        gradeViewModel.createNewRating(args.userId, index)
        Toast.makeText(requireContext(), "Graded!", (22).toInt()).show()
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
