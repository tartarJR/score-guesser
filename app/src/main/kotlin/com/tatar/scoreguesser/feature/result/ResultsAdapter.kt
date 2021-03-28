package com.tatar.scoreguesser.feature.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatar.core.dagger.scope.FragmentScope
import com.tatar.presentation.feature.result.model.MatchResultModel
import com.tatar.scoreguesser.databinding.LayoutResultItemBinding
import javax.inject.Inject

@FragmentScope
class ResultsAdapter @Inject constructor() :
    ListAdapter<MatchResultModel, ResultsAdapter.ViewHolder>(ResultsListItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutResultItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: LayoutResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchResultModel) {
            binding.homeTeamTv.text = item.homeTeamName
            binding.awayTeamTv.text = item.awayTeamName
            binding.homeTeamScoreTv.text = item.homeTeamScore
            binding.awayTeamScoreTv.text = item.awayTeamScore
            binding.homeTeamScorePredictionTv.text = item.homeTeamScorePrediction
            binding.awayTeamScorePredictionTv.text = item.awayTeamScorePrediction
        }
    }
}
