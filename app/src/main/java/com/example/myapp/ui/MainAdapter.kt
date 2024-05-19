package com.example.myapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.myapp.data.model.MainData
import com.example.myapp.databinding.ItemMainBinding

class MainPagerAdapter :
    PagingDataAdapter<MainData, MainPagerAdapter.MainViewHolder>(MainDiffCallback) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class MainViewHolder(private val binding: ItemMainBinding) : ViewHolder(binding.root) {
        fun bind(mainData: MainData) {
            binding.tvItem.text = mainData.title
            itemView.setOnClickListener {
                itemClickListener?.invoke(mainData)
            }
            Glide.with(itemView.context)
                .load(mainData.urlToImage)
                .placeholder(com.google.android.material.R.color.design_default_color_secondary)
                .into(binding.ivItem)
        }
    }

    private var itemClickListener: ((MainData) -> Unit)? = null

    fun setOnItemClickListener(listener: (MainData) -> Unit) {
        itemClickListener = listener
    }

    object MainDiffCallback : DiffUtil.ItemCallback<MainData>() {
        override fun areItemsTheSame(oldItem: MainData, newItem: MainData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MainData, newItem: MainData): Boolean {
            return oldItem == newItem
        }
    }
}

//class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
//
//    private val items = ArrayList<MainData>()
//
//    fun setItems(items: List<MainData>) {
//        val diffResult = DiffUtil.calculateDiff(MainDiffCallBack(this.items, items))
//        this.items.clear()
//        this.items.addAll(items)
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    fun getItems(): List<MainData> {
//        return items
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
//        return MainViewHolder(
//            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    inner class MainViewHolder(private val binding: ItemMainBinding): ViewHolder(binding.root) {
//
//        fun bind(mainData: MainData) {
//            binding.tvItem.text = mainData.title
//            itemView.setOnClickListener {
//                itemClickListener?.invoke(mainData)
//            }
//            Glide.with(itemView.context)
//                .load(mainData.urlToImage)
//                .placeholder(com.google.android.material.R.color.design_default_color_secondary)
//                .into(binding.ivItem)
//        }
//
//    }
//
//    private var itemClickListener: ((MainData)->Unit)? = null
//
//    fun setOnItemClickListener(listener: (MainData)->Unit) {
//        itemClickListener = listener
//    }
//}

//class MainDiffCallBack(private val oldList: List<MainData>, private val newList: List<MainData>) :
//    DiffUtil.Callback() {
//    override fun getOldListSize(): Int {
//        return oldList.size
//    }
//
//    override fun getNewListSize(): Int {
//        return newList.size
//    }
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return (oldList[oldItemPosition].title == newList[newItemPosition].title)
//
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
//}