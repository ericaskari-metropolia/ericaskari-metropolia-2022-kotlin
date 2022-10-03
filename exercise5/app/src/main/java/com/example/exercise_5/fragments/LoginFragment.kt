package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.LoginBinding

/**
 * @author Mohammad Askari
 */
class LoginFragment : Fragment() {
    lateinit var binding: LoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener { this.onLoginClick(it) }
    }

    private fun onLoginClick(p0: View?) {
        val nickname = binding.nickname.text.toString()
        if (nickname.isEmpty()) {
            Toast.makeText(requireContext(), "Nickname is required and it can be anything.", Toast.LENGTH_SHORT).show()
            return
        }

        (requireActivity().application as ExerciseApplication).login(nickname)
        Toast.makeText(requireContext(), "Welcome $nickname", Toast.LENGTH_SHORT).show()

        val action = LoginFragmentDirections.toHomePageFragmentAction()
        findNavController().navigate(action)
    }

}
