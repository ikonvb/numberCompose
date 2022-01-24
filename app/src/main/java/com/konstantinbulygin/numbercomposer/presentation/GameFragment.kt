package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.konstantinbulygin.numbercomposer.databinding.FragmentGameBinding
import com.konstantinbulygin.numbercomposer.domain.entity.GameResult

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

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
        gameBinding.viewModel = viewModel
        gameBinding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
        }
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