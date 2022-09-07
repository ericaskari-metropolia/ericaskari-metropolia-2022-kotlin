package com.example.exercise_4

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise_4.adapters.ParliamentListItemRecycleViewAdapter
import com.example.exercise_4.databinding.ActivityMainBinding
import com.example.exercise_4.datasource.ParliamentMembersData
import com.example.exercise_4.interfaces.OnParliamentClickListener
import com.example.exercise_4.models.Parliament


class MainActivity : AppCompatActivity(), OnParliamentClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val p = Parliament(ParliamentMembersData.members)

        //  Create adapter. pass this class as listener since it has implemented OnHomePageItemClickListener interface
        val adapter = ParliamentListItemRecycleViewAdapter(this, p.parties())

        //  Set recycleView adapter
        this.binding.mainActivityRecyclerView.adapter = adapter;

        this.binding.mainActivityRecyclerView.layoutManager = GridLayoutManager(null, 1)

//        println("Parliament has ${p.members.size} members")
//
//        // partymembers for party "r"
//        println("partyMembers() ${p.partyMembers("r")}")
//
//        // print parties in alphabetical order
//        println("parties() ${p.parties()}")
//
//        // print parties from largest to smallest
//        println("partiesBySize() ${p.partiesBySize()}")
//
//        // government parties
//        println("governmentParties are ${p.governmentParties()}")
//        println("or ${p.govPartiesFromLargestPartytoSmallest()}")
//
//        // how many seats does government have
//        println("governmentSeats() ${p.governmentSeats()}")
//
//        println(p.seats(setOf("sd", "kesk", "vas", "r", "vihr")))
//
//        val parties = p.parties().toSet()
//        val govs = p.governments(parties)
//        println("${govs.size} goverments from parties $parties:")
//        govs.forEach { println("$it: ${it.map { p.partyMembers(it) }.sumOf { it.size }}") }
//

    }

    override fun onItemClick(v: View?, index: Number) {
        TODO("Not yet implemented")
    }
}