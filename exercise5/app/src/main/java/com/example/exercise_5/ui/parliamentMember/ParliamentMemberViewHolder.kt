package com.example.exercise_5.ui.parliamentMember

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.ParliamentMemberListItemBinding

class ParliamentMemberViewHolder(val binding: ParliamentMemberListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnParliamentMemberClickListener
    lateinit var index: Number

    /**
     * Here we update UI values.
     */
    fun setViewHolderData(
        member: ParliamentMember,
        listener: OnParliamentMemberClickListener,
        index: Number
    ) {
        //  Set UI
        binding.member = member

        //  Save listener instance
        this.listener = listener

        //  Save position to pass into click listener
        this.index = index
    }


    override fun onClick(v: View?) {
        println("ParliamentListItemRecycleViewHolder onClick: " + this.index)
        //  Validation
        this.listener.onParliamentMemberClick(v, this.index)
    }
}