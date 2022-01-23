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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameBinding
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

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
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

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

    private fun launchGameFinishedFragment(gameResult: GameResult) {

        /*another approach to navigate between fragments
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.getNewInstance(gameResult))
            .addToBackStack(null)
            .commit()*/

//        val args = Bundle().apply {
//            putParcelable(KEY_GAME_RESULT, gameResult)
//        }
//        findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment, args)

        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
    }
}