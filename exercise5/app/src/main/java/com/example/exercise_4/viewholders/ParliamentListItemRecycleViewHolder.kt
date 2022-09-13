package com.example.exercise_5.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.ParliamentMemberCardItemBinding
import com.example.exercise_5.interfaces.OnParliamentClickListener
import com.example.exercise_5.models.ParliamentMember

class ParliamentListItemRecycleViewHolder(private val binding: ParliamentMemberCardItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnParliamentClickListener
    lateinit var index: Number

    /**
     * Here we update UI values.
     */
    fun setViewHolderData(member: ParliamentMember, listener: OnParliamentClickListener, index: Number) {
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
        this.listener.onItemClick(v, this.index)
    }
}