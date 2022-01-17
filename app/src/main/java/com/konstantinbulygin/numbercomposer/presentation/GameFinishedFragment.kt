package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private var _gameFinishedBinding: FragmentGameFinishedBinding? = null
    private val gameFinishedBinding: FragmentGameFinishedBinding
        get() = _gameFinishedBinding
            ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _gameFinishedBinding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return gameFinishedBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _gameFinishedBinding = null
    }
}