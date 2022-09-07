package com.example.exercise_4

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.exercise_4.databinding.ActivityMainBinding
import com.example.exercise_4.models.Parliament
import com.example.exercise_4.models.ParliamentMembersData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val p = Parliament(ParliamentMembersData.members)

        println("Parliament has ${p.members.size} members")

        // partymembers for party "r"
        println("partyMembers() ${p.partyMembers("r")}")

        // print parties in alphabetical order
        println("parties() ${p.parties()}")

        // print parties from largest to smallest
        println("partiesBySize() ${p.partiesBySize()}")

        // government parties
        println("governmentParties are ${p.governmentParties()}")
        println("or ${p.govPartiesFromLargestPartytoSmallest()}")

        // how many seats does government have
        println("governmentSeats() ${p.governmentSeats()}")

        println(p.seats(setOf("sd", "kesk", "vas", "r", "vihr")))

        val parties = p.parties().toSet()
        val govs = p.governments(parties)
        println("${govs.size} goverments from parties $parties:")
        govs.forEach { println("$it: ${it.map { p.partyMembers(it) }.sumOf { it.size }}") }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}