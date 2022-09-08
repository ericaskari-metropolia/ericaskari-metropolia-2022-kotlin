package com.example.exercise_4.adapters

// Commented out and will be used for future feature

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.exercise_4.databinding.ParliamentMemberCardItemBinding
//import com.example.exercise_4.interfaces.OnParliamentClickListener
//import com.example.exercise_4.models.ParliamentMember
//import com.example.exercise_4.viewholders.ParliamentListItemRecycleViewHolder

//class ParliamentMemberListItemRecycleViewAdapter(
//    private val onParliamentClickListener: OnParliamentClickListener,
//    private val parliamentMemberList: List<ParliamentMember>
//) : RecyclerView.Adapter<ParliamentListItemRecycleViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParliamentListItemRecycleViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//
//        val binding: ParliamentMemberCardItemBinding =
//            ParliamentMemberCardItemBinding.inflate(layoutInflater, parent, false)
//
//        return ParliamentListItemRecycleViewHolder(binding)
//    }
//
//    /**
//     * Inject data into list items.
//     */
//    override fun onBindViewHolder(holder: ParliamentListItemRecycleViewHolder, position: Int) {
//        holder.setViewHolderData(parliamentMemberList[position], this.onParliamentClickListener, position);
//    }
//
//    override fun getItemCount(): Int {
//        return this.parliamentMemberList.size
//    }
//
//}