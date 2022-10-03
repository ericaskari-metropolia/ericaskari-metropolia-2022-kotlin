package com.example.exercise_5.ui.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.PartyListItemBinding


class PartyAdapter(
    private val partyList: List<Party>,
    private val clickListener: PartyViewHolder.Companion.OnListItemClickListener
) : RecyclerView.Adapter<PartyViewHolder>() {

    override fun getItemCount(): Int {
        return this.partyList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: PartyListItemBinding = PartyListItemBinding.inflate(layoutInflater, parent, false)
        return PartyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        partyList.forEach { println("onBindViewHolder ${it.name} ${it.seatCount}") }
        holder.setViewHolderData(partyList[position], this.clickListener, position);

    }
}

class PartyViewHolder(val binding: PartyListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnListItemClickListener
    lateinit var index: Number

    fun setViewHolderData(party: Party, listener: OnListItemClickListener, index: Number) {
        this.listener = listener
        binding.name = party.name
        binding.seatCount = party.seatCount.toString()
        this.index = index
    }

    override fun onClick(v: View?) {
        this.listener.onPartyClick(v, this.index)
    }

    companion object {
        interface OnListItemClickListener {
            fun onPartyClick(v: View?, index: Number)
        }
    }
}

