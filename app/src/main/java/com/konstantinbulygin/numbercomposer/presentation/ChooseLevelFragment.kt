package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstantinbulygin.numbercomposer.R
import com.konstantinbulygin.numbercomposer.databinding.FragmentChooseLevelBinding
import com.konstantinbulygin.numbercomposer.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _chooseLevelBinding: FragmentChooseLevelBinding? = null
    private val chooseLevelBinding: FragmentChooseLevelBinding
        get() = _chooseLevelBinding
            ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _chooseLevelBinding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return chooseLevelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(chooseLevelBinding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _chooseLevelBinding = null
    }

    private fun launchGameFragment(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.getNewInstance(level))
            .addToBackStack(GameFragment.GAME_NAME)
            .commit()
    }

    companion object {

        const val CHOOSE_LEVEL_NAME = "choose_level"

        fun getNewInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

}