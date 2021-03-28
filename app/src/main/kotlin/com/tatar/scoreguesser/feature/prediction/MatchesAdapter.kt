package com.tatar.scoreguesser.feature.prediction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatar.core.dagger.scope.FragmentScope
import com.tatar.presentation.feature.prediction.model.MatchModel
import com.tatar.scoreguesser.databinding.LayoutMatchItemBinding
import javax.inject.Inject

@FragmentScope
class MatchesAdapter @Inject constructor() :
    ListAdapter<MatchModel, MatchesAdapter.ViewHolder>(MatchesListItemDiffCallback()) {

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutMatchItemBinding.inflate(layoutInflater, parent, false)

        val viewHolder = ViewHolder(binding)

        setItemClickListener(viewHolder)

        return viewHolder
    }

    private fun setItemClickListener(viewHolder: ViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position == -1) return@setOnClickListener

            val item = getItem(position)
            if (item is MatchModel) itemClickListener?.onItemClicked(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: LayoutMatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchModel) {
            binding.homeTeamTv.text = item.homeTeamName
            binding.awayTeamTv.text = item.awayTeamName
            binding.homeTeamScoreTv.text = item.homeTeamScore
            binding.awayTeamScoreTv.text = item.awayTeamScore
        }
    }

    fun setAdapterItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun interface ItemClickListener {
        fun onItemClicked(match: MatchModel)
    }
}
