package com.example.solidarity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.solidarity.data.model.Categories
import com.example.solidarity.databinding.SingleCategoryLayoutBinding

class CategoriesAdapter  :
    ListAdapter<Categories, CategoriesAdapter.CategoriesViewHolder>(
        ItemsCallback()
    ) {

    private lateinit var itemClickListener: (Categories, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CategoriesViewHolder {
        val binding =
            SingleCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindData()
    }

    inner class CategoriesViewHolder(private val binding: SingleCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var model: Categories? = null

        fun bindData() {
            model = getItem(adapterPosition)
            binding.apply {

                binding.etCategorieName.text = model?.categoryName
                binding.etCategorieName.text = model?.categoryDescription
            }
            binding.mainLayout.setOnClickListener {
                itemClickListener.invoke(model!!, adapterPosition)

            }

        }
    }

    fun setOnItemClickListener(clickListener: (Categories, Int) -> Unit) {
        itemClickListener = clickListener
    }

}

class ItemsCallback :
    DiffUtil.ItemCallback<Categories>() {
    override fun areItemsTheSame(
        oldItem: Categories,
        newItem: Categories
    ): Boolean {
        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(
        oldItem: Categories,
        newItem: Categories
    ): Boolean {
        return oldItem == newItem
    }


}