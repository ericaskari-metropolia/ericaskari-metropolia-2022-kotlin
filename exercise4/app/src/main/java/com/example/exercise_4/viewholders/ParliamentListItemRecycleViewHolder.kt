package com.example.exercise_4.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_4.databinding.ParliamentCardItemBinding
import com.example.exercise_4.interfaces.OnParliamentClickListener

class ParliamentListItemRecycleViewHolder(private val binding: ParliamentCardItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnParliamentClickListener
    lateinit var index: Number

    /**
     * Here we update UI values.
     */
    fun setViewHolderData(parliamentName: String, listener: OnParliamentClickListener, index: Number) {
        //  Set UI Text
        binding.parliamentName.text = parliamentName

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