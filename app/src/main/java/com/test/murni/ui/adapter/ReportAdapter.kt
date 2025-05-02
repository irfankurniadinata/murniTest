package com.test.murni.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.murni.R
import com.test.murni.data.model.Article
import com.test.murni.databinding.ItemViewReportBinding
import com.test.murni.utils.extention.convertDpToPixel
import com.test.murni.utils.extention.setImage

class ReportAdapter(val items: MutableList<Article>): RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    var listener: AdapterClickListener<Article>? = null

    var maxWidth = 160

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemViewReportBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_view_report, parent,
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

    inner class ViewHolder(var binding: ItemViewReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(model: Article, position: Int) {
            binding.data = model
            binding.apply {
                if (maxWidth > 0) {
                    sectionReport.layoutParams.width = convertDpToPixel(itemView.context, maxWidth)
                } else {
                    sectionReport.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                }

                tvReportName.text = model.title
                ivReport.setImage(model.imageUrl)

                sectionReport.setOnClickListener {
                    listener?.onItemClick(model)
                }
            }
        }
    }
}