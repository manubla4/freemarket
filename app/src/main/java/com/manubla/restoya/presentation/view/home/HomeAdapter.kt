package com.manubla.restoya.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.restoya.R
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.databinding.ItemRestaurantBinding

class HomeAdapter(private val listener: OnAdapterInteraction):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var restaurants = arrayListOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addRestaurantItems(items: List<Restaurant>) {
        restaurants.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Any) {
        restaurants.add(item)
        notifyDataSetChanged()
    }

    fun removeProgressItem() {
        if (restaurants.isNotEmpty() && restaurants[restaurants.size-1] !is Restaurant)
            restaurants.removeAt(restaurants.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == RESTAURANT_TYPE) {
            RestaurantViewHolder(ItemRestaurantBinding.inflate(inflater))
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (restaurants[position] is Restaurant)
            return RESTAURANT_TYPE
        return PROGRESS_TYPE
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RestaurantViewHolder && restaurants[position] is Restaurant)
            holder.bind(restaurants[position] as Restaurant)
    }

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Restaurant) {
            binding.restaurant = item
            binding.cardRestaurant.setOnClickListener {
                listener.onSelectRestaurant(item)
            }
            binding.executePendingBindings()
        }
    }

    inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val PROGRESS_TYPE = 1
        private const val RESTAURANT_TYPE = 2
    }

    interface OnAdapterInteraction {
        fun onSelectRestaurant(restaurant: Restaurant?)
    }
}