package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise_5.adapters.ParliamentMemberListRecycleViewAdapter
import com.example.exercise_5.databinding.ParliamentMemberListBinding
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.interfaces.OnParliamentClickListener
import com.example.exercise_5.models.Parliament
import com.example.exercise_5.models.ParliamentMember

class ParliamentMemberListFragment : Fragment(), OnParliamentClickListener {
    private lateinit var binding: ParliamentMemberListBinding

    private var members: List<ParliamentMember> = Parliament(ParliamentMembersData.members).members

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ParliamentMemberListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ParliamentMemberListRecycleViewAdapter(this, members)

        //  Set recycleView adapter
        this.binding.listRecycleView.adapter = adapter
        //  Set grid layout with one columns
        this.binding.listRecycleView.layoutManager = GridLayoutManager(null, 1)
    }

    override fun onItemClick(v: View?, index: Number) {
        // val item = members[index.toInt()]
        // Toast.makeText(requireActivity().applicationContext,"Clicked on ${item.fullName()}", Toast.LENGTH_SHORT).show()
        val action = ParliamentMemberListFragmentDirections.toParliamentMemberDetailsFragmentAction(index.toInt())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
