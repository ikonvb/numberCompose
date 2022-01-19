package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameFinishedBinding
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult
import com.konstantinbulygin.numbercomposer.presentation.GameFragment.Companion.GAME_NAME

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _gameFinishedBinding: FragmentGameFinishedBinding? = null
    private val gameFinishedBinding: FragmentGameFinishedBinding
        get() = _gameFinishedBinding
            ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
        gameFinishedBinding.buttonRetry.setOnClickListener {
            retryGame()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _gameFinishedBinding = null
    }

    private fun parseArgs() {
       requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
           gameResult = it
       }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GAME_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun getNewInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}