package com.example.exercise_5.ui.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.MemberListItemBinding


class MembersAdapter(
    private val memberList: List<Member>,
    private val onParliamentClickListener: MemberViewHolder.Companion.OnParliamentMemberClickListener
) : RecyclerView.Adapter<MemberViewHolder>() {

    override fun getItemCount(): Int {
        return this.memberList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val binding: MemberListItemBinding =
            MemberListItemBinding.inflate(layoutInflater, parent, false)

        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.setViewHolderData(memberList[position], this.onParliamentClickListener, position);
    }
}

class MemberViewHolder(val binding: MemberListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnParliamentMemberClickListener
    lateinit var index: Number

    /**
     * Here we update UI values.
     */
    fun setViewHolderData(
        member: Member,
        listener: OnParliamentMemberClickListener,
        index: Number
    ) {
        binding.member = member
        this.listener = listener
        this.index = index
    }


    override fun onClick(v: View?) {
        this.listener.onParliamentMemberClick(v, this.index)
    }

    companion object {
        interface OnParliamentMemberClickListener {
            fun onParliamentMemberClick(v: View?, index: Number)
        }
    }
}

