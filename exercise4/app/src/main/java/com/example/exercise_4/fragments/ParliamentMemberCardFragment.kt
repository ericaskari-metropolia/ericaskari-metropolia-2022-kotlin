package com.example.exercise_4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.exercise_4.R
import com.example.exercise_4.databinding.ParliamentMemberCardItemBinding
import com.example.exercise_4.models.ParliamentMember

class ParliamentMemberCardFragment() : Fragment() {
    private var member: ParliamentMember? = null

    constructor(member: ParliamentMember) : this() {
        this.member = member
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ParliamentMemberCardItemBinding = ParliamentMemberCardItemBinding.inflate(inflater, container, false)
        binding.member = this.member
        return binding.root;

    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, profileImage: String) {
            Glide.with(view.context)
                .load("https://avoindata.eduskunta.fi/$profileImage")
                .override(300, 300)
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.white)
                .fallback(R.drawable.user)
                .error(R.drawable.user)
                .into(view)
        }
    }

}
