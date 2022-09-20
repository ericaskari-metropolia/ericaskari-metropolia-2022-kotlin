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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.adapters.ParliamentMembersAdapter
import com.example.exercise_5.data.ExerciseApplication
import com.example.exercise_5.databinding.ParliamentMemberDetailsBinding
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModel
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory

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

        viewModel().getAll.observe(viewLifecycleOwner) { members ->
            binding.member = members[args.userId]
        }
    }

    private fun viewModel(): ParliamentMembersViewModel {
        val viewModel: ParliamentMembersViewModel by viewModels { ParliamentMembersViewModelFactory((requireActivity().application as ExerciseApplication).parliamentMemberRepository) }
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
