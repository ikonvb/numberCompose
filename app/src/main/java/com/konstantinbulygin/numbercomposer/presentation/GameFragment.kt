package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _gameBinding: FragmentGameBinding? = null
    private val gameBinding: FragmentGameBinding
        get() = _gameBinding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _gameBinding = FragmentGameBinding.inflate(inflater, container, false)
        return gameBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _gameBinding = null
    }
}