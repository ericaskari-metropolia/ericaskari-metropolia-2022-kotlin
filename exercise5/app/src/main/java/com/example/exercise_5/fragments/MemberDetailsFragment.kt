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
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberDetailsBinding
import com.example.exercise_5.network.ImageApiClient
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
import com.example.exercise_5.ui.newgrade.NewGradingViewModelFactory

/**
 * @author Mohammad Askari
 */
class MemberDetailsFragment : Fragment(), NewGradeClickListener, NewCommentClickListener {
    lateinit var binding: MemberDetailsBinding
    private val args: MemberDetailsFragmentArgs by navArgs()

    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory((requireActivity().application as ExerciseApplication).memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).memberInfoRepository) }
    private val memberGradeViewModel: MemberGradeViewModel by viewModels { MemberGradeViewModelFactory((requireActivity().application as ExerciseApplication).memberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewGradingViewModelFactory((requireActivity().application as ExerciseApplication).memberGradeRepository) }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory() }

    private val username by lazy { (requireActivity().application as ExerciseApplication).username() }

    private val currentGrading by lazy {
        listOf(
            binding.currentGrading.rateOne,
            binding.currentGrading.rateTwo,
            binding.currentGrading.rateThree,
            binding.currentGrading.rateFour,
            binding.currentGrading.rateFive,
        )
    }

    private val newGrading by lazy {
        listOf(
            binding.newGrading.rateOne,
            binding.newGrading.rateTwo,
            binding.newGrading.rateThree,
            binding.newGrading.rateFour,
            binding.newGrading.rateFive,
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemberDetailsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newGrading.clickListener = this
        binding.newComment.clickListener = this
        binding.editGrade.setOnClickListener { this.onEditGradeButtonClick(it) }

        memberViewModel.getAll.distinctUntilChanged().observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }

        memberInfoViewModel.getAll.distinctUntilChanged().observe(viewLifecycleOwner) { memberInfoList ->
            binding.memberInfo = memberInfoList[args.userId]
        }

        newCommentViewModel.commentValue.distinctUntilChanged().observe(viewLifecycleOwner) { commentValue ->
            if (binding.newComment.commentText.text.toString() != commentValue) {
                binding.newComment.commentText.setText(commentValue)
            }
        }

        memberGradeViewModel.getGradeValue(args.userId).distinctUntilChanged().observe(viewLifecycleOwner) { gradeValue ->
            binding.currentGrading.currentGrade.text = "$gradeValue"
        }

        memberGradeViewModel.getGradeCount(args.userId).distinctUntilChanged().observe(viewLifecycleOwner) { count ->
            @SuppressLint("SetTextI18n")
            binding.currentGrading.gradeCount.text = "($count)"
        }

        memberGradeViewModel.getRoundedGrade(args.userId).distinctUntilChanged().observe(viewLifecycleOwner) { rounded ->
            for (i in 0..4) {
                val resource = if ((i + 1) <= rounded) R.drawable.start_filled else R.drawable.star_empty
                currentGrading[i].setImageResource(resource)
            }
        }

        newGradeViewModel.currentGrade(username, args.userId).observe(viewLifecycleOwner) { currentGrade ->
            if (currentGrade != null) {
                for (i in 0 until newGrading.count()) {
                    newGrading[i].setImageResource(if (i <= (currentGrade - 1)) R.drawable.start_filled else R.drawable.star_empty)
                }
            }
        }

        newGradeViewModel.isGraded(username, args.userId).observe(viewLifecycleOwner) { isGraded ->
            binding.editGrade.visibility = if (isGraded) View.VISIBLE else View.INVISIBLE
            for (imageButton in newGrading) {
                imageButton.isEnabled = !isGraded
                imageButton.alpha = if (isGraded) 0.8f else 1f
            }
        }

    }

    private fun onEditGradeButtonClick(v: View?) {
        binding.editGrade.visibility = View.INVISIBLE
        for (imageButton in newGrading) {
            imageButton.isEnabled = true
            imageButton.alpha = 1f

        }
    }

    override fun onGradeButtonClick(v: View?, index: Int) {
        newGradeViewModel.createNewGrade(username, args.userId, index) {
            Toast.makeText(requireContext(), "Successfully graded!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCommentValueTextChange(value: String) {
        newCommentViewModel.updateCommentValue(value)
    }

    override fun onCreateCommentButtonClicked(v: View?) {
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadDetailsPageImage")
        fun loadDetailsPageImage(view: ImageView, imageId: String?) {
            Glide.with(view.context)
                .load(ImageApiClient.imageUrlBuilder(imageId))
                .placeholder(R.drawable.white)
                .fallback(R.drawable.user)
                .error(R.drawable.user)
                .into(view)
        }
    }
}
