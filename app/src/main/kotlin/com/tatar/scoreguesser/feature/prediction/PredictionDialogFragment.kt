package com.tatar.scoreguesser.feature.prediction

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.scoreguesser.R
import com.tatar.scoreguesser.databinding.FragmentPredictionDialogBinding

class PredictionDialogFragment(
    private val matchModel: MatchModel
) : AppCompatDialogFragment() {

    private var listener: PredictionDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater
            .inflate(R.layout.fragment_prediction_dialog, null)
        val binding = FragmentPredictionDialogBinding.bind(view)

        bindInteractions(binding)
        setUpData(binding)

        builder.setView(view)
            .setTitle(getString(R.string.prediction_pop_up_title))
            .setNegativeButton(getString(R.string.prediction_pop_up_action_cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.prediction_pop_up_action_enter)) { _, _ ->
                val homeScore = binding.homeTeamScoreTv.text.toString()
                val awayScore = binding.awayTeamScoreTv.text.toString()

                listener?.onPredictionsEntered(matchModel.matchIdentifier, homeScore, awayScore)
            }

        return builder.create()
    }

    private fun setUpData(binding: FragmentPredictionDialogBinding) {
        binding.homeTeamTv.text = matchModel.homeTeamName
        binding.awayTeamTv.text = matchModel.awayTeamName
        if (matchModel.homeTeamScore.isNotBlank())
            binding.homeTeamScoreTv.text = matchModel.homeTeamScore
        if (matchModel.awayTeamScore.isNotBlank())
            binding.awayTeamScoreTv.text = matchModel.awayTeamScore
    }

    //I would these calculations in a view model/use case too but did not have time
    @SuppressLint("SetTextI18n")
    private fun bindInteractions(binding: FragmentPredictionDialogBinding) {
        with(binding) {
            homeTeamScoreUpTv.setOnClickListener {
                val homeScore = homeTeamScoreTv.text.toString().toInt()
                homeTeamScoreTv.text = "${homeScore + 1}"
            }
            homeTeamScoreDownTv.setOnClickListener {
                val homeScore = homeTeamScoreTv.text.toString().toInt()
                val newHomeScore = if (homeScore - 1 > 0) homeScore - 1 else 0
                homeTeamScoreTv.text = newHomeScore.toString()
            }
            awayTeamScoreUpTv.setOnClickListener {
                val awayScore = awayTeamScoreTv.text.toString().toInt()
                awayTeamScoreTv.text = "${awayScore + 1}"
            }
            awayTeamScoreDownTv.setOnClickListener {
                val awayScore = awayTeamScoreTv.text.toString().toInt()
                val awayHomeScore = if (awayScore - 1 > 0) awayScore - 1 else 0
                awayTeamScoreTv.text = awayHomeScore.toString()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            targetFragment as PredictionDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "Must implement PredictionDialogListener.")
        }
    }

    interface PredictionDialogListener {
        fun onPredictionsEntered(
            matchIdentifier: String,
            homeTeamScore: String,
            awayTeamScore: String
        )
    }
}
