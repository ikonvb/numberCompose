package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _welcomeBinding: FragmentWelcomeBinding? = null
    private val welcomeBinding: FragmentWelcomeBinding
        get() = _welcomeBinding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _welcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return welcomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcomeBinding.buttonUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _welcomeBinding = null
    }

    private fun launchChooseLevelFragment() {

        /*another approach to navigate between fragments
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment.getNewInstance())
            .addToBackStack(CHOOSE_LEVEL_NAME)
            .commit() */

        findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)


    }
}