package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.MemberListBinding
import com.example.exercise_5.databinding.PartyListBinding
import com.example.exercise_5.ui.party.*

/**
 * @author Mohammad Askari
 */
class PartiesFragment : Fragment(), PartyViewHolder.Companion.OnListItemClickListener {
    //  ViewBinding
    lateinit var binding: PartyListBinding

    //  Application
    private val application by lazy { requireActivity().application as ExerciseApplication }


    private val partyViewModel: PartyViewModel by viewModels { PartyViewModelFactory(application.partyRepository) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PartyListBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Listen to all parties and update the ui
        partyViewModel.getAll.distinctUntilChanged().observe(viewLifecycleOwner) { items ->
            this.binding.listRecycleView.layoutManager = LinearLayoutManager(requireContext())
            this.binding.listRecycleView.adapter = PartyAdapter(items, this)
        }

        //  Get necessary data.
        partyViewModel.populate()
    }

    override fun onPartyClick(v: View?, index: Number) {
        val parties = partyViewModel.getAll.value
        val party: Party? = parties?.get(index as Int)

        if (party != null) {
            val action = PartiesFragmentDirections.toParliamentMemberListFragmentAction(party.name)
            findNavController().navigate(action)
        }
    }
}