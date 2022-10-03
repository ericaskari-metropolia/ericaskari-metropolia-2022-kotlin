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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberDetailsBinding
import com.example.exercise_5.network.ImageApiClient
import com.example.exercise_5.ui.member.MemberViewModel
import com.example.exercise_5.ui.member.MemberViewModelFactory
import com.example.exercise_5.ui.membercomment.MemberCommentAdapter
import com.example.exercise_5.ui.membercomment.MemberCommentViewHolder
import com.example.exercise_5.ui.membercomment.MemberCommentViewModel
import com.example.exercise_5.ui.membercomment.MemberCommentViewModelFactory
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
class MemberDetailsFragment : Fragment(), NewGradeClickListener, NewCommentClickListener,
    MemberCommentViewHolder.Companion.OnMemberCommentClickListener {
    lateinit var binding: MemberDetailsBinding
    private val args: MemberDetailsFragmentArgs by navArgs()

    private val application by lazy { requireActivity().application as ExerciseApplication }

    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory(application.memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory(application.memberInfoRepository) }
    private val memberGradeViewModel: MemberGradeViewModel by viewModels { MemberGradeViewModelFactory(application.memberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewGradingViewModelFactory(application.memberGradeRepository) }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory(application.memberCommentRepository) }
    private val memberCommentViewModel: MemberCommentViewModel by viewModels { MemberCommentViewModelFactory(application.memberCommentRepository) }

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

        memberViewModel.findOneByHetekaId(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { member ->
            binding.member = member
        }

        memberCommentViewModel.loadAllByHetekaId(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { comments ->
            this.binding.commentSection.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.commentSection.listRecycleView.adapter = MemberCommentAdapter(comments, this)
        }

        memberInfoViewModel.getAll.distinctUntilChanged().observe(viewLifecycleOwner) { memberInfoList ->
            binding.memberInfo = memberInfoList.find { it.hetekaId == args.hetekaId }
        }

        memberGradeViewModel.getGradeValue(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { gradeValue ->
            binding.currentGrading.currentGrade.text = "$gradeValue"
        }

        memberGradeViewModel.getGradeCount(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { count ->
            @SuppressLint("SetTextI18n")
            binding.currentGrading.gradeCount.text = "($count)"
        }

        memberGradeViewModel.getRoundedGrade(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { rounded ->
            for (i in 0..4) {
                val resource = if ((i + 1) <= rounded) R.drawable.start_filled else R.drawable.star_empty
                currentGrading[i].setImageResource(resource)
            }
        }

        newGradeViewModel.currentGrade(username, args.hetekaId).observe(viewLifecycleOwner) { currentGrade ->
            if (currentGrade != null) {
                for (i in 0 until newGrading.count()) {
                    newGrading[i].setImageResource(if (i <= (currentGrade - 1)) R.drawable.start_filled else R.drawable.star_empty)
                }
            }
        }

        newGradeViewModel.isGraded(username, args.hetekaId).observe(viewLifecycleOwner) { isGraded ->
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
        newGradeViewModel.createNewGrade(username, args.hetekaId, index) {
            Toast.makeText(requireContext(), "Successfully graded!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateCommentButtonClicked(v: View?) {
        if (binding.newComment.commentText.text.isEmpty()) {
            return
        }

        newCommentViewModel.createNewComment(username, args.hetekaId, binding.newComment.commentText.text.toString()) {
            Toast.makeText(requireContext(), "Successfully commented!", Toast.LENGTH_SHORT).show()
            binding.newComment.commentText.setText("")
        }
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

    override fun onMemberCommentClick(v: View?, index: Number) {

    }
}
