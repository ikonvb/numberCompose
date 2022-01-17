package com.konstantinbulygin.numbercomposer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.konstantinbulygin.numbercomposer.databinding.FragmentChooseLevelBinding

class ChooseLevelFragment : Fragment() {

    private var _chooseLevelBinding: FragmentChooseLevelBinding? = null
    private val chooseLevelBinding: FragmentChooseLevelBinding
        get() = _chooseLevelBinding
            ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _chooseLevelBinding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return chooseLevelBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _chooseLevelBinding = null
    }

}