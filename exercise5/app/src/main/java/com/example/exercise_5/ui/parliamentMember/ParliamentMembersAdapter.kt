package com.example.exercise_5.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.ParliamentMemberListItemBinding
import com.example.exercise_5.ui.parliamentMember.OnParliamentMemberClickListener
import com.example.exercise_5.ui.parliamentMember.ParliamentMember
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberViewHolder

class ParliamentMembersAdapter(
    private val parliamentMemberList: List<ParliamentMember>,
    private val onParliamentClickListener: OnParliamentMemberClickListener
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