package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.databinding.ParliamentMemberDetailsBinding
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.models.Parliament
import com.example.exercise_5.models.ParliamentMember

class ParliamentMemberDetailsFragment : Fragment() {
    lateinit var binding: ParliamentMemberDetailsBinding

    private var members: List<ParliamentMember> = Parliament(ParliamentMembersData.members).members

    private val args: ParliamentMemberDetailsFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ParliamentMemberDetailsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.member = members[args.userId]
    }


    companion object {
        @JvmStatic
        @BindingAdapter("loadDetailsPageImage")
        fun loadDetailsPageImage(view: ImageView, profileImage: String) {
            Glide.with(view.context)
                .load("https://avoindata.eduskunta.fi/$profileImage")
                .placeholder(R.drawable.white)
                .fallback(R.drawable.user)
                .error(R.drawable.user)
                .into(view)
        }
    }
}
