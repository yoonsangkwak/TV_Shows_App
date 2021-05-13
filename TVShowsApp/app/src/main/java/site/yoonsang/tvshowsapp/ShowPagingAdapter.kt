package site.yoonsang.tvshowsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tvshowsapp.data.model.Show
import site.yoonsang.tvshowsapp.databinding.ShowItemBinding

class ShowPagingAdapter(private val listener: ShowPagingAdapter.OnItemClickListener): PagingDataAdapter<Show, ShowPagingAdapter.ShowViewHolder>(SHOW_COMPARATOR) {

    companion object {
        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem == newItem
        }
    }

    inner class ShowViewHolder(private val binding: ShowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.apply {
                textName.text = show.name
                textStartDate.text = show.start_date
                textCountry.text = show.country
                textStatus.text = show.status
                Glide.with(itemView)
                    .load(show.image_thumbnail_path)
                    .into(imageView)
            }
        }

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(show: Show)
    }
}