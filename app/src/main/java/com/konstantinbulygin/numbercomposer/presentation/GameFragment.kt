package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameBinding
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult
import com.konstantinbulygin.numbercomposer.domain.entity.GameSettings
import com.konstantinbulygin.numbercomposer.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _gameBinding: FragmentGameBinding? = null
    private val gameBinding: FragmentGameBinding
        get() = _gameBinding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _gameBinding = FragmentGameBinding.inflate(inflater, container, false)
        return gameBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(gameBinding) {
            tvSum.setOnClickListener {
                launchGameFinishedFragment(
                    GameResult(
                        true,
                        30,
                        30,
                        GameSettings(
                            0,
                            0,
                            0,
                            10
                        )
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _gameBinding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.getNewInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val GAME_NAME = "game_name"
        private const val KEY_LEVEL = "level"

        fun getNewInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}