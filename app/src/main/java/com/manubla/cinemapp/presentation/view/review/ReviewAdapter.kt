package com.manubla.cinemapp.presentation.view.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.cinemapp.R
import com.manubla.cinemapp.data.model.Review
import com.manubla.cinemapp.databinding.ItemReviewBinding

class ReviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var reviews = arrayListOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addMovieItems(items: List<Review>) {
        reviews.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Any) {
        reviews.add(item)
        notifyDataSetChanged()
    }

    fun removeProgressItem() {
        if (reviews[reviews.size-1] !is Review)
            reviews.removeAt(reviews.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == REVIEW_TYPE) {
            ReviewViewHolder(ItemReviewBinding.inflate(inflater))
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (reviews[position] is Review)
            return REVIEW_TYPE
        return PROGRESS_TYPE
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReviewViewHolder && reviews[position] is Review)
            holder.bind(reviews[position] as Review)
    }

    inner class ReviewViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review?) {
            binding.review = item
            binding.executePendingBindings()
        }
    }

    inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val PROGRESS_TYPE = 1
        private const val REVIEW_TYPE = 2
    }

}