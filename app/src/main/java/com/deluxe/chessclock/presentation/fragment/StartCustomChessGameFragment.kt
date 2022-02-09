package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.deluxe.chessclock.databinding.FragmentStartCustomGameBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StartCustomChessGameFragment : BottomSheetDialogFragment() {

    private val binding: FragmentStartCustomGameBinding by lazy {
        FragmentStartCustomGameBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    private val viewModel: ChessViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}