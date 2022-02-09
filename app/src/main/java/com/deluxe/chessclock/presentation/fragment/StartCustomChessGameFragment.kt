package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.FragmentStartCustomGameBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.deluxe.chessclock.presentation.util.navigateSafely
import com.deluxe.chessclock.presentation.util.showToast
import com.deluxe.core.data.ChessGame
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.start.setOnClickListener {
            if (!validateFieldsNotEmpty()) activity?.showToast(R.string.all_fields_required)
            else if (validateFieldsInput())
                startGame(binding.save.isChecked)
        }
    }

    private fun startGame(shouldSave: Boolean) {
        val game = ChessGame(
            binding.nameOfTheGameText.text.toString(),
            binding.durationText.text.toString().toLong() * 60,
            binding.incrementText.text.toString().toInt()
        )
        if (shouldSave) viewModel.saveChessGame(game)
        viewModel.setActiveGame(game)
        navigateSafely(
            StartCustomChessGameFragmentDirections.actionStartCustomChessGameFragmentToFragmentChessGame(),
            R.id.startCustomChessGameFragment
        )
    }

    private fun validateFieldsNotEmpty(): Boolean =
        binding.durationText.text.toString().isNotEmpty() &&
                binding.incrementText.text.toString().isNotEmpty() &&
                (!binding.save.isChecked || binding.nameOfTheGameText.text.toString().isNotEmpty())


    private fun validateFieldsInput(): Boolean =
        binding.durationText.text.toString().length <= binding.duration.counterMaxLength &&
                binding.incrementText.text.toString().length <= binding.increment.counterMaxLength &&
                binding.nameOfTheGameText.text.toString().length <= binding.nameOfTheGame.counterMaxLength


}