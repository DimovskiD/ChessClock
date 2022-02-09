package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
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
    private val isEditMode : Boolean by lazy { viewModel.isEditMode() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isEditMode) initEditMode()

        binding.start.setOnClickListener {
            if (!validateFieldsNotEmpty()) activity?.showToast(R.string.all_fields_required)
            else if (validateFieldsInput()) {
                val game = ChessGame(
                    binding.nameOfTheGameText.text.toString(),
                    binding.durationText.text.toString().toLong() * 60,
                    binding.incrementText.text.toString().toInt(),
                    if (isEditMode) viewModel.selectedGame!!.id else -1L
                )
                if (binding.save.isChecked) viewModel.saveChessGame(game)
                if (!isEditMode) startGame(game)
                else closeBottomSheetDialog()
            }
        }
    }

    private fun closeBottomSheetDialog() {
        viewModel.setSelectedGame(null)
        NavHostFragment.findNavController(this).navigateUp()
    }

    private fun initEditMode() {
        binding.title.text = getString(R.string.edit_game)
        binding.save.isChecked = true
        binding.save.visibility = View.GONE
        binding.start.text = getString(R.string.save)
        binding.nameOfTheGameText.setText(viewModel.selectedGame?.name)
        binding.incrementText.setText(viewModel.selectedGame?.increment.toString())
        binding.durationText.setText((viewModel.selectedGame?.time?.div(60)).toString())
    }

    private fun startGame(game: ChessGame) {
        viewModel.setSelectedGame(game)
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