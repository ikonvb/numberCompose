package com.konstantinbulygin.numbercomposer.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameBinding
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult
import com.konstantinbulygin.numbercomposer.domain.entity.Level

class GameFragment : Fragment() {

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(gameBinding.tvOption1)
            add(gameBinding.tvOption2)
            add(gameBinding.tvOption3)
            add(gameBinding.tvOption4)
            add(gameBinding.tvOption5)
            add(gameBinding.tvOption6)
        }
    }

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

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
        observeViewModel()
        setClickListenersToOptions()
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            question.observe(viewLifecycleOwner) {
                gameBinding.tvSum.text = it.sum.toString()
                gameBinding.tvLeftNumber.text = it.visibleNumber.toString()
                for (i in 0 until tvOptions.size) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
            percentOfRightAnswers.observe(viewLifecycleOwner) {
                gameBinding.progressBar.setProgress(it, true)
            }
            enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
                val color = getColorByState(it)
                gameBinding.tvAnswersProgress.setTextColor(color)
            }
            enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
                val color = getColorByState(it)
                gameBinding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }
            formattedTime.observe(viewLifecycleOwner) {
                gameBinding.tvTimer.text = it
            }
            minPercent.observe(viewLifecycleOwner) {
                gameBinding.progressBar.secondaryProgress = it
            }
            gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
            progressAnswers.observe(viewLifecycleOwner) {
                gameBinding.tvAnswersProgress.text = it
            }
        }
    }

    private fun getColorByState(it: Boolean): Int {
        val coloResId = if (it) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }

        return ContextCompat.getColor(requireContext(), coloResId)
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