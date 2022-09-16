package com.example.exercise_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.exercise_5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        appBarConfiguration = AppBarConfiguration(findNavController(R.id.nav_host_fragment_content_main).graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        supportFragmentManager.beginTransaction()
//            .add(binding.navHostFragment.id, ParliamentMemberListFragment())
//            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_content_main).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//    override fun onItemClick(v: View?, index: Number) {
////        supportFragmentManager.beginTransaction()
////            .replace(binding.navHostFragment.id, ParliamentMemberDetailsFragment())
////            .commit()
//    }

}