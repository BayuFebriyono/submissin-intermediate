package site.encryptdev.submissionawalintermediate.ui.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem
import site.encryptdev.submissionawalintermediate.databinding.ItemStoryBinding
import site.encryptdev.submissionawalintermediate.dto.Story

class StoryAdapter() :
    PagingDataAdapter<ListStoryItem, StoryAdapter.StoryHolder>(DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryHolder(binding)
    }


    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
//        holder.binding.tvTitle.text = listStory[position]?.name.toString()
//        holder.binding.tvDeskripsi.text = listStory[position]?.description.toString()
//        Glide.with(holder.itemView)
//            .load(listStory[position]?.photoUrl)
//            .into(holder.binding.imageView)
//        holder.itemView.setOnClickListener { onItemClickCallback.onItem(listStory[holder.adapterPosition]) }
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class StoryHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.tvTitle.text = data.name.toString()
            binding.tvDeskripsi.text = data.description.toString()
            Glide.with(itemView)
                .load(data.photoUrl)
                .into(binding.imageView)
            itemView.setOnClickListener {
                val story = Story(data.name!!, data.description!!, data.photoUrl!!)
                val move = Intent(itemView.context, DetailStoryActivity::class.java)
                move.putExtra(DetailStoryActivity.EXTRA_STORY, story)
                itemView.context.startActivity(move, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }



    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}