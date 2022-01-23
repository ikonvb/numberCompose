package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _gameFinishedBinding: FragmentGameFinishedBinding? = null
    private val gameFinishedBinding: FragmentGameFinishedBinding
        get() = _gameFinishedBinding
            ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _gameFinishedBinding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return gameFinishedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        gameFinishedBinding.gameResult = args.gameResult
    }

    private fun setupClickListeners() {
        gameFinishedBinding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _gameFinishedBinding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}