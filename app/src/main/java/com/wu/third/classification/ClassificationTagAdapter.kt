package com.wu.third.classification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.wu.base.adapter.KtAdapter
import com.wu.base.base.adapter.KtDataBindingViewHolder
import com.wu.third.R
import com.wu.third.databinding.LayoutClassficationTagBinding


/**
 * @author wkq
 *
 * @date 2021年11月18日 14:55
 *
 *@des
 *
 */

class ClassificationTagAdapter(mContext: Context) : KtAdapter<ClassificationTagInfo>(mContext) {

    var diff: AsyncListDiffer<ClassificationTagInfo>? = null

    init {
        this.mContext = mContext
        diff = AsyncListDiffer<ClassificationTagInfo>(this, ClassificationTagDiffItemCallBack())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = DataBindingUtil.inflate<LayoutClassficationTagBinding>(
            LayoutInflater.from(mContext),
            R.layout.layout_classfication_tag, parent, false
        )

        var holder = KtDataBindingViewHolder(binding.root)
        holder.binding = binding
        return holder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var bindingHolder = holder as KtDataBindingViewHolder
        var binding = bindingHolder.binding as LayoutClassficationTagBinding
        binding.tvTag.text = getAsyncListItem(position)!!.title
        if (getAsyncListItem(position)!!.isShow) {
            binding.rlRoot.setBackgroundColor(mContext.resources.getColor(R.color.color_23d41e))
        } else {
            binding.rlRoot.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }
    }

    fun setAsyncListDatas(newList: List<ClassificationTagInfo>) {
        if (diff == null) return
        diff!!.submitList(newList)
    }

    fun getAsyncListItem(position: Int): ClassificationTagInfo {
        return diff!!.currentList.get(position)
    }

    fun getAsyncListItems(): List<ClassificationTagInfo> {
        return diff!!.currentList
    }

    override fun getItemCount(): Int {
        return diff!!.currentList.size
    }


}