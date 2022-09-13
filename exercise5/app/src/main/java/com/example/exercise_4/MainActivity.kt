package com.example.exercise_5

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise_5.adapters.ParliamentMemberListItemRecycleViewAdapter
import com.example.exercise_5.databinding.ActivityMainBinding
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.fragments.ParliamentMemberCardFragment
import com.example.exercise_5.interfaces.OnParliamentClickListener
import com.example.exercise_5.models.Parliament


class MainActivity : AppCompatActivity(R.layout.activity_main), OnParliamentClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val p = Parliament(ParliamentMembersData.members)

        val adapter = ParliamentMemberListItemRecycleViewAdapter(this, p.members)

        //  Set recycleView adapter
        this.binding.recycleView.adapter = adapter

        //  Set grid layout with two columns
        this.binding.recycleView.layoutManager = GridLayoutManager(null, 1)
    }

    override fun onItemClick(v: View?, index: Number) {
    }
}