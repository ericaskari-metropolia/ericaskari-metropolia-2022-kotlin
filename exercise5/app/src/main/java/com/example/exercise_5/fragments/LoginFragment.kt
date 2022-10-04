package com.example.exercise_5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise_5.R
import com.example.exercise_5.application.ExerciseApplication
import com.example.exercise_5.databinding.LoginBinding

/**
 * @author Mohammad Askari
 * Login Page where user can login with simple nickname.
 */
class LoginFragment : Fragment() {
    lateinit var binding: LoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener { this.onLoginClick(it) }
    }

    private fun onLoginClick(p0: View?) {
        val nickname = binding.nickname.text.toString()

        //  Validations
        if (nickname.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.nickname_required), Toast.LENGTH_SHORT).show()
            return
        }

        //  Build and Show Toast
        (requireActivity().application as ExerciseApplication).login(nickname)
        Toast.makeText(requireContext(), getString(R.string.welcome_username, nickname), Toast.LENGTH_SHORT).show()

        //  Build and Navigate Action
        val action = LoginFragmentDirections.toHomePageFragmentAction()
        findNavController().navigate(action)
    }

}
