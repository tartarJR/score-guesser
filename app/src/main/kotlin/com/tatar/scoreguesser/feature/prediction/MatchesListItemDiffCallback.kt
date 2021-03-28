package com.tatar.scoreguesser.feature.prediction

import androidx.recyclerview.widget.DiffUtil
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.scoreguesser.util.ListItemChange

class MatchesListItemDiffCallback : DiffUtil.ItemCallback<MatchModel>() {
    override fun areItemsTheSame(
        oldItem: MatchModel,
        newItem: MatchModel
    ): Boolean {
        return oldItem.matchIdentifier == newItem.matchIdentifier
    }

    override fun areContentsTheSame(
        oldItem: MatchModel,
        newItem: MatchModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: MatchModel,
        newItem: MatchModel
    ): Any {
        return ListItemChange(oldItem, newItem)
    }
}
