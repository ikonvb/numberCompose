package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.konstantinbulygin.numbercomposer.R
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
        bindViews()
    }

    private fun bindViews() {
        with(gameFinishedBinding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                args.gameResult.gameSettings.minCountRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                args.gameResult.countRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPercentRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getPercentOfRightAnswers() = with(args.gameResult) {
        if (countQuestions == 0) {
            0
        } else {
            ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
        }
    }


    private fun getSmileResId(): Int {
        return if (args.gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
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