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
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberViewModel
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModel
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoViewModelFactory

class ParliamentMemberDetailsFragment : Fragment() {
    lateinit var binding: ParliamentMemberDetailsBinding

    private val args: ParliamentMemberDetailsFragmentArgs by navArgs()


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

        parliamentMemberViewModel().getAll.observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }
        parliamentMemberInfoViewModel().getAll.observe(viewLifecycleOwner) { memberInfos ->
            binding.memberInfo = memberInfos[args.userId]
        }
    }

    private fun parliamentMemberViewModel(): ParliamentMemberViewModel {
        val viewModel: ParliamentMemberViewModel by viewModels { ParliamentMembersViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRepository) }
        return viewModel
    }

    private fun parliamentMemberInfoViewModel(): ParliamentMemberInfoViewModel {
        val viewModel: ParliamentMemberInfoViewModel by viewModels { ParliamentMemberInfoViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberInfoRepository) }
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
}
