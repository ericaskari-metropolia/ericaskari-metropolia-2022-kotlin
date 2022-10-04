package com.example.exercise_5.ui.membercomment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_5.databinding.CommentListItemBinding
import java.text.SimpleDateFormat


/**
 * @author Mohammad Askari
 */
class MemberCommentAdapter(
    private val items: List<MemberComment>,
    private val onItemClickListener: MemberCommentViewHolder.Companion.OnMemberCommentClickListener
) : RecyclerView.Adapter<MemberCommentViewHolder>() {

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberCommentViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val binding = CommentListItemBinding.inflate(layoutInflater, parent, false)

        return MemberCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberCommentViewHolder, position: Int) {
        holder.setViewHolderData(items[position], this.onItemClickListener, position)
    }
}

class MemberCommentViewHolder(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.root.setOnClickListener(this)
    }

    lateinit var listener: OnMemberCommentClickListener
    lateinit var index: Number

    fun setViewHolderData(item: MemberComment, listener: OnMemberCommentClickListener, index: Number) {
        binding.comment = item
        this.listener = listener
        this.index = index
    }


    override fun onClick(v: View?) {
        this.listener.onMemberCommentClick(v, this.index)
    }

    companion object {
        interface OnMemberCommentClickListener {
            fun onMemberCommentClick(v: View?, index: Number)
        }
    }
}

