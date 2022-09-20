package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.exercise_5.R
import com.example.exercise_5.databinding.ParliamentMemberListItemBinding

class ParliamentMemberListItemFragment() : Fragment() {
    private lateinit var binding: ParliamentMemberListItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ParliamentMemberListItemBinding.inflate(inflater, container, false)
        return binding.root;
    }


    companion object {
        @JvmStatic
        @BindingAdapter("loadListItemImage")
        fun loadListItemImage(view: ImageView, profileImage: String?) {
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
