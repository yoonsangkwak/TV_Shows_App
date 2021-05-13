package site.yoonsang.tvshowsapp.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tvshowsapp.database.FavoriteShow
import site.yoonsang.tvshowsapp.databinding.ShowItemBinding

class FavoriteAdapter(val clickListener: ShowClickListener) : ListAdapter<FavoriteShow, FavoriteAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<FavoriteShow>() {
        override fun areItemsTheSame(oldItem: FavoriteShow, newItem: FavoriteShow): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FavoriteShow, newItem: FavoriteShow): Boolean =
            oldItem == newItem
    }

    class ViewHolder(private val binding: ShowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteShow: FavoriteShow) {
            binding.apply {
                textName.text = favoriteShow.name
                textStartDate.text = favoriteShow.start_date
                textCountry.text = favoriteShow.country
                textStatus.text = favoriteShow.status
                Glide.with(itemView)
                    .load(favoriteShow.image_thumbnail_path)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
    }

    class ShowClickListener(val clickListener: (favoriteShow: FavoriteShow) -> Unit) {
        fun onClick(favoriteShow: FavoriteShow) = clickListener(favoriteShow)
    }
}