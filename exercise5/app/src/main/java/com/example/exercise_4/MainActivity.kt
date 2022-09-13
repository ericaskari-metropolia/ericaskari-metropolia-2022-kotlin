package com.example.exercise_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.exercise_5.databinding.ActivityMainBinding
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.fragments.ParliamentMemberCardFragment
import com.example.exercise_5.models.Parliament

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val p = Parliament(ParliamentMembersData.members)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view, ParliamentMemberCardFragment(p.members.random()))
                setReorderingAllowed(true)
            }
        }

        binding.viewNext.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, ParliamentMemberCardFragment(p.members.random()))
                setReorderingAllowed(true)
            }
        }
    }
}