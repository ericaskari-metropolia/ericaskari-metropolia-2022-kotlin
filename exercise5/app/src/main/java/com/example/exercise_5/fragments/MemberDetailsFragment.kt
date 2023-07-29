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
    //  ViewBinding
    lateinit var binding: MemberDetailsBinding

    //  Navigation Arguments
    private val args: MemberDetailsFragmentArgs by navArgs()

    //  Application
    private val application by lazy { requireActivity().application as ExerciseApplication }

    //  ViewModels
    private val memberViewModel: MemberViewModel by viewModels { MemberViewModelFactory(application.memberRepository) }
    private val memberInfoViewModel: MemberInfoViewModel by viewModels { MemberInfoViewModelFactory(application.memberInfoRepository) }
    private val memberGradeViewModel: MemberGradeViewModel by viewModels { MemberGradeViewModelFactory(application.memberGradeRepository) }
    private val newGradeViewModel: NewGradeViewModel by viewModels { NewGradingViewModelFactory(application.memberGradeRepository) }
    private val newCommentViewModel: NewCommentViewModel by viewModels { NewCommentViewModelFactory(application.memberCommentRepository) }
    private val memberCommentViewModel: MemberCommentViewModel by viewModels { MemberCommentViewModelFactory(application.memberCommentRepository) }

    //  LoggedIn Username
    private val username by lazy { (requireActivity().application as ExerciseApplication).username() }

    //  Current Grade starts view references
    private val currentGrading by lazy {
        listOf(
            binding.currentGrading.rateOne,
            binding.currentGrading.rateTwo,
            binding.currentGrading.rateThree,
            binding.currentGrading.rateFour,
            binding.currentGrading.rateFive,
        )
    }

    //  New Grade starts view references
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

        //  Initializing ClickListeners
        binding.newGrading.clickListener = this
        binding.newComment.clickListener = this
        binding.editGrade.setOnClickListener { this.onEditGradeButtonClick(it) }

        //  Listen to member changes
        memberViewModel.findOneByHetekaId(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { member ->
            binding.member = member
        }

        //  Listen to member comment section changes
        memberCommentViewModel.findByHetekaId(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { comments ->
            this.binding.commentSection.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.commentSection.listRecycleView.adapter = MemberCommentAdapter(comments, this)
        }

        //  Listen to member info changes
        memberInfoViewModel.getAll.distinctUntilChanged().observe(viewLifecycleOwner) { memberInfoList ->
            binding.memberInfo = memberInfoList.find { it.hetekaId == args.hetekaId }
        }

        //  Listen to current grade value and update the grade number
        memberGradeViewModel.getGradeValue(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { gradeValue ->
            binding.currentGrading.currentGrade.text = "$gradeValue"
        }

        //  Listen to current grade count value and update grade count
        memberGradeViewModel.getGradeCount(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { count ->
            @SuppressLint("SetTextI18n")
            binding.currentGrading.gradeCount.text = "($count)"
        }

        //  Listen to current grade rounded value and update stars drawable
        memberGradeViewModel.getRoundedGrade(args.hetekaId).distinctUntilChanged().observe(viewLifecycleOwner) { rounded ->
            for (i in 0..4) {
                val resource = if ((i + 1) <= rounded) R.drawable.start_filled else R.drawable.star_empty
                currentGrading[i].setImageResource(resource)
            }
        }

        //  Listen to new grade value and update stars drawable
        newGradeViewModel.currentGrade(username, args.hetekaId).observe(viewLifecycleOwner) { currentGrade ->
            if (currentGrade != null) {
                for (i in 0 until newGrading.count()) {
                    newGrading[i].setImageResource(if (i <= (currentGrade - 1)) R.drawable.start_filled else R.drawable.star_empty)
                }
            }
        }

        //  Listen to isGraded to see if we need to disable the starts to prevent extra calls and also add some opacity
        newGradeViewModel.isGraded(username, args.hetekaId).observe(viewLifecycleOwner) { isGraded ->
            binding.editGrade.visibility = if (isGraded) View.VISIBLE else View.INVISIBLE
            for (imageButton in newGrading) {
                imageButton.isEnabled = !isGraded
                imageButton.alpha = if (isGraded) 0.8f else 1f
            }
        }

    }

    private fun onEditGradeButtonClick(v: View?) {
        //  Enable the buttons and hide edit button.
        binding.editGrade.visibility = View.INVISIBLE
        for (imageButton in newGrading) {
            imageButton.isEnabled = true
            imageButton.alpha = 1f

        }
    }

    override fun onGradeButtonClick(v: View?, index: Int) {
        newGradeViewModel.createNewGrade(username, args.hetekaId, index) {
            //  When grading successfully registered.
            Toast.makeText(requireContext(), getString(R.string.graded), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateCommentButtonClicked(v: View?) {
        if (binding.newComment.commentText.text.isEmpty()) {
            return
        }

        newCommentViewModel.createNewComment(username, args.hetekaId, binding.newComment.commentText.text.toString()) {
            //  When commenting successfully registered.
            Toast.makeText(requireContext(), getString(R.string.commented), Toast.LENGTH_SHORT).show()
            binding.newComment.commentText.setText("")
        }
    }

    companion object {
        /**
         * Endpoint to get the image for details page.
         */
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
        //  Nothing to do for now.
    }
}
