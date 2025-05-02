package com.test.murni.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.murni.R
import com.test.murni.data.model.Article
import com.test.murni.databinding.ItemViewBlogBinding
import com.test.murni.utils.extention.convertDpToPixel
import com.test.murni.utils.extention.setImage

class BlogAdapter(val items: MutableList<Article>): RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    var listener: AdapterClickListener<Article>? = null

    var maxWidth = 160

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemViewBlogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_view_blog, parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setData(items: List<Article>?) {
        this.items.clear()
        items?.let {
            this.items.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], position)
    }

    inner class ViewHolder(var binding: ItemViewBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(model: Article, position: Int) {
            binding.data = model
            binding.apply {
                if (maxWidth > 0) {
                    sectionBlog.layoutParams.width = convertDpToPixel(itemView.context, maxWidth)
                } else {
                    sectionBlog.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                }

                tvBlogName.text = model.title
                ivBlog.setImage(model.imageUrl)

                sectionBlog.setOnClickListener {
                    listener?.onItemClick(model)
                }
            }
        }
    }
}