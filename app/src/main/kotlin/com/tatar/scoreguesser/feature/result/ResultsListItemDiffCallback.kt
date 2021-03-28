package com.tatar.scoreguesser.feature.result

import androidx.recyclerview.widget.DiffUtil
import com.tatar.presentation.feature.result.model.MatchResultModel
import com.tatar.scoreguesser.util.ListItemChange

class ResultsListItemDiffCallback : DiffUtil.ItemCallback<MatchResultModel>() {
    override fun areItemsTheSame(
        oldItem: MatchResultModel,
        newItem: MatchResultModel
    ): Boolean {
        return oldItem.matchIdentifier == newItem.matchIdentifier
    }

    override fun areContentsTheSame(
        oldItem: MatchResultModel,
        newItem: MatchResultModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: MatchResultModel,
        newItem: MatchResultModel
    ): Any {
        return ListItemChange(oldItem, newItem)
    }
}
