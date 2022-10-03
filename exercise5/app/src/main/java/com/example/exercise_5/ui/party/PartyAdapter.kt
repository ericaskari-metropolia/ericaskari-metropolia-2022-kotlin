package com.example.exercise_5.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.PartyListItemBinding
import com.example.exercise_5.ui.party.Party


class PartyAdapter(
    private val PartyList: List<Party>,
    private val onParliamentClickListener: PartyViewHolder.Companion.OnListItemClickListener
) : RecyclerView.Adapter<PartyViewHolder>() {

    override fun getItemCount(): Int {
        return this.PartyList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PartyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val binding: PartyListItemBinding =
            PartyListItemBinding.inflate(layoutInflater, parent, false)

        return PartyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.setViewHolderData(
            PartyList[position],
            this.onParliamentClickListener,
            position
        );
    }
}

class PartyViewHolder(val binding: PartyListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnListItemClickListener
    lateinit var index: Number

    /**
     * Here we update UI values.
     */
    fun setViewHolderData(
        party: Party,
        listener: OnListItemClickListener,
        index: Number
    ) {
        //  Set UI
        binding.name = party.name
        binding.seatCount = party.seatCount

        //  Save listener instance
        this.listener = listener

        //  Save position to pass into click listener
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

