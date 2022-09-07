package com.example.exercise_4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_4.databinding.ParliamentCardItemBinding
import com.example.exercise_4.interfaces.OnParliamentClickListener
import com.example.exercise_4.viewholders.ParliamentListItemRecycleViewHolder

class ParliamentListItemRecycleViewAdapter(
    private val onParliamentClickListener: OnParliamentClickListener,
    private val parliamentList: List<String>
) : RecyclerView.Adapter<ParliamentListItemRecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParliamentListItemRecycleViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val binding: ParliamentCardItemBinding = ParliamentCardItemBinding.inflate(layoutInflater, parent, false)

        return ParliamentListItemRecycleViewHolder(binding)
    }

    /**
     * Inject data into list items.
     */
    override fun onBindViewHolder(holder: ParliamentListItemRecycleViewHolder, position: Int) {
        holder.setViewHolderData(parliamentList[position], this.onParliamentClickListener, position);
    }

    override fun getItemCount(): Int {
        return this.parliamentList.size
    }

}