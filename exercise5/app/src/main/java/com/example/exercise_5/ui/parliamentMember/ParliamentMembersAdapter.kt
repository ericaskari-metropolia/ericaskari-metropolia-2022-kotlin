package com.example.exercise_5.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.ParliamentMemberListItemBinding
import com.example.exercise_5.ui.parliamentMember.ParliamentMember


class ParliamentMembersAdapter(
    private val parliamentMemberList: List<ParliamentMember>,
    private val onParliamentClickListener: ParliamentMemberViewHolder.Companion.OnParliamentMemberClickListener
) : RecyclerView.Adapter<ParliamentMemberViewHolder>() {

    override fun getItemCount(): Int {
        return this.parliamentMemberList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParliamentMemberViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val binding: ParliamentMemberListItemBinding =
            ParliamentMemberListItemBinding.inflate(layoutInflater, parent, false)

        return ParliamentMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParliamentMemberViewHolder, position: Int) {
        holder.setViewHolderData(
            parliamentMemberList[position],
            this.onParliamentClickListener,
            position
        );
    }
}

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

    companion object {
        interface OnParliamentMemberClickListener {
            fun onParliamentMemberClick(v: View?, index: Number)
        }
    }
}

